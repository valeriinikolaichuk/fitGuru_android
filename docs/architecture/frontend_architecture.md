## Components (Android)

### Tech Stack
- Java
- Retrofit 2
- OkHttp
- SharedPreferences (`SessionManager`)

---

### Launcher (App Entry Point)
- `LauncherActivity`

➡ [Launcher](docs/frontend/launcher.md)

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
                    └── Register
```

---

### auth/

#### Overview

Handles all authentication-related logic.

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

---

### main/
Entry point after authentication. Acts as the landing screen after login / registration
- `MainActivity`

#### Responsibilities:
- Role detection ('CLIENT' / 'TRAINER') via 'SessionManager'
- Displays main user interface
- Loads appropriate data (clients or trainers)
- Displays results in a `ListView`
- Navigates to 'ProgramsActivity' (selected user programs)
- Navigates to 'TrainersListActivity' (trainer search screen)
- Navigates to 'TrainerRequestsActivity' (incoming requests for trainers)
- Closes application using system call ('finishAffinity()')

#### Trainer Flow
Trainer sees:
- View their assigned clients
- "Entries" button
- "CloseApp" button

#### Client Flow
Clients can:
- View their assigned trainers
- "AddTrainer" button (view list of available trainers)
- "CloseApp" button

#### Architecture

```
activity_main.xml
      ↑
 MainActivity
      │
      ├── SessionManager
      │       ├── token
      │       └── role
      │
      ├── UserRepository
      │
      └── ApiService (Retrofit)
              │
              ├── GET /trainer/clients <----------------> TrainerController
              ├── GET /client/trainers
              ├── GET /trainer/requests
              ├── POST /trainer/requests/{id}/accept
              ├── POST /trainer/requests/{id}/reject                         
              │         
              ↓
        HTTP Response (JSON)
              ↓
   List<ClientResponse> (DTO parsing via Gson)
              ↓
     MainActivity → Adapter → ListView
```

`RequestsActivity`  
Screen used by trainers to manage incoming training requests.

#### Responsibilities
Loads pending training requests
Displays request list
Allows accepting requests
Allows rejecting requests
Updates UI after request action

#### RequestsActivity Architecture
```
activity_requests.xml
        ↑
        │ setContentView()
        │
 RequestsActivity
        │
        ├── ListView
        │
        ├── UserRepository
        │
        ├── SessionManager
        │
        └── ApiService
                │
                ├── GET /trainer/requests
                ├── POST /trainer/requests/{id}/accept
                └── POST /trainer/requests/{id}/reject
```
                
---

### adapters/

#### `UserAdapter`  
#### Used for:
- trainer clients
- client trainers
#### Layout: 
`item_user.xml`  
  
#### `RequestAdapter`
#### Used for:
- incoming training requests
#### Layout:
`item_request.xml`  
#### Handles:
- request acceptance
- request rejection

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

