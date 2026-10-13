# VSMS — Application Documentation

**Vehicle Service Management System** — a web application that connects vehicle
owners with authorised service centres, and gives each centre and the platform
administrator their own private workspace.

> Companion documents: [`DEPLOY.md`](./DEPLOY.md) for installation, build and
> operations. This document describes **what the system is, how it behaves, and
> how its roles are designed**.

---

## 1. What this application does

VSMS replaces the phone-call/WhatsApp round trip of arranging car servicing
with a searchable directory, slot booking, live job tracking and invoicing.

The whole product is built around three steps:

| Step | What happens |
|---|---|
| **Find your centre** | Search verified service centres by city, area and vehicle brand; compare packages, facilities and ratings side by side. |
| **Book a slot** | Register a vehicle, pick a fixed-price package, choose a date within the next 7 days and confirm. A booking reference is issued immediately. |
| **Track & drive** | Follow the job live through every stage, watch it become an invoice, settle it and collect the car. |

### 1.1 Functional areas

**1. Discovery and comparison (public)**
A visitor can search centres by **City → Area → Brand** without signing in.
Results show the centre's name, brand specialism, contact number, email,
address and rating. Details beyond that require an account.

**2. Registration and onboarding**
- *Customers* self-register and are active immediately.
- *Service centres* self-register but land in a **pending** state and cannot
  sign in until an administrator approves them.
- Both roles recover a forgotten password through a security question.

**3. Booking and scheduling**
A customer selects a centre and one of that centre's packages, chooses a date,
and books against a vehicle registered on their account. The backend enforces
the centre's configurable **booking limit** so a day cannot be overbooked.

**4. Job execution and live tracking**
Every service request moves through five recorded stages:

```
check-in → inspection → repair → quality check → delivery
(checkin)  (stageone)   (stagetwo) (checkout)     (delivered)
```

The service centre advances the stages; the customer watches them on a timeline.

**5. Invoicing and payment**
Completing a job produces an invoice joined to a transaction record (amount,
date, payment mode). Invoices are attached to the customer's service history.

**6. Ratings and reviews**
A customer scores any centre they have used. Aggregate ratings feed back into
the public search results, so better centres surface first.

### 1.2 What this application deliberately does *not* do

- **No payment gateway.** Payments are *recorded* (amount, date, mode) but not
  processed — there is no card/UPI integration.
- **No notifications.** No email, SMS or push; confirmation is in-app only.
- **No maps or geocoding.** Cities and areas are text/dropdown data, not
  coordinates.
- **No package-authoring screen.** The `CreatePackages` component exists but is
  not wired into any route, so packages are currently data-driven only.
- **Single site, single language, INR assumed.**

---

## 2. Is this a public directory, or an app for one individual service centre?

### It is a **public, multi-tenant directory — not an individual-centre app.**

The application is a **marketplace/directory** in which *many* service centres
are listed side by side and drivers search, compare and choose between them.
Each centre then gets its own **private workspace** scoped to itself, and a
platform administrator sits above both.

Think "directory of garages" rather than "one garage's booking page".

### 2.1 Evidence from the running system

| Surface | Scope of data shown | Who can reach it |
|---|---|---|
| `/viewservicecenters` — labelled **"Service Centres"** in the main nav | **All** centres matching the brand + area filter | **Public** — no sign-in |
| Home page | Platform-wide | **Public** |
| Customer → "Browse service centres" | **All** listed centres | Signed-in customer |
| Customer → "Book appointment" | Picks **any** listed centre + its package | Signed-in customer |
| Customer → "Rate a service" | Fetches `/allservicecenters`, rates **any** of them | Signed-in customer |
| Customer → "Service progress" | Only **their own** jobs (`getSerReqsByCid?cid=`) | Signed-in customer |
| Service centre dashboard | Only **its own** record (`getScDetailsByLoginid` → its `scid`) | Signed-in centre |
| Service centre → "Update service progress" | Only **its own** jobs (`getAllServiceReqsByDateSc?scid=`) | Signed-in centre |
| Admin dashboard | All accounts + the approval queue | Signed-in admin |

Two facts settle the question:

1. **The public search returns a *list* of competing centres.** The endpoint is
   `GET /getScByBrandArea?bid=&aid=` and it renders multiple rows — a
   single-centre app has nothing to compare.
2. **A logged-in centre sees only itself.** Its `scid` is derived from
   `getScDetailsByLoginid?loginid=`, never chosen by the user, and is what every
   subsequent query is scoped by.

### 2.2 What an anonymous visitor gets

The **"Service Centres"** page is in the *public* navigation (visible before
login) and offers three filters — City, Area, Brand — returning:

