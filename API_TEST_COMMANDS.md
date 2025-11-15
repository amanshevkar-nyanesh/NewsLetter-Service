# API Test Commands - Newsletter Service

This file contains curl commands to test all APIs with sample data:
- 4 Topics
- 3 Subscribers
- 10 Contents

## Base URL
```bash
BASE_URL="http://localhost:8080/api/v1"
# For Heroku, use: BASE_URL="https://your-app-name.herokuapp.com/api/v1"
```

---

## 1. Create Topics (4 Topics)

### Topic 1: Technology
```bash
curl -X POST "${BASE_URL}/topics" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Technology",
    "description": "Latest technology news, gadgets, and innovations"
  }'
```

### Topic 2: Health & Wellness
```bash
curl -X POST "${BASE_URL}/topics" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Health & Wellness",
    "description": "Health tips, fitness advice, and wellness updates"
  }'
```

### Topic 3: Business
```bash
curl -X POST "${BASE_URL}/topics" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Business",
    "description": "Business news, market updates, and entrepreneurship"
  }'
```

### Topic 4: Entertainment
```bash
curl -X POST "${BASE_URL}/topics" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Entertainment",
    "description": "Movies, music, TV shows, and celebrity news"
  }'
```

### Get All Topics
```bash
curl -X GET "${BASE_URL}/topics"
```

---

## 2. Create Subscribers (3 Subscribers)

### Subscriber 1: Sayali Shevkar
```bash
curl -X POST "${BASE_URL}/subscribers" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "sayali shevkar",
    "email": "sayalishevkar@gmail.com"
  }'
```

### Subscriber 2: Prasanna Kulkarni
```bash
curl -X POST "${BASE_URL}/subscribers" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Prasanna kulkarni",
    "email": "prasannakulkarni221@gmail.com"
  }'
```

### Subscriber 3: Sangeeta Shevkar
```bash
curl -X POST "${BASE_URL}/subscribers" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "sangeeta shevkar",
    "email": "sangeetashevkar01@gmail.com"
  }'
```

### Get All Subscribers
```bash
curl -X GET "${BASE_URL}/subscribers"
```

---

## 3. Subscribe Subscribers to Topics

### Subscribe Sayali (1) to Technology (1)
```bash
curl -X POST "${BASE_URL}/subscribers/1/subscribe/1"
```

### Subscribe Sayali (1) to Business (3)
```bash
curl -X POST "${BASE_URL}/subscribers/1/subscribe/3"
```

### Subscribe Prasanna (2) to Technology (1)
```bash
curl -X POST "${BASE_URL}/subscribers/2/subscribe/1"
```

### Subscribe Prasanna (2) to Health & Wellness (2)
```bash
curl -X POST "${BASE_URL}/subscribers/2/subscribe/2"
```

### Subscribe Sangeeta (3) to Business (3)
```bash
curl -X POST "${BASE_URL}/subscribers/3/subscribe/3"
```

### Subscribe Sangeeta (3) to Entertainment (4)
```bash
curl -X POST "${BASE_URL}/subscribers/3/subscribe/4"
```

### Get Subscriber by ID (to verify subscriptions)
```bash
curl -X GET "${BASE_URL}/subscribers/1"
```

---

## 4. Create Contents (10 Contents)

**Note:** Update the `scheduledTime` to a future time (at least 1-2 minutes from now) to test the scheduler.
You can use a tool like `date` command or set it manually.

### Content 1: Technology - AI Breakthrough
```bash
curl -X POST "${BASE_URL}/contents" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "AI Breakthrough: New Language Model Released",
    "text": "Scientists have announced a groundbreaking new AI language model that can understand and generate human-like text with unprecedented accuracy. This model represents a significant leap forward in artificial intelligence research and has potential applications in education, healthcare, and creative industries.",
    "topicId": 1,
    "scheduledTime": "2024-12-20T10:00:00"
  }'
```

### Content 2: Technology - Smartphone Launch
```bash
curl -X POST "${BASE_URL}/contents" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "New Flagship Smartphone Launched",
    "text": "The latest flagship smartphone has been unveiled with cutting-edge features including a 200MP camera, 5G connectivity, and an all-day battery life. Pre-orders start next week with exclusive launch offers for early adopters.",
    "topicId": 1,
    "scheduledTime": "2024-12-20T11:00:00"
  }'
```

### Content 3: Health & Wellness - Morning Routine
```bash
curl -X POST "${BASE_URL}/contents" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "5 Morning Habits for Better Health",
    "text": "Start your day right with these five simple morning habits: 1) Drink a glass of water, 2) Get 10 minutes of sunlight, 3) Practice 5 minutes of meditation, 4) Eat a protein-rich breakfast, 5) Take a 10-minute walk. These habits can significantly improve your energy levels and overall well-being.",
    "topicId": 2,
    "scheduledTime": "2024-12-20T12:00:00"
  }'
```

