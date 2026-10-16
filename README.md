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

## AI-assisted development

This repository includes selected, repo-local skills from [Matt Pocock's skills repository](https://github.com/mattpocock/skills) for use with OpenCode. The skills are copied into `.agents/skills/` and tracked in `skills-lock.json` so the workflow is reproducible.

Use `/grill-with-docs` before substantial design work, `/to-spec` and `/to-tickets` to turn agreed work into GitHub Issues, and `/implement` to build it with testing and review. The project-specific rules in `AGENTS.md` remain authoritative for Spring Boot, Vue, security, and repository conventions.

### Updating the skills

Review updates before committing them:

```bash
npx skills@latest update --project
npx skills@latest add mattpocock/skills --agent opencode --skill setup-matt-pocock-skills --skill grill-with-docs --skill to-spec --skill to-tickets --skill implement --skill tdd --skill code-review --skill diagnosing-bugs --skill retro --skill improve-codebase-architecture --skill grilling --skill domain-modeling --skill codebase-design --skill writing-for-agents --copy --yes
git diff
```

Re-run the `add` command when Matt adds a new skill that you want to adopt. Do not install the skills globally for this repository.