| Name | Brand | Contact Number | Email | Address | Ratings |
|---|---|---|---|---|---|

followed by *"To View More Details Please Login"* with Login / Register buttons.
So discovery is genuinely public; **transacting requires an account**.

### 2.3 Data isolation summary

```
                        ┌──────────────────────────────┐
     PUBLIC (no auth)   │  Home · Service Centres      │
                        │  Login · Sign up             │
                        └──────────────┬───────────────┘
                                       │ sign in
        ┌──────────────────────────────┼──────────────────────────────┐
        │ roleid = 1                   │ roleid = 2                   │ roleid = 3
        ▼                              ▼                              ▼
┌───────────────────┐        ┌───────────────────┐        ┌───────────────────┐
│ CUSTOMER          │        │ SERVICE CENTRE    │        │ ADMIN             │
│                   │        │                   │        │                   │
│ All centres       │  ───►  │ Only its own      │        │ All accounts      │
│ My vehicles       │ books  │ Only its own jobs │        │ Approve / reject  │
│ My bookings       │        │ Its own packages  │        │ centres           │
│ My ratings        │        │ Its own listing   │        │ View public site  │
│ Own job history   │        │                   │        │                   │
└───────────────────┘        └───────────────────┘        └───────────────────┘
```

### 2.4 One caveat worth knowing

Approval is **not** currently applied to the public listing. A centre is
inserted into `servicecenters` at registration time, while only the `login`
row carries the `status` flag — and `/getScByBrandArea` does not join on it.
I verified this directly: a centre registered with `status = 0` (pending) was
returned by the public search. So today an **unapproved centre is publicly
listed before an admin has seen it**. See §7.

---

## 3. How the roles are designed

### 3.1 The data model behind a role

Roles are **data, not hard-coded branches**. Three tables carry the design:

**`roles`** (reference table)

| roleid | rolename |
|---:|---|
| 1 | Customer |
| 2 | Service Center |
| 3 | Admin |

**`login`** — exactly one row per account, and the *only* place a role is
attached:

| Column | Meaning |
|---|---|
| `loginid` | Primary key, used to join to the profile tables |
| `userid` | What the user types to sign in — **UNIQUE** across all roles |
| `password` | **PBKDF2-HMAC-SHA1** hash (10,000 iterations, 256-bit key, Base64) with the configured salt — the plaintext is never stored |
| `answer`, `questionid` | Security question and its answer, for recovery |
| `status` | `1` = active/approved · `0` = pending |
| `roleid` | FK → `roles`, and the single source of truth for "who is this" |

**Profile tables**, each joined back to `login` by `loginid`:

| roleid | Profile table | Created |
|---:|---|---|
| 1 | `customers` | Automatically at self-registration |
| 2 | `servicecenters` | Automatically at self-registration |
| 3 | *(none)* | The admin account is seeded; its dashboard uses a fixed identity |

This is a clean **role → one profile** design: adding a new actor means a new
role row plus a new profile table, without touching the login flow.

### 3.2 What each role can do

**Customer (roleid 1)** — self-service, active on signup
- Register vehicles on their account
- Browse and compare **all** listed centres
- Book a package at any centre
- Track their own jobs on a timeline
- View invoices and service history
- Rate a centre
- Change password / recover via security question

**Service Center (roleid 2)** — tenant workspace, **requires approval**
- Register a centre (brand, area, booking limit, credentials)
- Advance its own jobs through the five stages
- View the packages its jobs reference
- Inspect how it appears in the public listing
- Change password
- **Cannot** see other centres' jobs or customers' full histories

**Admin (roleid 3)** — platform oversight, seeded only
- Review registered accounts and **approve or reject** service centres
- See all accounts and their status
- Change password
- Preview the public site
- **Cannot** be self-registered

### 3.3 The authentication flow

```
LoginPage  ──POST /logincheck {userid, password}──►  Backend
                                                   hashes with salt,
                                                   matches on userid
        ◄── 200 + {loginid, userid, roleid, ...} ──  ✅ correct password
        ◄── 200 + empty body ──────────────────────  ❌ wrong password
        ◄── 403 {"error":"pending","message":…} ───  ⏳ centre awaiting approval

Frontend stores:  localStorage.roleid / loginid / userid   +  Redux `logged`
                  └──────────── routes the user ─────────────┘

   roleid 1  ─►  /customerhome
   roleid 2  ─►  /serviceHome
   roleid 3  ─►  /adminHome
```

Notable design decisions:

- **Login is matched on `userid` alone**, with the password compared as a
  PBKDF2-HMAC-SHA1 hash rather than in plaintext. The salt is externalised as
  `spring.saltvalue` in configuration — changing it re-hashes every stored
  password, so it must be treated as a permanent secret.
