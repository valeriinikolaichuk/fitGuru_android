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
#### `getTrainers_shouldReturnTrainerList`

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

#### `getTrainers_shouldThrowWhenUserNotFound`
Checking:
```
User client = userRepository.findByPhone(phone)
                .orElseThrow(() -> new RuntimeException("User not found"));
```

---

#### `getTrainers_shouldThrowWhenUserIsNotClient`
Checking:
```
if (client.getRole() != Role.CLIENT) {
    throw new RuntimeException("Access denied");
}
```

**Verifies:**
- Phone number successfully extracted from `JWT`;
- User found;
- Role is not `CLIENT`;
- `RuntimeException("Access denied")` thrown;
- `trainerClientRepository` is not called at all.

---

#### `getTrainers_shouldReturnEmptyList`
**Verifies** the case when the client exists, has the correct `CLIENT` role, but does not have any connected trainers yet..

If
```
trainingRequestRepository.findByClient(client)
```
returns
```
Collections.emptyList()
```
then the service should return an empty list of TrainerResponse objects.

---

#### `getAvailableTrainers_shouldReturnAvailableTrainerList`
```
- Trainer №1 is already connected → should not be in the list
- Trainer №2 has a pending request
- Trainer №3 has no request at all
```
Expected result:
```
trainer2 -> PENDING
trainer3 -> NONE
```

**Verifies:**
- `JWT` extraction
- Finding the client
- Fetching connected trainers list
- Creating connectedTrainerIds
- Getting requests
- Building statuses map
- Filtering trainers
- Invoking getOrDefault(..., "NONE")
- Creating `AvailableTrainerResponse`

---

#### `getAvailableTrainers_shouldReturnEmptyList`
**Verifies:**
- Given:
  - Client exists,
  - no connected trainers,
  - no requests,
  - no available trainers in the system
- Then: The result should be an empty list

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
**verify:**
- `GET /client/trainers` endpoint
- `GET /client/trainers/available` endpoint
- Authorization header handling
- Service layer interaction
- Correct `HTTP` **200** responses
- `JSON` response mapping

---

### Current test coverage
Client module:

- Instruction Coverage: 100%
- Branch Coverage: 100%

The Client module business logic and REST API layer are fully covered by automated tests.
