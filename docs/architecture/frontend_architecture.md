## Components (Android)

### Tech Stack
- Java
- Retrofit 2
- OkHttp
- SharedPreferences (`SessionManager`)

---

### Entry point of the application
➡ [Launcher](frontend/launcher.md)

---

### auth/

#### Overview

Handles all authentication-related logic.  

➡ [auth/](frontend/auth.md)

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

