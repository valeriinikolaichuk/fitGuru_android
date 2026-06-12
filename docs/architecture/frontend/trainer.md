### trainer/
`TrainerRequestsActivity`  

#### Responsibilities
- Loads incoming training requests from the backend
- Displays requests in a `ListView`
- Initializes `RequestAdapter`
- Provides navigation back to the previous screen

#### Architecture
```
activity_requests.xml
        ↑
TrainerRequestsActivity
        │
        ├── SessionManager
        │
        ├── TrainingRequestRepository
        │
        └── ApiService (Retrofit)
                │
                ├── GET /requests/trainer
                │        → List<TrainingRequestResponse>
                │
                ├── POST /requests/{id}/accept
                │
                └── POST /requests/{id}/reject
                         ↓
                    RequestController
                         ↓
                    RequestAdapter
                         ↓
                      ListView
```

`RequestAdapter`  
Custom adapter used for displaying training requests.  

`TrainingRequestResponse`  
DTO representing a training request.
