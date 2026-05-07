## Components (Android)

---

### Auth Feature

#### Overview

The Auth Feature currently implements the authentication feature of the FitGuru application.
It is responsible for user registration, login, and maintaining the authenticated session using a locally stored `JWT token`.
The module communicates with the backend via REST API using `Retrofit`.

#### Architecture

The Android authentication feature is organized in a feature-based structure:
```
auth/
 ├── LoginActivity
 ├── RegisterActivity
 ├── AuthRepository
 ├── dto/
 │    ├── LoginRequest
 │    ├── RegisterRequest
 │    └── LoginResponse
 ├── network/
 │    ├── ApiService
 │    └── RetrofitClient
 ├── storage/
      └── SessionManager
```

#### Responsibilities
**1. User Interface**  
- `LoginActivity` handles user login input
- `RegisterActivity` handles user registration input
- Displays validation messages and navigation between screens

**2. Network Communication**  
- Uses `Retrofit` to communicate with backend REST endpoints
- Sends authentication requests: `POST /auth/register` `POST /auth/login`
- Interact with `Auth Module`

**3. Session Management**  
- Stores **JWT token** locally using `SessionManager`
- Retrieves token on app startup
- Clears token on logout  
- Storage mechanism: SharedPreferences  

**4. Authentication Flow**
- User opens the app
- App checks if JWT token exists
- If token exists → user is redirected to main screen
- If not → Login/Register screen is shown
- After successful login:  
token is saved locally  
user is redirected to main screen

#### Notes
- No business logic is handled in Activities
- Authentication state is fully client-side via stored token
- Retrofit is used as the single API communication layer

---
