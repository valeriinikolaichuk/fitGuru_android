## Swagger API Documentation

The project integrates Swagger UI via Springdoc OpenAPI for interactive `API` documentation.

Swagger allows you to:
- View all available `REST` endpoints
- Inspect request and response models
- Execute `API` requests directly from the browser
- Test secured endpoints using JWT authentication

### Swagger UI

```
http://localhost:8080/swagger-ui/index.html
```

### Authentication
The API uses JWT-based authentication:
- Access Token — short-lived token used for API requests.
- Refresh Token — long-lived token used to obtain new access tokens.

For protected endpoints:
- Authenticate using `/auth/login`.
- Copy the received access token.
- Click **Authorize** in Swagger UI
- Enter:

Bearer <your-token>

- Execute authorized requests.