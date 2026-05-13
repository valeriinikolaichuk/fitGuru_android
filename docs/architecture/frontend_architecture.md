## Components (Android)

---

### auth/

#### Overview

Implements the authentication feature of the FitGuru application.
It is responsible for user registration, login, and maintaining the authenticated session using a locally stored `JWT token`.
The module communicates with the backend via REST API using `Retrofit`.

#### Includes:
- LoginActivity
- RegisterActivity
- DTOs:  
LoginRequest  
LoginResponse  
RegisterRequest  

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
token is saved locally  
user is redirected to main screen

#### Notes
- No business logic is handled in Activities
- Authentication state is fully client-side via stored `token`
- Stores `JWT token` locally using `SessionManager`
- `Retrofit` is used as the single API communication layer

---
