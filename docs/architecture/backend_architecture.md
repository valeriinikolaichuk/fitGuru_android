## Modules (Backend)

---

### Auth Module

#### Overview

- The module implements the **authentication system** for the FitGuru platform.  
- It provides **REST APIs** for user registration, login, and JWT-based authentication.  
- The module follows a layered architecture and is designed to be easily extendable for future features.  

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
|          ├── LoginResponse
|          └── RefreshRequest
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

#### Responsibilities
**1. Authentication API**   
Provides REST endpoints:
- `POST /auth/register` Registers a new user in the system
- `POST /auth/login` Authenticates user credentials and returns JWT access token
- `POST /auth/refresh` When access token expires server issues new tokens
 
**2. Business Logic (AuthService)**
- Validates user input
- Checks if user exists
- Verifies password
- Handles registration logic
- Generates JWT token via JwtService

**3. JWT Handling (JwtService)**
Responsible for:
- Generating JWT tokens
- Setting token expiration time
- Extracting user information from token
- Supporting authentication validation logic

**4. Data Layer**
- `UserRepository` handles database operations using Spring Data JPA
- `User` entity represents application users
- `TrainerClient` represents relationships between trainers and clients
- `Role` defines user types (`TRAINER` / `CLIENT`)

#### Authentication Flow
**1.** Client sends login request (phone + password)  
**2.** Backend validates credentials  
**3.** If valid:
- JWT token is generated
- token is returned to client

**4.** Client uses token for authorized requests

#### Security Model
- Passwords are stored as hashed values
- JWT token is used for stateless authentication
- Token is validated on each protected request (future extension)

#### Notes
- The module currently supports only authentication functionality
- Designed for future expansion (fitness tracking, training plans, etc.)
- Follows separation of concerns (Controller → Service → Repository)

---

### Trainer–Client Relationship Modules
#### Overview
These modules implement the relationship system between trainers and clients in the `FitGuru` platform.  
They provide REST APIs for retrieving assigned trainers and clients based on the authenticated user role.  
The system is built on a many-to-many relationship using a dedicated join table (`trainer_clients`) and follows a layered, scalable architecture.  

#### Architecture
```
├── trainer/
│     ├── controller/
│     │     └── TrainerController
│     ├── service/
│     │     └── TrainerService
│     ├── dto/
│     |      └── ClientResponse
|     ├── entity/
│     |      └── TrainerClient
│     └── repository/
│            └── TrainerClientRepository
│
├── client/
│     ├── controller/
│     │     └── ClientController
│     ├── service/
│     │     └── ClientService
│     └── dto/
│           └── TrainerResponse
|
└── user/
      └── entity/
            ├── User
            └── enums/
                  └── Role
```

### Trainer Module
#### Responsibilities  

Provides REST endpoints for trainers:
- `GET /trainer/clients` Returns a list of clients assigned to the authenticated trainer.

#### Business Logic
- Extracts trainer identity from JWT token
- Loads trainer from database
- Fetches related clients via `TrainerClientRepository`
- Maps data to `ClientResponse` DTO

#### Relationship Management (TrainerClient)
The `TrainerClient` entity represents a many-to-many relationship between users.  
It contains:
- `trainer` → User with TRAINER role
- `client` → User with CLIENT role
- `createdAt` → assignment timestamp

#### Data Access Layer  
`TrainerClientRepository`  
Provides access to relationship data:  
- findByTrainer(User trainer) → returns all clients of a trainer
- findByClient(User client) → returns all trainers of a client

---

### Client Module
#### Responsibilities

Provides REST endpoints for clients:
- `GET /client/trainers` Returns a list of trainers assigned to the authenticated client.

#### Business Logic
- Extracts client identity from JWT token
- Loads client from database
- Fetches related trainers via `TrainerClientRepository`
- Maps data to `TrainerResponse` DTO

---
