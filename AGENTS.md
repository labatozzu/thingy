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

- **Login flow:** `openspec/specs/login/spec.md` — canonical spec for auth, API contract, UI, non-goals
- **Architecture details:** `.cursor/rules/architecture.mdc` — auth, API patterns, security decisions
- **Conventions:** `.cursor/rules/conventions.mdc` — coding standards and patterns

## Before Making Changes

1. **Auth-related changes:** Read `openspec/specs/login/spec.md` and `.cursor/rules/architecture.mdc`. Preserve session-based auth, CSRF, and generic error messages (no email enumeration).
2. **New API endpoints:** Follow existing patterns in `AuthController` and `MessageController`. Public routes must be added to `SecurityConfig` permit list.
3. **Frontend API calls:** Use `src/api/client.js` (get/post) — it handles credentials and CSRF for POST. Never bypass it for authenticated requests.
4. **New features:** Consider using OpenSpec (`/opsx:new` or `/opsx:ff`) to create proposal → specs → design → tasks before implementing.
