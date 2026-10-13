# Deployment Guide — VSMS (Vehicle Service Management System)

Verified working locally on: Java 17 · Maven 3.9 · Node 24 · MySQL 8.0 · React (CRA)

---

## 1. Project layout

| Part      | Path                                          | Tech                     |
|-----------|-----------------------------------------------|--------------------------|
| Frontend  | `React/`                                      | Create React App (React 18) |
| Backend   | `SpringBoot/demo/`                            | Spring Boot 2.7.8        |
| Database  | `Database/VSMS+data.sql`                      | MySQL 8 schema + seed    |

---

## 2. Database

```bash
mysql -u root -p < "Database/VSMS+data.sql"
```

Creates the `vsms` database with 17 tables. Local password in use: `yuva@2005`.

The seed now loads a complete, working demo:

| Table | Rows | Notes |
|-------|------|-------|
| `city` | 6 | Pune, **Hyderabad**, Bengaluru, Mumbai, Delhi, Chennai |
| `area` | 44 | 10 each for Pune and Hyderabad, 6 for the rest |
| `login` | 6 | all with password `Vsms@123` (see 12) |
| `customers` / `servicecenters` | 3 / 2 | one demo centre per demo centre account |
| `packagedetails` / `packages` | 5 / 36 | both demo centres ship with priced packages |

Two defects in the original dump are fixed here:

* `customers` was written in the order `(birthdate, emailid, lane, contactno)`
  while the table declares `(birthdate, contactno, emailid, lane)`, so a fresh
  install had every customer's phone, email and street swapped by one column.
* `login` shipped three pre-salt password hashes, so the credentials documented
  in this guide did not work on a fresh install.

Loading the seed is idempotent-checked: it is safe to load into an empty
server, and it has been verified to load with zero foreign-key errors.

---

## 3. Backend

```bash
cd SpringBoot/demo
mvn -q -B clean package
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

`src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/vsms
spring.datasource.username=root
spring.datasource.password=<YOUR_DB_PASSWORD>
server.port=8080
```

Override secrets in production with environment variables instead of
committing them:

```bash
SPRING_DATASOURCE_PASSWORD=<YOUR_DB_PASSWORD> java -jar target/demo-0.0.1-SNAPSHOT.jar
```

---

## 4. Frontend

```bash
cd React
npm install
npm run build      # outputs to React/build
```

Serve the static bundle:

```bash
npx serve -s build          # or any static host (Nginx, S3, Netlify…)
```

---

## 5. ⚠️ Required changes before going live

These three things are still wired for local development.

### 5.1 API base URL is hardcoded to `localhost:8080`

Every fetch/axios call in `React/src` points at `http://localhost:8080`.
Introduce a shared constant (e.g. `src/config.js`) exporting `API_URL` from
`process.env.REACT_APP_API_URL`, then replace the literals:

```js
// src/config.js
export const API_URL = process.env.REACT_APP_API_URL || "http://localhost:8080";
```

### 5.2 CORS only allows `localhost:3000`

Every controller is annotated with:

```java
@CrossOrigin(origins = "http://localhost:3000")
```

Change this to your real domain (or centralise it in a single
`WebMvcConfigurer` bean):

```java
@CrossOrigin(origins = "https://your-domain.com")
```

### 5.3 Secrets in `application.properties`

Move the DB password out of source control (env var, as in §3).

---

## 6. Authentication — how it works and what was fixed

Password chain (all three steps must agree):

1. `registercustomer` / `registerservicecenter` — hashes the password
2. `logincheck` — hashes the submitted password and matches
   `userid + hash + status = 1`
3. `changePwd` — hashes the new password and updates by `userid`

Hashing is PBKDF2 (`PassBasedEnc`), keyed by `spring.saltvalue`.

**Fixed in this pass:**

| Bug | Symptom | Fix |
|---|---|---|
| `SaltValue` was `@Value("$ (spring.saltvalue]")` (typo, no `{`) | Salt injected as a literal no-op string | Corrected to `${spring.saltvalue}` + property added |
| `changePwd` / `checkAnswer` URLs built by string concatenation | Password truncated at `#` or `&`, so the new password was never stored — yet the UI said "Password changed Successfully" | Both now use `URLSearchParams` |
| `changePwd` success tested HTTP `200` | Endpoint returns *rows updated*; `0` also returns 200 → false success | Now requires `rows === 1` |
| Change-password `userid` field blank | User had to guess their user id; a wrong id updated 0 rows | Resolved automatically from the signed-in account and locked |
| No password policy on change | Could set a password the login form then rejects | Same rules as the login form |
| `logincheck` returned `null` for *both* "wrong password" and "not approved" | Unapproved accounts reported as "invalid credentials" | Returns `403 {error:"pending"}`; UI shows a specific message |

> ⚠️ Fixing the salt invalidated every previously stored hash. All accounts
> were reset — see §10 for credentials.

---

## 7. API robustness — unknown IDs no longer 500

