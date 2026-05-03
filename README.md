# AI Interview Assistant (Spring Boot + PostgreSQL + WebSocket)

Real-time helper-to-receiver interview answer system with unique receiver ID routing.

## Tech Stack
- Spring Boot (REST + STOMP WebSocket)
- PostgreSQL (users, questions, message_history, logs)
- HTML/CSS/JavaScript frontend (`/static`)

## Modules
- Receiver: login, unique ID, live answers on `/user/queue/answers`, last 10 history cards.
- Sender: login, receiver ID connect, send question via WebSocket `/app/send` or REST.
- Admin: users/status, active sessions, question CRUD, logs, manual override.

## Run
1. Create PostgreSQL DB: `interview_assistant`.
2. Update DB credentials in `src/main/resources/application.properties`.
3. Start app:
   - `mvn spring-boot:run`
4. Open:
   - `http://localhost:8080/`

## Demo Users (seeded)
- admin / admin123
- sender1 / sender123
- receiver1 / receiver123 (uniqueId: `rcv1001`)

## Architecture Notes
- Clean layering with config/controller/service/repository/entity.
- DB-first answer retrieval using `ILIKE` keyword search (`LIMIT 1`).
- AI fallback currently mocked in `AnswerService` (Gemini integration point marked).
- Session tracking in memory for multi-user online/offline handling.

## Future Roadmap
// JWT authentication add karna hai  
// Cloud deployment (Render / AWS / Railway)  
// HTTPS + WSS enable karna hai  
// AI model upgrade karna hai  
// Horizontal scaling for multiple servers  
