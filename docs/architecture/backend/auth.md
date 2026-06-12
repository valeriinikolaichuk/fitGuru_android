### auth/

#### Overview

- The module implements the **authentication system** for the FitGuru platform.  
- It provides **REST APIs** for user registration, login, and JWT-based authentication.  
- The module follows a layered architecture and is designed to be easily extendable for future features.

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
- Client sends login request (phone + password)  
- Backend validates credentials  
- If valid:
— JWT token is generated
— token is returned to client
- Client uses token for authorized requests

#### Security Model
- Passwords are stored as hashed values
- JWT token is used for stateless authentication
- Token is validated on each protected request (future extension)

#### Notes
- The module currently supports only authentication functionality
- Designed for future expansion (fitness tracking, training plans, etc.)
- Follows separation of concerns (Controller → Service → Repository)

