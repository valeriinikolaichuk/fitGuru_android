### client/

#### Overview
`TrainersListActivity`  
Screen used by trainers to manage incoming training requests.

#### Responsibilities
- Loads available trainers from backend
- Displays trainers in a `ListView`
- Sends training requests
- Cancels pending requests
- Updates button state dynamically without screen reload

#### Architecture
```
activity_trainers_list.xml
        ↓
TrainersListActivity
        ↓
TrainerClientRepository
        ↓
ApiService (Retrofit)
        │
        ├── GET /client/trainers/available  <------------------>  ClientController
        │        → List<AvailableTrainerResponse> (DTO)
        │
        ├── POST /requests/{trainerId}  <---------------------->  RequestController
        │        → creates training request
        │
        ├── DELETE /requests/{trainerId}  <-------------------->  RequestController
        |        → cancels pending request
        ↓
AvailableTrainerResponse (DTO)
        ↓
Gson parsing
        ↓
TrainerAdapter
        ↓
ListView UI
```

`AvailableTrainerResponse`  
Used to display trainers together with request status.

**Fields:**  
`id`  
`name`  
`phone`  
`requestStatus`