### Content 4: Health & Wellness - Exercise Tips
```bash
curl -X POST "${BASE_URL}/contents" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Best Exercises for Home Workouts",
    "text": "You don'\''t need a gym membership to stay fit! Here are effective home workout exercises: push-ups, squats, planks, jumping jacks, and burpees. Aim for 3-4 sets of 10-15 repetitions each. Remember to warm up before and cool down after your workout.",
    "topicId": 2,
    "scheduledTime": "2024-12-20T13:00:00"
  }'
```

### Content 5: Business - Market Update
```bash
curl -X POST "${BASE_URL}/contents" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Weekly Market Update: Tech Stocks Surge",
    "text": "Technology stocks have seen a significant surge this week, with major tech companies reporting strong quarterly earnings. The market is showing positive signs of recovery, with investors showing renewed confidence in the tech sector. Analysts predict continued growth in the coming months.",
    "topicId": 3,
    "scheduledTime": "2024-12-20T14:00:00"
  }'
```

### Content 6: Business - Startup Funding
```bash
curl -X POST "${BASE_URL}/contents" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Startup Raises $50M in Series B Funding",
    "text": "A promising fintech startup has successfully raised $50 million in Series B funding, led by prominent venture capital firms. The company plans to use the funds to expand its operations globally and develop new innovative financial products. This marks one of the largest funding rounds in the sector this year.",
    "topicId": 3,
    "scheduledTime": "2024-12-20T15:00:00"
  }'
```

### Content 7: Entertainment - Movie Review
```bash
curl -X POST "${BASE_URL}/contents" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Blockbuster Movie Breaks Box Office Records",
    "text": "The latest action-packed blockbuster has shattered box office records, earning over $100 million in its opening weekend. Critics are praising the film'\''s stunning visual effects, compelling storyline, and outstanding performances. Fans are already calling for a sequel.",
    "topicId": 4,
    "scheduledTime": "2024-12-20T16:00:00"
  }'
```

### Content 8: Entertainment - Music News
```bash
curl -X POST "${BASE_URL}/contents" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Top Artist Releases New Album",
    "text": "The world'\''s top-charting artist has just released their highly anticipated new album, featuring collaborations with several A-list musicians. The album has already topped streaming charts worldwide, with fans praising its innovative sound and meaningful lyrics. Exclusive behind-the-scenes content is available on the artist'\''s official website.",
    "topicId": 4,
    "scheduledTime": "2024-12-20T17:00:00"
  }'
```

### Content 9: Technology - Cybersecurity Alert
```bash
curl -X POST "${BASE_URL}/contents" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Important Cybersecurity Update",
    "text": "Security experts are warning about a new type of phishing attack targeting email users. Always verify sender identities, never click suspicious links, and enable two-factor authentication on all your accounts. Stay safe online by keeping your software updated and using strong, unique passwords.",
    "topicId": 1,
    "scheduledTime": "2024-12-20T18:00:00"
  }'
```

### Content 10: Business - Economic Forecast
```bash
curl -X POST "${BASE_URL}/contents" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Economic Forecast for Next Quarter",
    "text": "Leading economists predict steady economic growth for the next quarter, with inflation expected to stabilize. Key sectors showing strong performance include technology, healthcare, and renewable energy. Businesses are advised to focus on innovation and digital transformation to stay competitive in the evolving market landscape.",
    "topicId": 3,
    "scheduledTime": "2024-12-20T19:00:00"
  }'
```

### Get All Contents
```bash
curl -X GET "${BASE_URL}/contents"
```

### Get Content by ID
```bash
curl -X GET "${BASE_URL}/contents/1"
```

### Get Contents by Topic ID
```bash
curl -X GET "${BASE_URL}/contents/topic/1"
```

---

## 5. Additional Test Commands

### Get Topic by ID
```bash
curl -X GET "${BASE_URL}/topics/1"
```

### Get Subscriber by ID
```bash
curl -X GET "${BASE_URL}/subscribers/1"
```

### Unsubscribe a Subscriber from a Topic
```bash
curl -X POST "${BASE_URL}/subscribers/1/unsubscribe/1"
```

### Deactivate a Subscriber
```bash
curl -X DELETE "${BASE_URL}/subscribers/1/deactivate"
```

### Delete a Content
```bash
curl -X DELETE "${BASE_URL}/contents/1"
```

### Delete a Topic
```bash
curl -X DELETE "${BASE_URL}/topics/1"
```

---

## 6. Quick Test Script (All-in-One)

Save this as `test-all-apis.sh` and run it:

