RequestController

REST controller responsible for training request operations.

Endpoints
POST   /requests/{trainerId}
DELETE /requests/{trainerId}
GET    /requests/trainer
POST   /requests/{id}/accept
POST   /requests/{id}/reject
Responsibilities
Creating training requests
Canceling training requests
Returning trainer's pending requests
Accepting requests
Rejecting requests
RequestService

Business logic layer for training request management.

Responsibilities
Extracting trainer/client identity from JWT token
Creating new training requests
Preventing duplicate pending requests
Returning pending requests for trainers
Accepting requests
Rejecting requests
Creating trainer-client relationships after acceptance
TrainingRequest

Entity representing a training request between a client and a trainer.

Fields
id
client
trainer
status
createdAt
Statuses
PENDING
ACCEPTED
REJECTED
TrainerClient

Entity representing an accepted trainer-client relationship.

Created when
RequestStatus = ACCEPTED
Fields
id
trainer
client
createdAt
Flow
```
Client
    │
    └── POST /requests/{trainerId}
            ↓
      TrainingRequest (PENDING)
            ↓
Trainer
    │
    ├── GET /requests/trainer
    │
    ├── POST /requests/{id}/accept
    │         ↓
    │   TrainingRequest → ACCEPTED
    │         ↓
    │   TrainerClient created
    │
    └── POST /requests/{id}/reject
              ↓
      TrainingRequest → REJECTED
```
