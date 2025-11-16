# Newsletter Service

A simple newsletter service built with Spring Boot. We can manage subscribers, create topics, schedule content, and it'll automatically send emails to subscribers at the scheduled time.

## What it does

- Manage subscribers (add, view, subscribe/unsubscribe to topics)
- Create topics for organizing newsletters
- Schedule content with a specific send time
- Automatically sends emails to all subscribers of a topic when the scheduled time arrives
- Uses SendGrid SMTP service for sending emails (designed for cloud deployments)

## Tech Stack

- Java 21
- Spring Boot 3.2.0
- Spring Data JPA
- H2 (for local dev, in-memory)
- PostgreSQL (for production on Render)
- Spring Mail for sending emails
- Maven for builds


## API Endpoints

Base URL: `http://localhost:8080/api/v1` 

Render Base URL:  `https://newsletter-service-w8n2.onrender.com/api/v1` 
### Topics

**Create topic:**
```
POST /api/v1/topics
Content-Type: application/json

{
  "name": "Technology",
  "description": "Tech news and updates"
}
```

**Get all topics:** `GET /api/v1/topics`

**Get topic by ID:** `GET /api/v1/topics/{id}`

**Delete topic:** `DELETE /api/v1/topics/{id}`

### Subscribers

**Create subscriber:**
```
POST /api/v1/subscribers
Content-Type: application/json

{
  "name": "Aman Shevkar",
  "email": "aman@example.com"
}
```

**Get all subscribers:** `GET /api/v1/subscribers`

**Get subscriber by ID:** `GET /api/v1/subscribers/{id}`

**Subscribe to topic:** `POST /api/v1/subscribers/{subscriberId}/subscribe/{topicId}`

**Unsubscribe from topic:** `POST /api/v1/subscribers/{subscriberId}/unsubscribe/{topicId}`

**Deactivate subscriber:** `DELETE /api/v1/subscribers/{id}/deactivate`

### Content

**Create content:**
```
POST /api/v1/contents
Content-Type: application/json

{
  "title": "Weekly Update",
  "text": "Here's what happened this week...",
  "topicId": 1,
  "scheduledTime": "2024-01-15T14:30:00"
}
```

The `scheduledTime` should be in ISO format (YYYY-MM-DDTHH:mm:ss). The scheduler will pick it up and send emails when that time arrives.

**Get all contents:** `GET /api/v1/contents`

**Get content by ID:** `GET /api/v1/contents/{id}`

**Get contents by topic:** `GET /api/v1/contents/topic/{topicId}`

**Delete content:** `DELETE /api/v1/contents/{id}`

## How it works

1. Create some topics (e.g., "Technology", "Business")
2. Add subscribers with their email addresses
3. Subscribe users to topics they're interested in
4. Create content and set a scheduled time
5. The scheduler runs every minute and checks for content that should be sent
6. When the scheduled time arrives, it sends emails to all active subscribers of that topic

The scheduler is in `NewsletterSchedulerService` - it runs every 60 seconds and looks for content where `scheduledTime <= now` and `isSent = false`.

## Quick Start: Adding a New Subscriber and Sending Them a Newsletter

Here's a simple walkthrough of how to get a new subscriber set up and send them content:

**Step 1: Check existing topics (optional but recommended)**
```
GET /api/v1/topics
```
This shows you all the topics you already have. If you see a topic you want to use, note its ID and skip to Step 2. If not, create a new one.

