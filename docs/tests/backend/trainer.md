## Trainer Testing

<details open="open">
<summary>Contents</summary>
  
- [Trainer Service](#trainer-service)
  - [getClients_shouldReturnClientList](#getclients_shouldreturnclientlist)
  - [getClients_shouldThrowWhenUserNotFound](#getclients_shouldthrowwhenusernotfound)
  - [getClients_shouldThrowWhenUserIsNotTrainer](#getclients_shouldthrowwhenuserisnottrainer)
  - [getClients_shouldReturnEmptyList](#getclients_shouldreturnemptylist)
- [Trainer Controller](#trainer-controller)
- [Current test coverage](#current-test-coverage)
  
</details>

---

### Trainer Service
### Test Configuration
- JUnit 5 for writing and executing unit tests
- Mockito for mocking repositories and external dependencies
- `@ExtendWith(MockitoExtension.class)` for isolated unit testing
- Dependencies are mocked to test service logic without database interaction

---

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

#### getClients_shouldThrowWhenUserNotFound
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

---

#### getClients_shouldThrowWhenUserIsNotTrainer
Checking:
```
if (trainer.getRole() != Role.TRAINER) {
    throw new RuntimeException("Access denied");
}
```

**Verifies** that access is denied when the authenticated user does not have the `TRAINER` role.  

If
```
user.getRole()
```
is not
```
TRAINER
```
then the service must throw a `RuntimeException` with the message:
```
`Access denied`
```
The trainer-client repository must not be accessed.

---

#### getClients_shouldReturnEmptyList
**Verifies** the case when the authenticated trainer exists, has the correct `TRAINER` role, but does not have any connected clients.

If
```
repository.findByTrainer(trainer)
```
returns
```
Collections.emptyList()
```
then the service should return an empty list of `ClientResponse` objects.

---

### Trainer Controller
The Trainer Controller is covered by automated MVC tests using Spring MockMvc.

#### Covered Endpoint
#### GET /trainer/clients

**The test verifies:**
- Authorization header handling
- TrainerService invocation
- HTTP 200 response
- `JSON` response serialization
- Correct mapping of `ClientResponse` objects

---

### Current test coverage
```
com.fitguru.backend.trainer.service      100%
com.fitguru.backend.trainer.controller   100%
```
