## API Testing via Swagger

### 1. Open Swagger UI

Swagger UI is available at:
```
http://localhost:8080/swagger-ui/index.html
```

---

### 2. Register a user

Open:
```
POST /auth/register
```

Click **Try it out**.

Example request:
```
{
  "name": "John Doe",
  "phone": "380991112233",
  "password": "password123",
  "role": "CLIENT"
}
```

Click **Execute**.

If registration succeeds, the `API` returns a success response.

```json
{
  "accessToken": "<JWT access token>",
  "refreshToken": "<JWT refresh token>",
  "role": "CLIENT"
}
```

---

### 3. Login

Open:
```
POST /auth/login
```

Click **Try it out**.

Example:
```
{
  "email": "user1@example.com",
  "password": "password123"
}
```

Click **Execute**.

The response contains a JWT token:
```
{
  "accessToken": "eyJhbGciOiJIUzI1NiJ9..."
}
```

Copy the token.

---

### 4. Authorize Swagger

Click the **Authorize** button in the top-right corner.

Enter:

Bearer eyJhbGciOiJIUzI1NiJ9...

Click **Authorize**.

---

### 5. Call protected endpoint

Open:
```
POST /api/process
```

Click **Try it out**.

Example:
```
{
  "text": "hello world"
}
```

Click **Execute**.

The request will include:
```
Authorization: Bearer <JWT>
```

The auth-api will:
- Validate the `JWT` token.
- Load the user from `PostgreSQL`.
- Call data-api.
- Send `X-Internal-Token`.
- Save a processing log.
- Return the transformed result.

---
