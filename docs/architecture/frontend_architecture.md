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

Allows clients to browse available trainers and manage training requests.

➡ [client/](frontend/client.md)

---

### trainer/

Allows trainers to view and manage incoming training requests from clients.

➡ [trainer/](frontend/trainer.md)
                
---

### view/

The view module is responsible for displaying workout programs and their hierarchical structure.

➡ [view/](frontend/view.md)

---

### program/

Provides functionality for creating and editing workout programs.

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

