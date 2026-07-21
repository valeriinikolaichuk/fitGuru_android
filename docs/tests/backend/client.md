## Client Testing

<details open="open">
<summary>Contents</summary>
  
- [ClientServiceTest](#clientservice)
  - [getTrainers_shouldReturnTrainerList](#gettrainers_shouldreturntrainerlist)
  - [getTrainers_shouldThrowWhenUserNotFound](#gettrainers_shouldthrowwhenusernotfound)
  - [getTrainers_shouldThrowWhenUserIsNotClient](#gettrainers_shouldthrowwhenuserisnotclient)
  - [getTrainers_shouldReturnEmptyList](#gettrainers_shouldreturnemptylist)
  - [getAvailableTrainers_shouldReturnAvailableTrainerList](#getavailabletrainers_shouldreturnavailabletrainerlist)
  - [getAvailableTrainers_shouldReturnEmptyList](#getavailabletrainers_shouldreturnemptylist)
- [ClientController](#clientcontroller)
- [Current test coverage](#current-test-coverage)
  
</details>

### Test Configuration

- JUnit 5 for writing and executing unit tests
- Mockito for mocking repositories and external dependencies
- `@ExtendWith(MockitoExtension.class)` for isolated unit testing
- Dependencies are mocked to test service logic without database interaction

---

### ClientService
#### getTrainers_shouldReturnTrainerList
Checking:
- `JWT` parsed successfully;
- User found;
- Role = `CLIENT`;
- Trainer list returned.

**Verifies:**
- Correct trimming of `Bearer` before passing to `JwtService`.
- Phone number extraction from the `JWT`.
- Client existence check in `UserRepository`.
- Successful `CLIENT` role validation.
- Call to `TrainerClientRepository`.
- Proper mapping of `TrainerClient` to `TrainerResponse`.
- Verification of all `DTO` fields.
- Verification of interactions with all mocks.

---

#### getTrainers_shouldThrowWhenUserNotFound
Checking:
```
User client = userRepository.findByPhone(phone)
                .orElseThrow(() -> new RuntimeException("User not found"));
```

---

#### getTrainers_shouldThrowWhenUserIsNotClient
Checking:
```
if (client.getRole() != Role.CLIENT) {
    throw new RuntimeException("Access denied");
}
```

Create user:
```
user.setRole(Role.TRAINER);
```
Checking:
```
assertThrows(RuntimeException.class,
        () -> clientService.getTrainers(token));
```

---

#### getTrainers_shouldReturnEmptyList
If
```
trainingRequestRepository.findByClient(...)
```
returns
```
Collections.emptyList()
```
then the status must be
```
NONE
```

---

#### getAvailableTrainers_shouldReturnAvailableTrainerList
Checking:
- Trainer №1 is already connected → should not be in the list
- Trainer №2 has a pending request
- Trainer №3 has no request at all

Expected result:
```
trainer2 -> PENDING
trainer3 -> NONE
```

---

#### getAvailableTrainers_shouldReturnEmptyList
If
```
userRepository.findByRole(Role.TRAINER)
```
returns
```
List.of()
```
then the expected result is
```
assertTrue(result.isEmpty());
```

---

### ClientController


---

### Current test coverage

