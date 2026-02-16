# Minimal Spring Boot + Vue.js

Minimal full-stack app: Spring Boot backend, Vue 3 + Vite frontend. The Vue app displays the message from `GET /api/message`.

## Requirements

- Java 17
- Node.js 18+ and npm
- Maven 3.6+

## Development

**1. Start the backend**

```bash
cd backend
mvn spring-boot:run
```

Backend runs at http://localhost:8080. Test: http://localhost:8080/api/message

**2. Start the frontend**

```bash
cd frontend
npm install
npm run dev
```

Frontend runs at http://localhost:5173. It proxies `/api` to the backend.

## Production build

**1. Build the frontend**

```bash
cd frontend
npm run build
```

**2. Copy frontend build into backend**

Copy the contents of `frontend/dist/` into `backend/src/main/resources/static/`.

**3. Build and run the backend**

```bash
cd backend
mvn package
java -jar target/backend-1.0.0-SNAPSHOT.jar
```

Open http://localhost:8080. The same JAR serves the API and the Vue app.