Several lookups called `Optional.get()` directly, and three aggregate
endpoints declared a primitive `int` while the JPQL select returns `null`
for an empty result. Any stale or unknown id therefore came back as
**HTTP 500**, which in the browser surfaces as a blank page or a stuck
spinner rather than "no records".

| Area | Before | After |
|---|---|---|
| `getScById` · `getSrById` · `getScpById` · `getVehicleById` · `getCustomerById` · `getBrandById` | `findById(id).get()` → `NoSuchElementException` | `orElse(null)` |
| `getBookingLimitScById` · `getRating` · `getAllServReqOnADateforSC` | primitive `int` unboxing `null` → `AopInvocationException` | declared `Integer`, null-safe `0` |
| `getAllServReqOnADateforSC?date=` | `@RequestParam Date` rejected `yyyy-MM-dd` → **400** | parsed with `SimpleDateFormat`, same as its sibling endpoint |
| `getVehiclesByCid` · `getSerReqsByCid` · `getInvoiceBySvReqId` · `setRating` · `addCheckIn` · `addStageOne` · `addStageTwo` · `addCheckOut` · `addDelivered` | dereferenced a possibly-null entity | guarded → empty list / `0` / `null` |

A missing id now returns `200` with an empty body, empty list or `0`, which
the UI renders as "no records" instead of failing.

---

## 8. Registration — clear errors instead of a bare failure

Every registration endpoint failed opaquely: the browser only ever saw a
status code, so the UI could do no better than "Registration Failed".

| | Before | After |
|---|---|---|
| **Duplicate user id** (`login.userid` is `UNIQUE`) | raw `SQLIntegrityConstraintViolationException` → **500** | **409** `{"error":"duplicate-userid","message":"That user ID is already taken…"}` |
| **Missing brand / area** | inserted as a `NULL` foreign key → **500** *after* the login row was written | **400** `{"message":"Please select a valid brand."}` — checked before any insert |
| **Atomicity** | login insert + entity insert were independent, so a failure could leave an orphaned `login` row that could never sign in | `@Transactional` on both register endpoints — all or nothing |
| **`/registervehicle` returned `null`** | `null` is serialized as **200 with an empty body**, so the UI printed *"Vehicle Information Added."* for a vehicle that was never created | explicit **400** + message; the UI now requires a real `vehicleid` back |
| **Frontend payload** | posted the raw reducer state, whose fields are `{value,error,valid,touched}` wrappers → Jackson `MismatchedInputException` → **400** | payload flattened explicitly before send |
| **Frontend reducer** | `[action.fld]: action.value` *replaced* the wrapper, so inputs went uncontrolled after the first keystroke and `error`/`valid`/`touched` were dropped — no validation message could ever render | merges into the wrapper, so inputs stay controlled and inline errors appear |
| **Frontend error display** | `alert("Registration Failed.")` (customer form then reloaded the page) | reads `body.message` and renders it in an alert above the form |

Additional form-level fixes:

- **`ServRegistration.js` / `CustRegistration.js`** — added the `validate`
  cases the forms actually call (`scname`, `emailid`, `lane`, `pincode`,
  `answer`, `userid`, `bookinglimit`, `birthdate`); those fields previously
  always reported `valid=false`.
- **`CustRegistration.js`** — the date-of-birth input was bound to
  `info.fname.value`, so it displayed the first name.
- **`VehRegistration.js`** — `val.equals("Petrol")` threw
  `val.equals is not a function` (`String` has no `.equals` in JS); also
  added a submit guard so an empty form is caught client-side.
- All three forms now show which fields are missing or invalid instead of
  posting blindly.

The **service-centre → admin-approve → sign-in** chain:
`register` creates the account with `status=0` → `/logincheck` returns
**403** `{"error":"pending","message":"This account is awaiting approval…"}`
→ admin `PATCH /updateReq/{loginid}` sets `status=1` → sign-in succeeds.

---

## 9. Service packages, ratings and vehicle removal

Three features had front-end stubs with no working backend behind them. All
three are now full CRUD.

### 9.1 Service packages (service-centre dashboard)

`/serviceHome/packages`. A centre creates, edits and deletes the fixed-price
packages drivers pick at booking.

| Method | Path | Body / params |
|--------|------|---------------|
| `POST` | `/addPackageDetails` | `PackageRequest` |
| `PUT`  | `/updatePackageDetails` | `PackageRequest` |
| `DELETE` | `/deletePackageDetails?id=` | |
| `GET`  | `/getPackageByScId?scid=` | unchanged |

```jsonc
// PackageRequest — the entity itself cannot be bound directly, because its
// ServiceCenter is a live managed object and facilities arrive as ids.
{
  "packagedetailsid": 0,      // 0 = create, otherwise the row being updated
  "packagename": "Basic Service",
  "cost": 1890,
  "servicecenterid": 8,
  "facilityids": [1, 2, 21]
}
```

Guards, all returning `{error, message}`:

| Status | Case |
|--------|------|
| 400 | blank or >45-char name, cost <= 0, unknown `servicecenterid` |
| 403 | `PUT` naming a package owned by a *different* centre |
| 404 | `PUT`/`DELETE` on an unknown `packagedetailsid` |
| 409 | `DELETE` on a package referenced by a `servicerequests` row |

