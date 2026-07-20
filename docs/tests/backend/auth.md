## Auth Testing

### Test Configuration

Testing framework:
- Spring Boot Test framework
- JUnit 5 for unit testing
- Mockito for mocking dependencies
- `@ExtendWith(MockitoExtension.class)` for isolated unit tests

The authentication module is tested without loading the full Spring context. External dependencies such as repositories, password encoder, and JWT services are mocked to verify business logic independently.

| Module | Tested functionality |
|--------|----------------------|
| AuthService | User registration, duplicate user validation, login flow, password validation, JWT token generation, refresh token flow |
| UserRepository | Repository interaction verification through mocked dependencies |
| JwtService | `JWT` access token and refresh token generation calls |
| PasswordEncoder | Password encoding and password matching validation |

### Test Scenarios
#### User Registration
Tests verify:
- New user creation.
- Password hashing before saving.
- Role assignment.
- Access and refresh token generation.
- Prevention of duplicate user registration.

#### User Login
Tests verify:
- Successful login with valid credentials.
- User lookup by phone number.
- Password validation.
- Last login timestamp update.
- `JWT` token generation.

#### Refresh Token Flow
Tests verify:
- Refresh token processing.
- User extraction from token.
- User lookup.
- Generation of new access and refresh tokens.
- Correct response creation.

---

## JwtService
Tested functionality:
- Access token generation.
- Refresh token generation.
- JWT subject extraction.
- Role claim validation.
- Token parsing and signature validation.
- Invalid token handling.

| Test | Description |
|------|-------------|
| generateAccessToken_shouldCreateValidToken | Verifies access token creation with user phone and role claims |
| generateRefreshToken_shouldCreateValidToken | Verifies refresh token creation and expiration |
| extractPhone_shouldReturnUserPhone | Verifies phone extraction from valid JWT |
| extractPhone_shouldFailWithInvalidToken | Verifies invalid JWT rejection |

---

### AuthController

| Endpoint | Tested functionality |
|---|---|
| POST `/auth/register` | User registration request mapping and response handling |
| POST `/auth/login` | Login request mapping and authentication response |
| POST `/auth/refresh` | Refresh token request processing |

**Testing approach:**
- `@WebMvcTest` used for controller isolation.
- `MockMvc` used for HTTP request simulation.
- `AuthService` mocked with Mockito.
- JSON request/response validation performed with `ObjectMapper` and `jsonPath`.

The controller layer is tested independently from authentication business logic.