```bash
#!/bin/bash

BASE_URL="http://localhost:8080/api/v1"

echo "=== Creating Topics ==="
curl -X POST "${BASE_URL}/topics" -H "Content-Type: application/json" -d '{"name":"Technology","description":"Latest technology news"}'
curl -X POST "${BASE_URL}/topics" -H "Content-Type: application/json" -d '{"name":"Health & Wellness","description":"Health tips and wellness"}'
curl -X POST "${BASE_URL}/topics" -H "Content-Type: application/json" -d '{"name":"Business","description":"Business news and updates"}'
curl -X POST "${BASE_URL}/topics" -H "Content-Type: application/json" -d '{"name":"Entertainment","description":"Movies, music, and TV"}'

echo -e "\n=== Creating Subscribers ==="
curl -X POST "${BASE_URL}/subscribers" -H "Content-Type: application/json" -d '{"name":"sayali shevkar","email":"sayalishevkar@gmail.com"}'
curl -X POST "${BASE_URL}/subscribers" -H "Content-Type: application/json" -d '{"name":"Prasanna kulkarni","email":"prasannakulkarni221@gmail.com"}'
curl -X POST "${BASE_URL}/subscribers" -H "Content-Type: application/json" -d '{"name":"sangeeta shevkar","email":"sangeetashevkar01@gmail.com"}'

echo -e "\n=== Subscribing Users to Topics ==="
curl -X POST "${BASE_URL}/subscribers/1/subscribe/1"
curl -X POST "${BASE_URL}/subscribers/1/subscribe/3"
curl -X POST "${BASE_URL}/subscribers/2/subscribe/1"
curl -X POST "${BASE_URL}/subscribers/2/subscribe/2"
curl -X POST "${BASE_URL}/subscribers/3/subscribe/3"
curl -X POST "${BASE_URL}/subscribers/3/subscribe/4"

echo -e "\n=== Creating Contents ==="
# Get current time and add 2 minutes for scheduling
SCHEDULED_TIME=$(date -u -v+2M +"%Y-%m-%dT%H:%M:%S" 2>/dev/null || date -u -d "+2 minutes" +"%Y-%m-%dT%H:%M:%S" 2>/dev/null || echo "2024-12-20T10:00:00")

curl -X POST "${BASE_URL}/contents" -H "Content-Type: application/json" -d "{\"title\":\"AI Breakthrough\",\"text\":\"New AI model released\",\"topicId\":1,\"scheduledTime\":\"${SCHEDULED_TIME}\"}"
curl -X POST "${BASE_URL}/contents" -H "Content-Type: application/json" -d "{\"title\":\"Smartphone Launch\",\"text\":\"New flagship phone unveiled\",\"topicId\":1,\"scheduledTime\":\"${SCHEDULED_TIME}\"}"
curl -X POST "${BASE_URL}/contents" -H "Content-Type: application/json" -d "{\"title\":\"Morning Routine\",\"text\":\"5 habits for better health\",\"topicId\":2,\"scheduledTime\":\"${SCHEDULED_TIME}\"}"
curl -X POST "${BASE_URL}/contents" -H "Content-Type: application/json" -d "{\"title\":\"Home Workouts\",\"text\":\"Best exercises at home\",\"topicId\":2,\"scheduledTime\":\"${SCHEDULED_TIME}\"}"
curl -X POST "${BASE_URL}/contents" -H "Content-Type: application/json" -d "{\"title\":\"Market Update\",\"text\":\"Tech stocks surge\",\"topicId\":3,\"scheduledTime\":\"${SCHEDULED_TIME}\"}"
curl -X POST "${BASE_URL}/contents" -H "Content-Type: application/json" -d "{\"title\":\"Startup Funding\",\"text\":\"$50M raised\",\"topicId\":3,\"scheduledTime\":\"${SCHEDULED_TIME}\"}"
curl -X POST "${BASE_URL}/contents" -H "Content-Type: application/json" -d "{\"title\":\"Movie Review\",\"text\":\"Blockbuster breaks records\",\"topicId\":4,\"scheduledTime\":\"${SCHEDULED_TIME}\"}"
curl -X POST "${BASE_URL}/contents" -H "Content-Type: application/json" -d "{\"title\":\"Music News\",\"text\":\"New album released\",\"topicId\":4,\"scheduledTime\":\"${SCHEDULED_TIME}\"}"
curl -X POST "${BASE_URL}/contents" -H "Content-Type: application/json" -d "{\"title\":\"Cybersecurity Alert\",\"text\":\"New phishing attack\",\"topicId\":1,\"scheduledTime\":\"${SCHEDULED_TIME}\"}"
curl -X POST "${BASE_URL}/contents" -H "Content-Type: application/json" -d "{\"title\":\"Economic Forecast\",\"text\":\"Growth predictions\",\"topicId\":3,\"scheduledTime\":\"${SCHEDULED_TIME}\"}"

echo -e "\n=== Test Complete ==="
echo "Check the application logs to see scheduled emails being sent!"
```

---

## Notes

1. **Update Scheduled Times**: Before running content creation commands, update the `scheduledTime` values to future dates/times (at least 1-2 minutes from now) to test the scheduler.

2. **For Immediate Testing**: To test email sending immediately, set `scheduledTime` to a time in the past (e.g., 1 minute ago). The scheduler will pick it up on the next run.

3. **Check Logs**: Monitor the application logs to see when emails are being sent by the scheduler.

4. **Verify Subscriptions**: Use GET endpoints to verify that subscribers are properly subscribed to topics before creating content.

5. **Email Configuration**: Make sure SMTP is configured correctly before testing email functionality.

