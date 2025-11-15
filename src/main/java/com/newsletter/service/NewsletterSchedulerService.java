package com.newsletter.service;

import com.newsletter.model.dao.Content;
import com.newsletter.repository.ContentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class NewsletterSchedulerService {

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private EmailService emailService;

    /**
     * Scheduled task that runs every minute to check for content that needs to be sent
     * This will send newsletters to subscribers at their scheduled time
     */
    @Scheduled(fixedRate = 60000) // Run every 60 seconds (1 minute)
    @Transactional
    public void sendScheduledNewsletters() {
        try {
            LocalDateTime currentTime = LocalDateTime.now();
            List<Content> unsentContents = contentRepository.findUnsentContentByScheduledTime(currentTime);

            if (unsentContents.isEmpty()) {
                log.debug("No newsletters to send at {}", currentTime);
                return;
            }

            log.info("Found {} newsletter(s) to send", unsentContents.size());

            for (Content content : unsentContents) {
                try {
                    // Send a news-letter to all subscribers of the topic
                    int sentCount = emailService.sendNewsletterToTopicSubscribers(content);
                    
                    // Mark content as sent only if at least one email was sent successfully
                    if (sentCount > 0) {
                        content.setIsSent(true);
                        contentRepository.save(content);
                        log.info("Marked content ID {} as sent. Sent to {} subscribers", 
                                content.getId(), sentCount);
                    } else {
                        log.warn("No emails were sent for content ID {}. Will retry in next cycle.", 
                                content.getId());
                    }
                } catch (Exception e) {
                    log.error("Error processing content ID {}: {}", content.getId(), e.getMessage(), e);
                    // Don't mark as sent if there was an error, so it can be retried
                }
            }
        } catch (Exception e) {
            log.error("Error in scheduled newsletter task: {}", e.getMessage(), e);
        }
    }
}

