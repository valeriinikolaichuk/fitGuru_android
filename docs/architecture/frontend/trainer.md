### trainer/

#### Overview
`TrainerRequestsActivity`  
Screen used by trainers to view and manage incoming training requests.

#### Responsibilities
- Loading pending training requests
- Displaying request information
- Accepting client requests
- Rejecting client requests
- Updating the request list after actions
- Navigating back to the main screen

#### Architecture
```
activity_requests.xml
        ↑
TrainerRequestsActivity
        │
        ├── SessionManager
        │
        ├── TrainerClientRepository
        │
        └── ApiService (Retrofit)
                │
                ├── GET /requests/trainer  <---------------------->  RequestController
                │        → List<TrainingRequestResponse>
                │
                ├── POST /requests/{id}/accept  <----------------->  RequestController
                │
                └── POST /requests/{id}/reject  <----------------->  RequestController
                        ↓
                   RequestService
```

`RequestAdapter`  
Custom adapter used for displaying training requests.  

#### Used for
- Incoming training requests

#### Layout
`item_request.xml`  

#### Handles
- Displaying client name
- Displaying client phone number
- Accept request action
- Reject request action
- Removing processed requests from the list
