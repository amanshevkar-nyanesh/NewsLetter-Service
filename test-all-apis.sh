#!/bin/bash

# Newsletter Service - Complete API Test Script
# This script creates 4 topics, 3 subscribers, subscribes them to topics, and creates 10 contents

BASE_URL="http://localhost:8080/api/v1"

echo "=========================================="
echo "Newsletter Service - API Test Script"
echo "=========================================="
echo ""

# Check if server is running
echo "Checking if server is running..."
if ! curl -s "${BASE_URL}/topics" > /dev/null 2>&1; then
    echo "❌ Error: Server is not running at ${BASE_URL}"
    echo "Please start the application first: mvn spring-boot:run"
    exit 1
fi
echo "✅ Server is running!"
echo ""

# Create Topics
echo "=== Creating Topics ==="
curl -X POST "${BASE_URL}/topics" \
  -H "Content-Type: application/json" \
  -d '{"name":"Technology","description":"Latest technology news"}' \
  -w "\n" -s | jq -r '.resultMessage // .' 2>/dev/null || echo ""

curl -X POST "${BASE_URL}/topics" \
  -H "Content-Type: application/json" \
  -d '{"name":"Health & Wellness","description":"Health tips and wellness"}' \
  -w "\n" -s | jq -r '.resultMessage // .' 2>/dev/null || echo ""

curl -X POST "${BASE_URL}/topics" \
  -H "Content-Type: application/json" \
  -d '{"name":"Business","description":"Business news and updates"}' \
  -w "\n" -s | jq -r '.resultMessage // .' 2>/dev/null || echo ""

curl -X POST "${BASE_URL}/topics" \
  -H "Content-Type: application/json" \
  -d '{"name":"Entertainment","description":"Movies, music, and TV"}' \
  -w "\n" -s | jq -r '.resultMessage // .' 2>/dev/null || echo ""

echo "✅ Topics created!"
echo ""

# Create Subscribers
echo "=== Creating Subscribers ==="
curl -X POST "${BASE_URL}/subscribers" \
  -H "Content-Type: application/json" \
  -d '{"name":"sayali shevkar","email":"sayalishevkar@gmail.com"}' \
  -w "\n" -s | jq -r '.resultMessage // .' 2>/dev/null || echo ""

curl -X POST "${BASE_URL}/subscribers" \
  -H "Content-Type: application/json" \
  -d '{"name":"Prasanna kulkarni","email":"prasannakulkarni221@gmail.com"}' \
  -w "\n" -s | jq -r '.resultMessage // .' 2>/dev/null || echo ""

curl -X POST "${BASE_URL}/subscribers" \
  -H "Content-Type: application/json" \
  -d '{"name":"sangeeta shevkar","email":"sangeetashevkar01@gmail.com"}' \
  -w "\n" -s | jq -r '.resultMessage // .' 2>/dev/null || echo ""

echo "✅ Subscribers created!"
echo ""

# Subscribe Users to Topics
echo "=== Subscribing Users to Topics ==="
curl -X POST "${BASE_URL}/subscribers/1/subscribe/1" -w "\n" -s > /dev/null && echo "Sayali subscribed to Technology"
curl -X POST "${BASE_URL}/subscribers/1/subscribe/3" -w "\n" -s > /dev/null && echo "Sayali subscribed to Business"
curl -X POST "${BASE_URL}/subscribers/2/subscribe/1" -w "\n" -s > /dev/null && echo "Prasanna subscribed to Technology"
curl -X POST "${BASE_URL}/subscribers/2/subscribe/2" -w "\n" -s > /dev/null && echo "Prasanna subscribed to Health & Wellness"
curl -X POST "${BASE_URL}/subscribers/3/subscribe/3" -w "\n" -s > /dev/null && echo "Sangeeta subscribed to Business"
curl -X POST "${BASE_URL}/subscribers/3/subscribe/4" -w "\n" -s > /dev/null && echo "Sangeeta subscribed to Entertainment"

echo "✅ Subscriptions completed!"
echo ""

# Create Contents
echo "=== Creating Contents ==="
# Calculate scheduled time (2 minutes from now)
if [[ "$OSTYPE" == "darwin"* ]]; then
    # macOS
    SCHEDULED_TIME=$(date -u -v+2M +"%Y-%m-%dT%H:%M:%S" 2>/dev/null)
else
    # Linux
    SCHEDULED_TIME=$(date -u -d "+2 minutes" +"%Y-%m-%dT%H:%M:%S" 2>/dev/null)
fi

# Fallback if date command fails
if [ -z "$SCHEDULED_TIME" ]; then
    SCHEDULED_TIME="2024-12-20T10:00:00"
    echo "⚠️  Using default scheduled time: ${SCHEDULED_TIME}"
    echo "   (Update scheduledTime in contents to test email sending)"