**Step 2: Create a topic (if you don't have one yet)**
```
POST /api/v1/topics
{
  "name": "Technology",
  "description": "Latest tech news and updates"
}
```
You'll get back a topic ID (let's say it's `1`). Keep this ID handy - you'll need it later.

**Step 3: Add the subscriber**
```
POST /api/v1/subscribers
{
  "name": "Aman Shevkar",
  "email": "aman@example.com"
}
```
This creates the subscriber and gives you their ID (let's say it's `5`).

**Step 4: Subscribe them to the topic**
```
POST /api/v1/subscribers/5/subscribe/1
```
Now Aman is subscribed to the Technology topic.

**Step 5: Create content and schedule it**
```
POST /api/v1/contents
{
  "title": "Weekly Tech Roundup",
  "text": "Here's what happened in tech this week...",
  "topicId": 1,
  "scheduledTime": "2024-12-20T10:00:00"
}
```
Set `scheduledTime` to a time in the past or current time if you want it sent immediately. The scheduler checks every minute, so it'll go out within 60 seconds.

**Step 6: Wait for the email**
The scheduler will pick up the content and send it to all active subscribers of that topic (including Aman). You can check the logs to see when it's sent.

That's it! The subscriber will receive the newsletter at the scheduled time. If you want to send them more content later, just create new content for that topic and they'll automatically get it.

## Testing

I've included some test files to make it easier:

- **Postman collection**: `Newsletter_Service.postman_collection.json` - just import it into Postman
- **cURL commands**: `API_TEST_COMMANDS.md` - has all the curl commands ready to copy/paste
- **Test script**: `test-all-apis.sh` - runs everything automatically

The test files create 4 topics, 3 subscribers, subscribe them to various topics, and create 10 contents. Just update the scheduled times in the content creation commands to test email sending.

## Deployment to Render

I deployed this on Render because they have a free tier. Here's how:

1. Push your code to GitHub

2. Sign up at https://render.com and create a new Web Service:
   - Connect your GitHub repo
   - Runtime: Java
   - Build command: `mvn clean package -DskipTests`
   - Start command: `java -jar target/newsletter-service.jar`

3. Create a PostgreSQL database on Render 

4. Link the database to your web service (Render sets DATABASE_URL automatically)

5. Add environment variables in Render dashboard:
   
   Go to your Web Service → Environment tab → Add the following variables:
   
   | Variable | Value | Example |
   |----------|-------|---------|
   | `MAIL_HOST` | `smtp.sendgrid.net` | smtp.sendgrid.net |
   | `MAIL_PORT` | `587` | 587 |
   | `MAIL_USERNAME` | `apikey` | apikey |
   | `MAIL_PASSWORD` | Your SendGrid API key | SG.xxxxx... |
   | `EMAIL_FROM_ADDRESS` | Your verified email | your-email@example.com |
   | `EMAIL_FROM_NAME` | Sender name | Newsletter Service |

   **SendGrid Setup:**
   - Sign up at https://sendgrid.com (free tier: 100 emails/day)
   - Create API key: Settings → API Keys → Create API Key
   - Verify sender email: Settings → Sender Authentication → Single Sender Verification
   - Use the verified email as `EMAIL_FROM_ADDRESS`

6. Deploy and wait for it to build


## Environment Variables

| Variable | What it does | Required |
|----------|--------------|----------|
| `MAIL_HOST` | SMTP server (smtp.sendgrid.net) | Yes |
| `MAIL_PORT` | SMTP port (587) | Yes |
| `MAIL_USERNAME` | SMTP username (use "apikey" for SendGrid) | Yes |
| `MAIL_PASSWORD` | SMTP password (your SendGrid API key) | Yes |
| `EMAIL_FROM_ADDRESS` | Sender email address (must be verified in SendGrid) | Yes |
| `EMAIL_FROM_NAME` | Sender name shown in emails | No |

## Database Schema

- **Topics**: id, name, description, createdAt
- **Subscribers**: id, email, name, isActive, createdAt, and a many-to-many relationship with topics
- **Contents**: id, title, text, topicId, scheduledTime, isSent, createdAt


## Project Structure

```
src/main/java/com/newsletter/
├── controller/     # REST endpoints
├── service/        # Business logic
├── repository/     # Database access
├── model/
│   ├── dao/       # Entities
│   ├── request/   # Request DTOs
│   └── response/  # Response DTOs
├── Utils/         # Helper classes
└── enums/         # Result codes, etc.
```

## Notes

- The scheduler runs every 60 seconds, so there might be up to a 1-minute delay from the scheduled time
- If email sending fails, the content won't be marked as sent, so it'll retry on the next cycle
- The Procfile and system.properties are for deployment (Render). I have deployed on render as heroku's free tier is 

That's about it. If you run into issues, check the logs - they're pretty verbose and should help debug most problems.

## Tradeoffs & Future Improvements

This is a simple project implementation for **JIRA: dev-7**. It's designed to be straightforward and easy to understand, but there are several production-grade features that aren't included:

**What's Missing:**
- **Redis for caching** - No caching layer for frequently accessed data (topics, subscribers)
- **Circuit breaker** - No resilience patterns for external service calls (email service)
- **Rate limiting** - API endpoints are not rate-limited
- **API Gateway** - Direct REST endpoints without gateway layer for routing/security
- **Kafka for scheduling** - Uses Spring's `@Scheduled` instead of a message queue for better scalability
- **Distributed locking** - Multiple instances could process the same scheduled content
- **Monitoring & Observability** - Basic logging only, no metrics/APM
- **Email retry mechanism** - Simple retry, no exponential backoff or dead letter queue
- **Database connection pooling optimization** - Using default HikariCP settings
- **Security** - No authentication/authorization, API keys, or OAuth

**Why these tradeoffs?**
This was built as a minimal viable implementation to demonstrate core functionality.

**Potential Enhancements:**
- Add Redis to cache topic/subscriber lookups
- Implement circuit breaker pattern for email service
- Add rate limiting per subscriber/IP
- Use Kafka for scheduled content delivery 
- Implement proper retry mechanism with exponential backoff
- Add API authentication (JWT/OAuth2)
- Set up monitoring (Prometheus/Grafana)
- Add API gateway for centralized routing
- Implement database read replicas for better read performance

