# Application Specification (High-Level)

## Purpose

A **circular economy application** for clothing that helps users:

- **A) Find pre-owned clothes** — Browse and acquire secondhand items from other users
- **B) Find new owners for clothes** — List and pass on clothes they no longer need
- **C) Notify the right-sized user** — When a garment doesn't fit its owner and gets listed, the app notifies users whose size and style match it about the potentially fitting garment
- **D) Personal registry** — Users can use the app as a registry of garments they own. Each item has a visibility state: private, friends only, public, or give away

The app extends the life of garments, reduces waste, and supports sustainable consumption. Proactive notifications help right-sized users discover garments before others. **Location-aware:** the app prioritizes close-proximity connections to make handoffs easier and more sustainable (less shipping).

---

## Core Product Vision

| Pillar | Description |
|--------|-------------|
| **Acquire** | Users discover and obtain pre-owned clothes listed by others |
| **Pass on** | Users list their own clothes and connect with people who want them |
| **Registry** | Personal inventory of owned garments. Each item has a state: **private** (owner only), **friends only**, **public**, or **give away** |
| **Match & notify** | When a garment doesn't fit the owner and is listed, the app notifies right-sized users (by size and style) about the potentially fitting garment |
| **Location-aware** | Prioritizes close-proximity connections so users can meet or exchange locally |
| **Connect (friend)** | Users can connect with each other; friends get enhanced access to each other's listings (e.g. early view, priority in matches) |

*(Exact mechanics—marketplace, swap, donation, matching algorithm, notification channels, location handling, friend connections—to be defined in future specs.)*

**Privacy:** Users control how detailed their body measurements are. Options may range from basic (e.g. S/M/L) to precise (e.g. chest, waist, inseam). More detail enables better matching; less detail preserves privacy.

**Fit estimate:** When listing a garment, the user can indicate their own estimate of whether it runs true to size, runs small, or runs big. This helps others judge if the item might fit them.

**Item visibility states:** Each garment in a user's registry has a state controlling who can see it:
- **Private** — Owner only
- **Friends only** — Owner + connected friends
- **Public** — Anyone
- **Give away** — Owner wants to pass it on; visible to eligible users (e.g. right-sized, nearby)

---

## MVP Scope

**The MVP supports jeans only.** Browse, list, match, and notify flows apply to jeans. Other garment types (tops, dresses, etc.) may be added in later releases.

---

## Scope

- **In scope:** User registration (with jeans size: waist range + length), login, logout, session-based auth, protected home page, CSRF protection. *(Core product features—browse jeans, list jeans, match—to be added.)*
- **Out of scope:** Social login, email verification, password reset, rate limiting, account lockout. Other garment types beyond jeans (post-MVP).

---

## Features

### Authentication

| Feature | Description |
|---------|-------------|
| **Registration** | New users sign up with email, password, jeans size estimate (waist range e.g. 31–32, length e.g. 32), and height (cm). Confirm password, terms acceptance, and password visibility toggle. Redirects to login on success. |
| **Login** | Existing users sign in with email and password. Session created on success. |
| **Logout** | Invalidates session. |
| **Session** | Cookie-based; no JWT. All state-changing requests require CSRF token. |

### Application (current)

| Feature | Description |
|---------|-------------|
| **Home** | Protected page. *(Will evolve into browse/dashboard for circular economy flows.)* |
| **Terms / Privacy** | Placeholder pages linked from registration. |

### Application (planned, MVP = jeans only)

| Feature | Description |
|---------|-------------|
| **Browse pre-owned jeans** | Discover jeans listed by other users (visibility depends on item state) |
| **List jeans** | Add jeans to your registry. Set state per item: private, friends only, public, or give away. For each item, the user can give their own estimate of fit: **true to size**, **runs small**, or **runs big** |
| **Size & style profile** | Users set their size and style preferences for jeans. Each user decides how detailed their body measurements are (e.g. basic S/M/L vs. precise measurements) to support matching |
| **Match & notify** | When a non-fitting pair of jeans is listed, notify right-sized users whose style matches it about the potentially fitting garment |
| **Location-aware matching** | Prioritize close-proximity connections; nearby users surface first |
| **Connect (friend)** | Users can friend each other; friends get enhanced access to each other's jeans listings (e.g. early view, priority in matches) |

### Security

- Session-based auth only (no JWT)
- CSRF protection via `XSRF-TOKEN` cookie and `X-XSRF-TOKEN` header
- Generic error messages (no email enumeration)
- Passwords hashed with BCrypt

---

## User Flows

```
Unauthenticated                    Authenticated
       |                                  |
       v                                  v
  /login, /register  ---------->  / (home)
       |                    |
       |                    +-> /logout --> /login
       |
       +-> Sign up --> /login --> Sign in --> /
```

- Unauthenticated users visiting `/` are redirected to `/login`
- Authenticated users visiting `/login` or `/register` are redirected to `/`
- Terms and Privacy pages are publicly accessible

---

## Tech Stack

| Layer | Technology |
|-------|------------|
| Backend | Spring Boot 3.2, Java 17, Spring Security, JPA, H2 (dev) |
| Frontend | Vue 3, Vue Router, Vite |
| Deployment | Docker, Fly.io (deploy on push to main) |

---

## Architecture

- **Monolith:** Single Spring Boot app serves REST API and static frontend in production
- **Database:** H2 in-memory for dev; configurable for production
- **API:** REST, JSON. Public endpoints: `/api/message`, `/api/csrf`, `/api/register`, `/login`, `/logout`. Protected: `/api/**`
- **Frontend:** SPA with lazy-loaded routes. All API calls via `src/api/client.js` (credentials + CSRF)

---

## Non-Goals

- Forgot password, remember me
- OAuth / social login
- JWT or token-based auth
- Email verification
- Rate limiting, account lockout
- Email enumeration in error messages
