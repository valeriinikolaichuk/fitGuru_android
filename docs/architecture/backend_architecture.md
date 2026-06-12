## Components (Backend)

### Tech Stack
- Spring Boot 3.5.14
- Maven
- Spring Security
- JWT Authentication
- Spring Data JPA
- Lombok

---

### auth/

Handles all authentication-related logic.  

➡ [auth/](backend/auth.md)

---

### config/

Central Spring Security configuration.

➡ [config/](backend/config.md)

---

### client/

Provides REST endpoints for client-related operations.

➡ [client/](backend/client.md)

---

### trainer/

Provides functionality for retrieving trainer-client relationships.

➡ [trainer/](backend/trainer.md)

---

### Trainer–Client Relationship Modules
#### Overview
These modules implement the relationship system between trainers and clients in the `FitGuru` platform.  
They provide REST APIs for retrieving assigned trainers and clients based on the authenticated user role.  
The system is built on a many-to-many relationship using a dedicated join table (`trainer_clients`) and follows a layered, scalable architecture.  



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