- **Three distinct outcomes** rather than a generic failure, so the UI can tell
  "wrong password" apart from "waiting for approval" — the distinction that
  makes the pending state understandable to a new centre.
- **Password policy is identical on login and change** (≥ 8 chars, one
  uppercase, one lowercase, one digit, one of `!@#$%^&*`), so any password the
  form will accept is also a password that will sign you in.
- **Role decides routing, not permissions.** `roleid` is read to pick the
  dashboard and to resolve the *"My dashboard"* nav item; it is not consulted
  again afterwards (see §7).

**Password recovery** runs through two steps — `POST /checkAnswer` verifies the
security answer, then `/changePwd` applies the new password. Both are
URL-encoded so special characters such as `#` and `&` survive the round trip.

### 3.4 The approval workflow (role 2 only)

```
 Service centre registers
        │
        ▼
 login.status = 0  +  servicecenters row inserted   (one transaction)
        │
        ▼
 tries to sign in  ──►  403 {"error":"pending",
                             "message":"This account is awaiting approval…"}
        │
        ▼
 Admin opens the requests screen, sees the account
        │
        ▼
 PATCH /updateReq/{loginid}  {"status": true}
        │
        ▼
 login.status = 1  ──►  sign-in succeeds, dashboard opens
```

Customers skip this entirely (`status = true` on registration). This
asymmetric design is deliberate: **drivers get zero friction, while the
directory is meant to be curated** — which makes the gap in §2.4 the more
important thing to close.

### 3.5 Where role design is enforced — and where it isn't

| Control | Status |
|---|---|
| Sign-in routes the user to the right dashboard | ✅ via `roleid` |
| Each dashboard renders only if signed in | ✅ `loggedIn === false → /userlogin` |
| Each dashboard renders only for the **correct** role | ❌ **no check** — a signed-in customer can open `/adminHome` |
| Backend endpoints require a session or token | ❌ none — every endpoint is open |
| Backend verifies the caller owns the `scid` / `cid` it is given | ❌ passed as query parameters, trusted as-is |
| `GET /all` restricted to administrators | ❌ returns every account's **password hash and security answer** to any caller |
| CORS restricts browser origins | ✅ but only `http://localhost:3000`, and CORS is not an authentication control |

Roles are therefore currently **an interface concern, not a security boundary**:
they decide what a user sees, but nothing on the server insists *who* is
asking. See §7 for the recommended remediation order.

---

## 4. Feature map by role

| Feature | Public | Customer | Service Centre | Admin |
|---|:--:|:--:|:--:|:--:|
| Home / marketing | ✅ | ✅ | ✅ | ✅ |
| Search service centres | ✅ | ✅ | ✅ | ✅ |
| Sign in / forgot password | ✅ | — | — | — |
| Register as customer | ✅ | — | — | — |
| Register as service centre | ✅ | — | — | — |
| Register vehicles | ❌ | ✅ | ❌ | ❌ |
| View own vehicles | ❌ | ✅ | ❌ | ❌ |
| Remove a registered vehicle | ❌ | ✅ | ❌ | ❌ |
| Book an appointment | ❌ | ✅ | ❌ | ❌ |
| Track own service progress | ❌ | ✅ | ❌ | ❌ |
| View own invoices | ❌ | ✅ | ❌ | ❌ |
| Rate a centre | ❌ | ✅ | ❌ | ❌ |
| Advance job stages (check-in → delivery) | ❌ | ❌ | ✅ | ❌ |
| Manage service packages | ❌ | ❌ | ✅ | ❌ |
| View own centre's listing | ❌ | ❌ | ✅ | ❌ |
| Approve / reject centre accounts | ❌ | ❌ | ❌ | ✅ |
| View all accounts | ❌ | ❌ | ❌ | ✅ |
| Change own password | — | ✅ | ✅ | ✅ |

Service packages are a full CRUD screen now: create, edit and delete, guarded server-side against foreign-key clashes and against one centre editing another centre's package. See `DEPLOY.md` section 9.1.

---

## 5. Domain model

```
City ──< Area ──< ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ┐
                                      │
Brand ────────────────────────────────┤
                                      │
Question ──< Login >──┬── Customer ───┴──< Vehicle >── Brand
                      │       │
                      │       └──< Rating >── ServiceCenter
                      │
                      └── ServiceCenter >── Area, Brand
                                │
                                ├──< PackageDetails >── Facility
                                │
                                └──< ServiceRequest >── Vehicle
                                        │
                                        ├──< ServiceProgress
                                        │         │
                                        │         └──< Transaction
                                        │                    │
                                        └──< Invoice >───────┘
```

