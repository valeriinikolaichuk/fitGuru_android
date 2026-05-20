<div align="center">
  <h3>FitGuru</h3>
  <h4>FitGuru is a fitness coaching platform that allows personal trainers to create and manage workout programs for their clients online.</h4>
  <h4>This project explores full-stack development with Java (Android + Spring Boot), focusing on system design, API development, and client-server interaction.</h4>
  <h4>The existing frontend serves as a learning implementation and is planned to be redesigned into production-grade native mobile applications for Android and iOS in future iterations.</h4>
</div>

## Features

- Trainer can create and edit workout programs
- Assign programs to clients
- Clients can view assigned programs
- Track exercise progress
- Custom and standard exercise database

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
