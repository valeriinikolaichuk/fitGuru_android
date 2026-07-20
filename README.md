<div align="center">
  <h3>FitGuru</h3>
  <h4>FitGuru is intended to be a fitness trainer-client platform that allows personal trainers to create and manage workout programs for their clients online. The application is designed to be as simple and practical as possible, focusing only on creating, tracking, and sharing workout plans based on the personal experience of a gym personal trainer.</h4>
  <h4>This project explores full-stack development with Java (Android + Spring Boot), focusing on system design, API development, and client-server interaction.</h4>
  <h4>The current application serves as a beta version for testing purposes, with functionality expected to be expanded over time. Future iterations will include a native iOS application as well as a full-featured web application.</h4>
</div>

<details open="open">
<summary>Contents</summary>

- [Features](#features)
- [Demo Access](#demo-access)
- [Architecture](#architecture)
- [Authentication Flow](#authentication-flow)
- [Setup and Run Instructions](#setup-and-run-instructions)
- [Database](#database)
- [Project Structure](#project-structure)
- [Testing](#testing)
- [Swagger API Documentation](#swagger-api-documentation)
- [License](LICENSE)
- [Author](#author)

</details>

## Features

- Create and manage workout programs
- Assign and share programs with clients
- Client access to assigned plans
- Track training progress
- Built-in exercise database with custom entries

---

## Demo Access
➡ [Demo Access](docs/demo_access.md)

---

## Architecture

The project is built as a full-stack system:

- **Android App (Java)** – mobile client
- **Backend (Spring Boot)** – REST API
- **Database (PostgreSQL)** – data storage
- **Docker** – development environment

---

## Authentication Flow

The app uses a dual-token system:
- **Access Token** → short-lived (2 hours)
- **Refresh Token** → long-lived (45 days)

---

## Setup and Run Instructions
➡ [How to run the project](docs/setup.md)

---

## Database

For a detailed explanation of the database structure see:  
➡ [Database Documentation](docs/database/database.md)

---

## Project Structure

For a detailed explanation of the system architecture see:    
➡ [frontend documentation](docs/architecture/frontend_architecture.md)  
➡ [backend documentation](docs/architecture/backend_architecture.md)

---

## Testing
➡ [backend tests](docs/tests/backend_tests.md)  

---

## Swagger API Documentation
➡ [Swagger API Documentation](docs/swagger.md)

---

## Author

Valerii Nikolaichuk

---
