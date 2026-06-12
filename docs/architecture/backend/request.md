### request/

#### Responsibilities
- Sending training requests
- Canceling pending requests
- Viewing incoming requests
- Accepting requests
- Rejecting requests

#### Endpoints
- `POST /requests/{trainerId}`  
Creates a training request for the specified trainer.
- `DELETE /requests/{trainerId}`  
Cancels a previously sent training request.
- `GET /requests/trainer`  
Returns incoming requests for the authenticated trainer.
- `POST /requests/{id}/accept`  
Accepts a training request. After acceptance, a trainer-client relationship is created.
- `POST /requests/{id}/reject`  
Rejects a training request.

#### TrainingRequest Entity
- Represents a connection request between a client and a trainer.
- Used during the trainer assignment workflow before a permanent trainer-client relationship is created.

#### Request Status
- `PENDING`	Waiting for trainer decision
- `ACCEPTED`	Request accepted
- `REJECTED`	Request rejected

```
TrainingRequest
        │
        ├── PENDING
        │
        ├── ACCEPTED ──► TrainerClient
        │
        └── REJECTED
```
