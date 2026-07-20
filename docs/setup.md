## Requirements
### Tech Stack
- Docker
- Docker Compose
- Java 21
- Spring Boot 3.5.14
- Maven
- PostgreSQL
- Spring Security
- JWT Authentication
- Spring Data JPA
- Lombok

#### Clone Repository
```
git clone https://github.com/valeriinikolaichuk/fitGuru_android.git
```

#### Go to backend folder:
```
cd fitGuru_android/backend
```
```
docker compose up -d
```
#### YAML
➡ [docker-compose.yml](https://github.com/valeriinikolaichuk/fitGuru_android/blob/main/docker-compose.yml)
### PostgreSQL
The database runs in Docker container.  
- Host: localhost
- Port: 5433
- Database: fitguru
- Username: postgres
- Password: postgres

#### Run Backend
Windows:
```
mvnw spring-boot:run
```
Mac/Linux:
```
./mvnw spring-boot:run
```
#### Application URL
http://localhost:8080

### DATABASE
➡ [db_backup.dump](https://github.com/valeriinikolaichuk/fitGuru_android/blob/main/docs/database)

#### Swagger
The project integrates `Swagger UI` (via Springdoc OpenAPI) for interactive API documentation.  
It allows you to view all available REST endpoints, inspect request/response data models, and test API queries directly from the browser.

* **Swagger UI URL:** [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
