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
                    └── Register
```
