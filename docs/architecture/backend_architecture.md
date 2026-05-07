## Modules (Backend)

---

### Auth Module

#### Overview

The module implements the **authentication system** for the FitGuru platform.  
It provides **REST APIs** for user registration, login, and JWT-based authentication.  
The module follows a layered architecture and is designed to be easily extendable for future features.  

#### Architecture
```
├── auth/
|     ├── controller/
|     │    └── AuthController
|     ├── service/
|     │    ├── AuthService
|     │    └── JwtService
|     └── dto/
|          ├── LoginRequest
|          ├── RegisterRequest
|          └── LoginResponse
|
├── user/
|    ├── repository/
|    │    └── UserRepository
|    └── entity/
|         ├── User
|         └── enums/
|              └── Role
|
└── trener/entity/ 
               └── TrainerClient
```
