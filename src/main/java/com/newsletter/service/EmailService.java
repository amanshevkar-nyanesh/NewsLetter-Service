package com.newsletter.service;

import com.newsletter.model.dao.Content;
import com.newsletter.model.dao.Subscriber;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username:}")
    private String smtpUsername;

    @Value("${app.email.from-address:}")
    private String fromEmail;

    @Value("${app.email.from-name:Newsletter Service}")
    private String fromName;

    @PostConstruct
    public void init() {
        if (smtpUsername == null || smtpUsername.trim().isEmpty()) {
            log.error("==========================================");
            log.error("EMAIL CONFIGURATION ERROR!");
            log.error("MAIL_USERNAME is not set or is empty!");
            log.error("Please set MAIL_USERNAME environment variable");
            log.error("or configure it in application.properties");
            log.error("==========================================");
        } else {
            // If fromEmail is not set, try to use smtpUsername (for Gmail)
            // For SendGrid (apikey), fromEmail must be set separately
            if (fromEmail == null || fromEmail.trim().isEmpty()) {
                // If smtpUsername looks like an email, use it as fromEmail
                if (smtpUsername.contains("@")) {
                    fromEmail = cleanEmailAddress(smtpUsername);
                    log.info("Using SMTP username as from email: {}", fromEmail);
                } else {
                    log.warn("SMTP username '{}' is not an email. Please set EMAIL_FROM_ADDRESS environment variable with a verified sender email.", smtpUsername);
                }
            } else {
                fromEmail = cleanEmailAddress(fromEmail);
                log.info("Using configured from email: {}", fromEmail);
            }
            log.info("Email service initialized - SMTP username: {}, From email: {}", smtpUsername, fromEmail);
        }
    }

    /**
     * Cleans email address by extracting only the email part
     * Handles cases where environment variables might be incorrectly concatenated
     */
    private String cleanEmailAddress(String email) {
        if (email == null) {
            return "";
        }

        String trimmed = email.trim();

        // If it contains a comma, take only the part before the comma
        if (trimmed.contains(",")) {
            trimmed = trimmed.substring(0, trimmed.indexOf(",")).trim();
        }

        // Extract email pattern (text@domain)
        // Find the @ symbol and extract the email part
        int atIndex = trimmed.indexOf("@");
        if (atIndex > 0) {
            // Find the start of the email (look backwards for space or start)
            int start = 0;
            for (int i = atIndex - 1; i >= 0; i--) {
                if (trimmed.charAt(i) == ' ' || trimmed.charAt(i) == ',') {
                    start = i + 1;
                    break;
                }
            }
            // Find the end of the email (look forwards for space, comma, or end)
            int end = trimmed.length();
            for (int i = atIndex + 1; i < trimmed.length(); i++) {
                if (trimmed.charAt(i) == ' ' || trimmed.charAt(i) == ',') {
                    end = i;
                    break;
                }
            }
            trimmed = trimmed.substring(start, end).trim();
        }

        return trimmed;
    }

    /**
     * Sends newsletter content to a subscriber
     *
     * @param subscriber The subscriber to send the email to
     * @param content    The content to send
     * @return true if email was sent successfully, false otherwise
     */
    public boolean sendNewsletter(Subscriber subscriber, Content content) {
        // Validate from email address (not SMTP username)
        String trimmedEmail = (fromEmail != null) ? cleanEmailAddress(fromEmail) : "";
        if (trimmedEmail.isEmpty()) {
            log.error("From email address is missing! Set EMAIL_FROM_ADDRESS environment variable. Cannot send email to {}",
                    subscriber.getEmail());
            return false;
        }

        // Validate email format (must be a valid email for the "from" address)
        if (!trimmedEmail.contains("@")) {
            log.error("Invalid email format for EMAIL_FROM_ADDRESS: '{}'. Must be a valid email address.", trimmedEmail);
            return false;
        }

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            // Use the configured email as the "from" address
            message.setFrom(trimmedEmail);
            message.setTo(subscriber.getEmail());
            message.setSubject("Newsletter: " + content.getTitle());
            message.setText(buildEmailBody(content, subscriber));

            mailSender.send(message);
            log.info("Newsletter sent successfully to {} for content ID: {}",
                    subscriber.getEmail(), content.getId());
            return true;
        } catch (Exception e) {
            log.error("Failed to send newsletter to {} for content ID: {}. Error: {} - {}. From email: '{}'",
                    subscriber.getEmail(), content.getId(), e.getClass().getSimpleName(), e.getMessage(), trimmedEmail);
            if (log.isDebugEnabled()) {
                log.debug("Full error stack trace:", e);
            }
            return false;
        }
    }

    /**
     * Sends newsletter content to all subscribers of a topic
     *
     * @param content The content to send
     * @return number of emails sent successfully
     */
    public int sendNewsletterToTopicSubscribers(Content content) {
        Set<Subscriber> subscribers = content.getTopic().getSubscribers();
        int successCount = 0;

        for (Subscriber subscriber : subscribers) {
            // Only send it to active subscribers
            if (subscriber.getIsActive() != null && subscriber.getIsActive()) {
                if (sendNewsletter(subscriber, content)) {
                    successCount++;
                }
            }
        }

        log.info("Sent newsletter to {}/{} active subscribers for content ID: {}",
                successCount, subscribers.size(), content.getId());
        return successCount;
    }

    /**
     * Builds the email body with content details
     */
    private String buildEmailBody(Content content, Subscriber subscriber) {
        StringBuilder builder = new StringBuilder();
        builder.append("Hello ").append(subscriber.getName()).append(",\n\n");
        builder.append("You have a new newsletter from ").append(content.getTopic().getName()).append(" topic.\n\n");
        builder.append("Title: ").append(content.getTitle()).append("\n\n");
        builder.append("Content:\n");
        builder.append(content.getText()).append("\n\n");
        builder.append("---\n");
        builder.append("This is an automated newsletter from Newsletter Service.\n");
        builder.append("To unsubscribe, please contact the administrator.");

        return builder.toString();
    }
}

