## Testing

<details open="open">
<summary>Contents</summary>  

- [auth](backend/auth.md)
- [client](backend/client.md)
- [trainer](backend/trainer.md)

</details>

---

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