| Entity | Key fields | Notes |
|---|---|---|
| **City → Area** | name, pincode | Location hierarchy used by every search |
| **Brand** | name | Centres specialise in one; vehicles belong to one |
| **Login** | userid, password, status, roleid | One per account; the role holder |
| **Customer** | name, contact, email, address, birthdate | Profile for roleid 1 |
| **ServiceCenter** | name, contact, email, address, **bookinglimit**, brand, area | Profile for roleid 2 |
| **Vehicle** | number, model, fuel type, reg year, brand | Owned by a customer |
| **PackageDetails** | name, cost, centre, facilities | What a customer actually books |
| **ServiceRequest** | booking date, pickup time, service date, status | The booking itself |
| **ServiceProgress** | checkin, stageone, stagetwo, checkout, delivered | The live five-stage timeline |
| **Transaction** | amount, date, payment mode | Attached to a progress record |
| **Invoice** | status, transaction | Attached to a service request |
| **Rating** | score, comment | Customer × ServiceCenter |

---

## 6. End-to-end journeys

### 6.1 A driver books a service
1. Browses **Service Centres** (no account needed) → filters by city/area/brand.
2. Signs up → lands on the customer dashboard with `custid` resolved.
3. Registers a vehicle (brand + number + model + fuel + reg year).
4. Opens **Browse service centres** → picks a centre **and a package** → the
   centre id, package id and brand id are carried forward.
5. Chooses a date within 7 days → booking created; booking limit enforced.
6. Watches **Service progress** advance through the five stages.
7. On delivery, views the invoice and pays — the centre records the transaction.
8. Rates the centre, which updates the public listing.

### 6.2 A service centre joins the platform
1. Fills in **Service Centre Registration** (name, contact, address, booking
   limit, brand, area, credentials, security question).
2. On a duplicate user id it is told so explicitly (`409`); invalid brand/area or
   a zero booking limit are rejected with a message before anything is written.
3. The `login` and `servicecenters` rows are written **in one transaction**, so a
   failure can never leave a half-created account.
4. Sign-in attempt returns **403 "awaiting approval"**.
5. The administrator approves it → sign-in succeeds → the centre's own
   dashboard opens, scoped to its own `scid`.

### 6.3 The administrator curates the directory
1. Signs in with the seeded admin account.
2. Opens **Service requests** → all accounts with their status.
3. Approves or rejects a pending centre (`PATCH /updateReq/{loginid}`).
4. Changes their own password as needed.

---

## 7. Known gaps and recommended next steps

Ordered by severity. Each was confirmed against the running system.

1. **Server-side authorization is missing entirely.** No endpoint requires a
   session or token, and role selection happens only in the browser. Anyone who
   can reach the API can call it. *Fix:* introduce Spring Security with JWT (or
   server sessions) and require authentication on every non-public endpoint.

2. **`GET /all` leaks credential material.** It returns every account's salted
   **password hash and security answer** with no authentication. *Fix:* restrict
   it to roleid 3 and project a safe DTO (`loginid`, `userid`, `status`,
   `roleid`) — never `password` or `answer`.

3. **No role guard on the dashboards.** Each dashboard checks only that you are
   signed in, not that you are signed in as the right role — a customer can
   navigate to `/adminHome` and see the approval controls. *Fix:* a route guard
   that compares `roleid` against the route's required role.

4. **Unapproved centres are publicly listed** (§2.4). *Fix:* have the public
   search join `login` and filter `status = 1`.

5. **Object-level access is trusted.** `scid` and `cid` are supplied by the
   client as query parameters; the server never confirms the caller owns them.
   *Fix:* derive the tenant id from the authenticated principal instead.

6. **Hardcoded environment.** Every fetch targets `http://localhost:8080`, CORS
   allows only `localhost:3000`, and the database password sits in plaintext in
   `application.properties`. All three must change before deployment — detailed
   in `DEPLOY.md` §5.

7. **Remaining unwired screens.** `Invoice`, `TrackProcess`, `ContactUs`,
   `Transaction` and `Multiple` still exist in the source tree but are not
   registered in `App.js`, so they are unreachable. None of them is a loss:
   invoices are shown inside `ServiceProgress`, tracking is `ServiceProgress`,
   and `Transaction` is rendered by `UpdateServiceProgress` as a checkout
   dialog. `CreatePackages` was in this list and is now routed at
   `/serviceHome/packages`. Either delete the dead files or give each a real
   destination.

8. **No notification layer.** Booking confirmations and delivery alerts are
   in-app only.

---

*Prepared against the application as verified on the running stack (Spring Boot
2.7.8 · MySQL 8 · React 18). All behaviours described above were tested against
the live API, not inferred from source alone.*