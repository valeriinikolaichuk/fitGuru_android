## Testing

<details open="open">
<summary>Contents</summary>  

- [auth](backend/auth.md)

</details>

---

### Test Configuration
Testing framework:
- Spring Boot Test framework
- JUnit 5 for unit testing
- Mockito for mocking dependencies
- `@ExtendWith(MockitoExtension.class)` for isolated unit tests

### Technologies
- Java
- Spring Boot
- JUnit 5
- Mockito
- Maven

### Running Tests
Run all backend tests:

```
mvnw.cmd test
```
To generate coverage report (if configured with `JaCoCo`):
```
mvnw clean test
```

JaCoCo reports are generated after running tests and can be found in:
```
target/site/jacoco/index.html
```
