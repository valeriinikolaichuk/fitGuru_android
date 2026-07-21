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

Example request:

```json
{
  "phone": "380991112233",
  "password": "password123"
}
```

Click **Execute**.

The response contains:

```json
{
  "accessToken": "<JWT access token>",
  "refreshToken": "<JWT refresh token>",
  "role": "CLIENT"
}
```

Copy the **access token**.

---

### 4. Authorize Swagger

Click the **Authorize** button in the top-right corner.

Enter:

```
Bearer <access_token>
```

Example:

```
Bearer eyJhbGciOiJIUzI1NiJ9...
```

Click **Authorize**.

---

### 5. Refresh Access Token

Open:

```
POST /auth/refresh
```

Click **Try it out**.

Example request:

```json
{
  "refreshToken": "<refresh_token>"
}
```

Click **Execute**.

The response returns a new pair of JWT tokens:

```json
{
  "accessToken": "<new access token>",
  "refreshToken": "<new refresh token>",
  "role": "CLIENT"
}
```

---

### 6. Access Protected Endpoints

After authorization, protected endpoints can be called directly from Swagger UI.

The backend will:

- Validate the `JWT` access token.
- Authenticate the current user.
- Check the user's role and permissions.
- Execute the requested operation.
- Return the requested resource or an appropriate error response.
