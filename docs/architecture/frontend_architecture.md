## Components (Android)

---

### Launcher (App Entry Point)
- `LauncherActivity`

#### Responsibilities:
- Entry point of the application
- Checks if authentication token exists
- Redirects user:  
➜ MainActivity (if token exists)  
➜ LoginActivity (if not authenticated)

#### Notes
- Token-based authentication (`JWT`-ready structure)
- Separation of concerns (auth / network / storage / UI)
- `Retrofit` for API communication
- Stores `JWT token` locally using `SessionManager`

#### Architecture

```
LauncherActivity
        │
        ├── sessionManager.getToken
        |
        ├── token exists?
        │
        ├── YES → MainActivity
        │
        └── NO → LoginActivity
                    │
                    ├── Login
                    │
                    └── RegisterActivity
```

---

### main/
Entry point after authentication. Acts as the landing screen after login / registration
- `MainActivity`

#### Responsibilities:
- Checks user role from session
- Displays main user interface
- Loads appropriate data (clients or trainers)
- Displays results in a ListView

#### Architecture

```
activity_main.xml
      ↑
      │ setContentView()
      │
 MainActivity
      │
      ├── findViewById() → ListView
      │
      ├── SessionManager (token + role)
      │
      ├── UserRepository
      │
      └── ApiService (Retrofit)
              │
              ├── @GET("/trainer/clients")
              │                 ↓
              │         TrainerController
              │         
              ↓
        HTTP Response (JSON)
              ↓
   List<ClientResponse> (DTO parsing via Gson)
              ↓
     MainActivity → Adapter → ListView
```

---

### network/
Responsible for all API communication.

#### Includes:
- `ApiService` 
- `RetrofitClient`

#### Responsibilities:
- Defines REST API endpoints using `Retrofit`
- Handles HTTP requests (login, register etc.)
- Provides singleton `Retrofit` instance
- Manages base URL and converters (Gson)

---

### repository/
Acts as an abstraction layer between UI and network.

`UserRepository`
- fetches clients for trainers
- fetches trainers for clients
- hides `Retrofit` implementation from Activities

This ensures a clean separation of concerns and makes the app easier to maintain and scale.

---

### storage/
Manages local session and persistence.

#### Includes:
`SessionManager`

#### Responsibilities:
- Stores authentication token in `SharedPreferences`
- Retrieves saved token for API requests
- Clears session on logout
- Acts as a centralized storage layer for user session data

---

### auth/

#### Overview

Handles all authentication-related logic.

#### Includes:
- LoginActivity
- RegisterActivity
- DTOs:  
— LoginRequest  
— LoginResponse  
— RegisterRequest  

#### Architecture

```
LoginActivity
        │
        ├── activity_login.xml
        │
        ├── LoginRequest
        │
        └── RetrofitClient 
                └── ApiService @POST("/auth/login") <--> AuthController
                        |
                        ├── sessionManager.saveToken
                        │
                        └── MainActivity

RegisterActivity
        │
        ├── activity_register.xml
        │
        ├── RegisterRequest
        │
        └── RetrofitClient 
                └── ApiService @POST("/auth/register") <--> AuthController
                        |
                        ├── sessionManager.saveToken
                        │
                        └── MainActivity
```

#### Responsibilities
- User login via phone and password
- User registration (CLIENT / TRAINER roles)
- Communication with backend authentication API
- Token retrieval after successful login
- Navigation to main screen after authentication

**Authentication Flow**
- User opens the app
- App checks if JWT token exists
- If token exists → user is redirected to main screen
- If not → Login/Register screen is shown
- After successful login/registration:  
— token is saved locally  
— user is redirected to main screen

---
