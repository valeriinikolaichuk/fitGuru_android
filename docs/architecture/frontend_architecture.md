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