else
    echo "📅 Scheduled time: ${SCHEDULED_TIME} (2 minutes from now)"
fi
echo ""

curl -X POST "${BASE_URL}/contents" \
  -H "Content-Type: application/json" \
  -d "{\"title\":\"AI Breakthrough\",\"text\":\"New AI model released\",\"topicId\":1,\"scheduledTime\":\"${SCHEDULED_TIME}\"}" \
  -w "\n" -s | jq -r '.resultMessage // .' 2>/dev/null || echo ""

curl -X POST "${BASE_URL}/contents" \
  -H "Content-Type: application/json" \
  -d "{\"title\":\"Smartphone Launch\",\"text\":\"New flagship phone unveiled\",\"topicId\":1,\"scheduledTime\":\"${SCHEDULED_TIME}\"}" \
  -w "\n" -s | jq -r '.resultMessage // .' 2>/dev/null || echo ""

curl -X POST "${BASE_URL}/contents" \
  -H "Content-Type: application/json" \
  -d "{\"title\":\"Morning Routine\",\"text\":\"5 habits for better health\",\"topicId\":2,\"scheduledTime\":\"${SCHEDULED_TIME}\"}" \
  -w "\n" -s | jq -r '.resultMessage // .' 2>/dev/null || echo ""

curl -X POST "${BASE_URL}/contents" \
  -H "Content-Type: application/json" \
  -d "{\"title\":\"Home Workouts\",\"text\":\"Best exercises at home\",\"topicId\":2,\"scheduledTime\":\"${SCHEDULED_TIME}\"}" \
  -w "\n" -s | jq -r '.resultMessage // .' 2>/dev/null || echo ""

curl -X POST "${BASE_URL}/contents" \
  -H "Content-Type: application/json" \
  -d "{\"title\":\"Market Update\",\"text\":\"Tech stocks surge\",\"topicId\":3,\"scheduledTime\":\"${SCHEDULED_TIME}\"}" \
  -w "\n" -s | jq -r '.resultMessage // .' 2>/dev/null || echo ""

curl -X POST "${BASE_URL}/contents" \
  -H "Content-Type: application/json" \
  -d "{\"title\":\"Startup Funding\",\"text\":\"$50M raised\",\"topicId\":3,\"scheduledTime\":\"${SCHEDULED_TIME}\"}" \
  -w "\n" -s | jq -r '.resultMessage // .' 2>/dev/null || echo ""

curl -X POST "${BASE_URL}/contents" \
  -H "Content-Type: application/json" \
  -d "{\"title\":\"Movie Review\",\"text\":\"Blockbuster breaks records\",\"topicId\":4,\"scheduledTime\":\"${SCHEDULED_TIME}\"}" \
  -w "\n" -s | jq -r '.resultMessage // .' 2>/dev/null || echo ""

curl -X POST "${BASE_URL}/contents" \
  -H "Content-Type: application/json" \
  -d "{\"title\":\"Music News\",\"text\":\"New album released\",\"topicId\":4,\"scheduledTime\":\"${SCHEDULED_TIME}\"}" \
  -w "\n" -s | jq -r '.resultMessage // .' 2>/dev/null || echo ""

curl -X POST "${BASE_URL}/contents" \
  -H "Content-Type: application/json" \
  -d "{\"title\":\"Cybersecurity Alert\",\"text\":\"New phishing attack\",\"topicId\":1,\"scheduledTime\":\"${SCHEDULED_TIME}\"}" \
  -w "\n" -s | jq -r '.resultMessage // .' 2>/dev/null || echo ""

curl -X POST "${BASE_URL}/contents" \
  -H "Content-Type: application/json" \
  -d "{\"title\":\"Economic Forecast\",\"text\":\"Growth predictions\",\"topicId\":3,\"scheduledTime\":\"${SCHEDULED_TIME}\"}" \
  -w "\n" -s | jq -r '.resultMessage // .' 2>/dev/null || echo ""

echo "✅ Contents created!"
echo ""

# Summary
echo "=========================================="
echo "✅ Test Complete!"
echo "=========================================="
echo ""
echo "Summary:"
echo "  • 4 Topics created"
echo "  • 3 Subscribers created"
echo "  • 6 Subscriptions created"
echo "  • 10 Contents created"
echo ""
echo "Next Steps:"
echo "  1. Wait for scheduled time (${SCHEDULED_TIME})"
echo "  2. Check application logs to see emails being sent"
echo "  3. Verify emails in subscriber inboxes"
echo ""
echo "To test immediately, create content with past scheduledTime"
echo "or wait for the scheduler to pick up the content."
echo ""

