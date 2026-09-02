# Project Context for AI Assistants

This document provides context about the myproject codebase so AI can respect existing patterns and decisions when creating new features.

## Tech Stack

- **Backend:** Spring Boot (Java 17), Spring Security, JPA/H2
- **Frontend:** Vue 3, Vue Router, Vite
- **Deployment:** Docker, Fly.io (deploy on push to main)

## Architecture Overview

- **Monolith:** Single Spring Boot app serves API and static frontend in production
- **Auth:** Session-based authentication with CSRF protection. No JWT. Form login at `POST /login`.
- **Frontend:** SPA with lazy-loaded route components. API calls go through `src/api/client.js` with credentials and CSRF handling.

## Key Documentation

- **Login flow:** Session-based auth with CSRF. `POST /login` with `username`, `password`, `_csrf`. Success → 200, failure → 401 with generic "Invalid email or password". See `.cursor/rules/architecture.mdc`.
- **Architecture details:** `.cursor/rules/architecture.mdc` — auth, API patterns, security decisions
- **Conventions:** `.cursor/rules/conventions.mdc` — coding standards and patterns

## Before Making Changes

1. **UX:** Aim for familiar patterns from popular web apps. See `.cursor/rules/conventions.mdc` — Design & UX.
2. **Security:** Follow secure coding principles and consider OWASP Top 10. See `.cursor/rules/conventions.mdc` — Security.
3. **Auth-related changes:** Read `.cursor/rules/architecture.mdc`. Preserve session-based auth, CSRF, and generic error messages (no email enumeration).
4. **New API endpoints:** Follow existing patterns in `AuthController` and `MessageController`. Public routes must be added to `SecurityConfig` permit list.
5. **Frontend API calls:** Use `src/api/client.js` (get/post) — it handles credentials and CSRF for POST. Never bypass it for authenticated requests.
6. **New features:** Plan in conversation before implementing. Follow conventions in `.cursor/rules/conventions.mdc`.
