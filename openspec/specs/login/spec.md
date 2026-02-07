# Login

## Overview

Users authenticate with email and password. The app uses session-based auth with CSRF protection. The frontend is a single form; the backend uses Spring Security form login.

## User flow

1. User visits `/login` (unauthenticated or after session expiry).
2. Frontend loads; fetches CSRF token from `GET /api/csrf` (credentials included).
3. User enters email and password and submits.
4. Frontend sends `POST /login` with body `username`, `password`, `_csrf` and header `X-XSRF-TOKEN`, credentials included.
5. **Success (200):** Frontend redirects to `/`.
6. **Failure (401):** Frontend shows "Invalid email or password" (no distinction between wrong email vs wrong password).
7. User can click "Back to home" to go to `/` without logging in.

## API / backend contract

- `GET /api/csrf` — Returns `{ "token": "<csrf-token>" }`. Required before POST to `/login`. Cookie `XSRF-TOKEN` may also carry the token.
- `POST /login` — Form login (application/x-www-form-urlencoded). Params: `username` (email), `password`, `_csrf`. Response: 200 success, 401 invalid credentials.
- `GET /api/me` — Returns current user `{ "id", "email" }` or 401 if not authenticated.
- Public routes: `/api/hello`, `/api/csrf`, `/login`, `/logout`. All other `/api/**` require authentication.

## UI

- Form: Email (type=email, required), Password (type=password, required), Submit button.
- Submit shows "Signing in..." and disables button while loading.
- Error message shown below form on failure.
- Link "Back to home" to `/`.

## Non-goals (current behavior)

- No "forgot password" or "remember me".
- No rate limiting or lockout specified in this spec.
- Generic error message only (no email enumeration).
