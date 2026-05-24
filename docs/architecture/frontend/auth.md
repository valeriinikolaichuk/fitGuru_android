### auth/

#### Includes:
- LoginActivity
- RegisterActivity
- AuthInterceptor
- DTOs:  
— LoginRequest  
— LoginResponse  
— RegisterRequest  
— RefreshRequest 

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

#### JWT Authentication System
The application uses a dual-token authentication architecture:   
- User enters credentials  
- Server returns:  
— accessToken  
— refreshToken  
— role   
- `Automatically` adds JWT token to every request
- Tokens are saved in `SessionManager`
- User is redirected to `MainActivity`
```
Login → Access Token + Refresh Token
  ↓
API requests (Access Token)
  ↓
If 401 → Refresh Token used
  ↓
New Access Token generated
```

**Authentication Flow**
- User opens the app
- App checks if JWT token exists
- If token exists → user is redirected to main screen
- If not → Login/Register screen is shown
- After successful login/registration:  
— token is saved locally  
— user is redirected to main screen
