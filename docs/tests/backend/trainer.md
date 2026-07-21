## Trainer Testing

---

### Trainer Service
### Test Configuration
- JUnit 5 for writing and executing unit tests
- Mockito for mocking repositories and external dependencies
- `@ExtendWith(MockitoExtension.class)` for isolated unit testing
- Dependencies are mocked to test service logic without database interaction

#### getClients_shouldReturnClientList
- Extracting trainer identity from `JWT` token
- Loading trainer data from `UserRepository`
- Validating that the user has the `TRAINER` role
- Retrieving connected clients
- Mapping `TrainerClient` entities into `ClientResponse` DTOs
- Handling trainers without connected clients
- Handling non-existing users
- Rejecting access for users with incorrect roles

---

### getClients_shouldThrowWhenUserNotFound
Checking:
```
userRepository.findByPhone(phone)
        .orElseThrow(() -> new RuntimeException("User not found"));
```

**Verifies** the behavior when the trainer extracted from the JWT token does not exist in the database.  
If
```
userRepository.findByPhone(phone)
```
returns
```
Optional.empty()
```
then the service must throw a `RuntimeException` with the message:
```
`User not found`
```
The trainer-client repository must not be accessed.
