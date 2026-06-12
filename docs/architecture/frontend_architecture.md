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

The `client` module is responsible for trainer discovery and training request management from the client side.

➡ [client/](frontend/client.md)

---

### trainer/

The `trainer` module responsible for trainer-specific functionality, including client management and training request processing.

➡ [trainer/](frontend/trainer.md)
                
---

### view/

➡ [view/](frontend/view.md)

---

### program/

➡ [program/](frontend/program.md)

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

