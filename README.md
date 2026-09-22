# Varsivo
A student focused mobile app that helps learners manage university applications, discover institutions and bursaries and track their application journey in one place.

# Varsivo API

Backend REST API for **Varsivo** — an Android app that helps South African students find universities, calculate their APS score, discover bursaries, and track their applications in one place.

This repository is the Node.js/Express backend. It connects to an Azure SQL Database and serves the endpoints the Varsivo Android app calls for authentication, institutions, bursaries, applications, and user settings.

## Purpose

The Varsivo frontend was originally built as a UI-only prototype with mock/local data. This API replaces that mock layer with a real backend: user accounts, JWT authentication, and CRUD-backed data for institutions, bursaries, and student applications, all persisted in Azure SQL.

## Tech stack

- Node.js / Express
- Azure SQL Database (via `mssql`)
- JWT (`jsonwebtoken`) for auth tokens
- `bcryptjs` for password hashing
- `express-validator` for request validation
- `helmet`, `cors`, `express-rate-limit` for basic security hardening

## Design

- **Layered structure**: `routes` → `controllers` → `services` (data access), following a standard Express separation of concerns.
- **Auth**: `POST /api/auth/register` and `POST /api/auth/login` return a JWT; protected routes require `Authorization: Bearer <token>` and are guarded by `middleware/auth.js`.
- **Data model**: `users`, `institutions`, `bursaries`, `applications`, `deadlines` tables in Azure SQL, with `applications` foreign-keyed to both `users` and `institutions`.

## Setup

1. Clone the repo and install dependencies:
   ```
   npm install
   ```

2. Copy `.env.example` to `.env` and fill in your own values:
   ```
   PORT=3000
   NODE_ENV=development
   JWT_SECRET=<generate with: node -e "console.log(require('crypto').randomBytes(32).toString('hex'))">
   JWT_EXPIRES_IN=7d

   DB_SERVER=<your-azure-sql-server>.database.windows.net
   DB_DATABASE=<your-database-name>
   DB_USER=<your-admin-username>
   DB_PASSWORD=<your-admin-password>
   DB_PORT=1433
   ```

3. Confirm the database connection:
   ```
   node test-db.js
   ```
   Should print `✅ Connection successful`.

4. Start the server:
   ```
   npm start
   ```
   Runs on `http://localhost:3000` by default.

## API Endpoints

| Method | Endpoint | Auth required | Description |
|---|---|---|---|
| POST | `/api/auth/register` | No | Create a new user account |
| POST | `/api/auth/login` | No | Log in, returns a JWT |
| GET | `/api/users/me` | Yes | Get the logged-in user's profile |
| PATCH | `/api/users/:id/settings` | Yes | Update language/notification preferences |
| GET | `/api/institutions` | No | List institutions, optionally filtered by `?aps=` |
| GET | `/api/bursaries` | No | List available bursaries |
| GET | `/api/applications` | Yes | List the logged-in user's applications |
| POST | `/api/applications` | Yes | Create a new application |
| GET | `/health` | No | Health check |

## Testing

- `node test-db.js` — verifies the Azure SQL connection independently of the API.
- Manual endpoint testing was done with Thunder Client / Postman against a locally running server connected to Azure SQL. See the attached screenshots for verified request/response pairs covering registration, login, settings, institutions, bursaries, and applications.

## Continuous Integration

A GitHub Actions workflow (`.github/workflows/ci.yml`) runs on every push and pull request: it installs dependencies on a clean Ubuntu runner to confirm the project builds cleanly.

```yaml
name: CI
on: [push, pull_request]
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - uses: actions/setup-node@v3
        with: { node-version: '20' }
      - run: npm install
```

## Known limitations (prototype scope)

- Password reset (`/auth/forgot-password`) and Google Sign-In are not yet backed by real email delivery / OAuth — planned for a future iteration.
- Push notifications and offline-first sync are not implemented in this phase.
- Bursary and institution data is seeded manually rather than pulled from a live external source.

## Related repositories

- Frontend (Android/Kotlin): see the Varsivo frontend repository for the client app that consumes this API.

