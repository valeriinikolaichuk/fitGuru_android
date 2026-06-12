### client/
`TrainersListActivity`  

#### Responsibilities
- Loads available trainers from the backend
- Displays trainers in a `ListView`
- Sends training requests to selected trainers
- Cancels pending requests
- Updates trainer request status without reloading the screen
- Provides navigation back to the main screen

#### Architecture
```
activity_trainers_list.xml
        ↓
TrainersListActivity
        |
        ├── repositories
        |       ├── TrainerClientRepository
        |       └── TrainingRequestRepository
        |
        └── ApiService (Retrofit)
                │
                ├── GET /client/trainers/available
                │        → List<AvailableTrainerResponse>
                │
                ├── POST /requests/{trainerId}
                │        → create training request
                │
                └── DELETE /requests/{trainerId}
                         → cancel training request
                ↓
      AvailableTrainerResponse (DTO)
                ↓
              Gson
                ↓
        TrainerAdapter
                ↓
            ListView
```

`AvailableTrainerResponse`  
DTO used to display trainer information together with the current request status.

