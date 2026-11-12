## Request Testing

<details open="open">
<summary>Contents</summary>
  
- [Request Service](#request-service)
  - [sendRequest_shouldSaveTrainingRequest](#sendrequest_shouldsavetrainingrequest)
  - [sendRequest_shouldNotSaveDuplicateRequest](#sendrequest_shouldnotsaveduplicaterequest)
  - [cancelRequest_shouldDeletePendingRequest](#cancelrequest_shoulddeletependingrequest)
  - [getRequests_shouldReturnTrainingRequests](#getrequests_shouldreturntrainingrequests)
  - [getRequests_shouldReturnEmptyList](#getrequests_shouldreturnemptylist)
  - [acceptRequest_shouldAcceptTrainingRequest](#acceptrequest_shouldaccepttrainingrequest)
  - [rejectRequest_shouldRejectTrainingRequest](#rejectrequest_shouldrejecttrainingrequest)
- [Request Controller](#request-controller)
- [Current test coverage](#current-test-coverage)
  
</details>

---

### Request Service

#### Scenarios
| Method | Tests|
|--------|------|
|sendRequest()	| success, already exists|
|cancelRequest()	| success|
|getRequests()	| return requests, empty list|
|acceptRequest()	| success|
|rejectRequest()	| success|

---

### sendRequest_shouldSaveTrainingRequest
**Verifies** that a new training request is created when no pending request already exists.
- JWT phone extraction
- Client lookup
- Trainer lookup
- Pending request validation
- Creation of a new `TrainingRequest`
- Saving the request with `PENDING` status

---

### sendRequest_shouldNotSaveDuplicateRequest
Checking:
```
if (exists) return;
```
**Verifies** that a duplicate training request is not created when a pending request already exists between the client and trainer.

If
```
trainingRequestRepository.existsByClientAndTrainerAndStatus(...)
```
returns
```
true
```
the service must exit without creating or saving a new `TrainingRequest`.

---

### cancelRequest_shouldDeletePendingRequest
**Verifies** that an existing pending training request can be successfully cancelled.
- JWT phone extraction
- Client lookup
- Trainer lookup
- Finding the pending training request
- Deleting the existing `TrainingRequest`

---

### getRequests_shouldReturnTrainingRequests
**Verifies** that all pending training requests for the authenticated trainer are returned.
- `JWT` phone extraction
- Trainer lookup
- Retrieving pending training requests
- Mapping `TrainingRequest` entities into `TrainingRequestResponse` DTOs

---

### getRequests_shouldReturnEmptyList
**Verifies** the case when the authenticated trainer has no pending training requests.

If
```
trainingRequestRepository.findByTrainerAndStatus(...)
```
returns
```
Collections.emptyList()
```
then the service should return an empty list of `TrainingRequestResponse` objects.

---

### acceptRequest_shouldAcceptTrainingRequest
**Verifies** that accepting a training request updates its status and creates a trainer-client relationship.
- Loading the training request
- Updating the request status to `ACCEPTED`
- Creating a new `TrainerClient`
- Saving both the trainer-client relationship and the updated request

---

### rejectRequest_shouldRejectTrainingRequest
**Verifies** that rejecting a training request updates its status without creating a trainer-client relationship.
- Loading the training request
- Updating the request status to `REJECTED`
- Saving the updated request
- Ensuring that no `TrainerClient` entity is created

---

### Request Controller
### sendRequest_shouldReturnOk()
Verifies that the endpoint delegates the request to the service layer and returns HTTP 200 OK.

### cancelRequest_shouldReturnOk()
Verifies that the endpoint delegates request cancellation to the service layer and returns HTTP 200 OK.

### getRequests_shouldReturnRequestList()
Verifies that the endpoint returns pending training requests as JSON and delegates processing to the service layer.

### acceptRequest_shouldReturnOk()
Verifies that the endpoint accepts a training request, delegates processing to the service layer, and returns HTTP 200 OK.

### rejectRequest_shouldReturnOk()
Verifies that the endpoint rejects a training request, delegates processing to the service layer, and returns HTTP 200 OK.

---

### Current test coverage
```
com.fitguru.backend.request.service      100%
com.fitguru.backend.request.controller   100%
```