`addPackageDetails` was a `GET` taking a `@RequestBody` and had no update or
delete counterpart. `PackageDetailsService.getPackageById` also called
`findById(id).get()`, which 500'd on any unknown id.

### 9.2 Ratings

`/customerhome/rate` → `setRating`, and the public listing reads `getRating`.

* `setRating` now validates 1-5 stars and resolves the customer and centre
  before writing. It used to `return null` on bad input, which serialises to
  an **empty HTTP 200** — the UI reported "Feedback sent!" for a rating that
  was never saved.
* `getRating?scid=` returns **404** for an unknown centre instead of 500.
* The public listing's "Ratings" column rendered the literal word `Ratings`
  against a URL (`/getrating=3`) that never existed. It now shows the real
  average, or "No ratings yet".

### 9.3 Vehicle removal (customer dashboard)

`DELETE /deleteVehById?vid=&cid=` — the customer's vehicle list had a
`deleteVeh()` helper pointing at an endpoint that did not exist, which is why
it was never wired to a button. It is now a real Remove button with a confirm
dialog.

| Status | Case |
|--------|------|
| 403 | `cid` does not own the vehicle |
| 404 | unknown `vid` |
| 409 | the vehicle has service history (`servicerequests.vehicleid`) |

### 9.4 Centre bookings are no longer locked to today

`UpdateServiceProgress` hardcoded `moment(new Date())`, so a centre could only
ever see jobs whose service date was *today* — yesterday's undelivered job was
unreachable. There is now a date picker (defaulting to today, with a one-click
**Today** reset). The endpoint already accepted an arbitrary date; only the UI
was wrong.

---

## 10. Health check


| Check | Expected |
|---|---|
| `GET  http://localhost:8080/getCities` | `200` |
| `POST http://localhost:8080/logincheck` (good credentials) | `200` + the `Login` JSON |
| `POST http://localhost:8080/logincheck` (bad password) | `200` + empty body |
| `POST http://localhost:8080/logincheck` (not yet approved) | `403` + `{"error":"pending",…}` |
| `POST http://localhost:8080/changePwd` | `200` + `1` (rows updated) |
| `GET  http://localhost:8080/getVehiclesByCid?cid=999999` | `200` + `[]` — not `500` |
| `GET  http://localhost:8080/getRating?scid=999999` | `200` + `0` — not `500` |
| `http://localhost:3000` | app shell, no compile errors |
| `npm run build && npm run smoke` | `21/21 routes rendered cleanly` |

### End-to-end auth check

```bash
# 1. change the password (URL-encoded; '#' and '&' must survive)
curl -X POST "http://localhost:8080/changePwd?userid=Admin&newPwd=Vsms%40123"

# 2. sign in with it
curl -X POST http://localhost:8080/logincheck \
     -H "Content-Type: application/json" \
     -d '{"userid":"Admin","password":"Vsms@123"}'
```

### Frontend smoke test

```bash
cd React
npm run build     # required: the test boots the production bundle
npm run smoke     # renders all 21 routes in jsdom, fails on any crash
```

The test fails (exit `1`) if any route renders blank or throws, which is the
failure mode you get when an endpoint returns an unexpected shape.

---

## 11. Application structure

**Header**

- Public pages: brand + `Home / Service Centres` + `Login / Sign Up`.
- Signed-in public pages: brand + `Home / Service Centres / My dashboard / Sign out`.
- Dashboards: brand + a single user menu (name, role, profile, sign out).

Feature entry points live **only** on the dashboard, grouped into sections —
they are intentionally not repeated in the header.

**Dashboards** (customer / service centre / admin) share the same layout:

- greeting hero
- grouped feature tiles
- opening a tile replaces the tiles with a full-width work area and a
  "Back to dashboard" link, so it is obvious which feature is active.

---

## 12. Account credentials

Every account in the `login` table was reset after the salt fix (§6).

| User ID    | Password  | Role           |
|------------|-----------|----------------|
| `Shreyas`  | `Vsms@123` | Customer      |
| `ojas123`  | `Vsms@123` | Customer      |
| `tata123`  | `Vsms@123` | Customer      |
| `Admin`    | `Vsms@123` | Admin          |
| `Tata Pune`| `Vsms@123` | Service centre |
| `boku123`  | `Vsms@123` | Service centre |

All of them satisfy the login rules (capital + digit + symbol) and are
verified working against `logincheck`.

**To test the "awaiting approval" state**, set a service-centre account back
to pending and sign in:

```sql
UPDATE login SET status = 0 WHERE userid = 'boku123';
```

Signing in now returns `403` and the UI says *"This account is awaiting
approval by the administrator."* Approve it again from **Admin → Service
requests**, and the same account signs in normally.

**Resetting any password** (same endpoint the app uses — note the URL
encoding):

```bash
curl -X POST "http://localhost:8080/changePwd?userid=Admin&newPwd=NewPass1%40"
```

