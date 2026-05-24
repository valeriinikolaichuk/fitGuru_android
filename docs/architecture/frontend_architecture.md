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

Handles all authentication-related logic.  

➡ [auth/](frontend/auth.md)

---

### main/

Entry point after authentication. Acts as the landing screen after login / registration

➡ [main/](frontend/main.md)

---

### client/



---

### trainer/



---

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

➡ [adapters/](frontend/adapters.md)

---

### network/

Responsible for all API communication.

➡ [network/](frontend/network.md)

---

### repository/

Acts as an abstraction layer between UI and network.

➡ [repository/](frontend/repository.md)

---

### storage/

Manages local session and persistence.

➡ [storage/](frontend/storage.md)

---

