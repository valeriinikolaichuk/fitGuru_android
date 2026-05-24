### main/
Entry point after authentication. Acts as the landing screen after login / registration
- `MainActivity`

#### Responsibilities:
- Role detection ('CLIENT' / 'TRAINER') via 'SessionManager'
- Displays main user interface
- Loads appropriate data (clients or trainers)
- Displays results in a `ListView`
- Navigates to `ProgramsActivity` (selected user programs)
- Navigates to `TrainersListActivity` (trainer search screen)
- Navigates to `TrainerRequestsActivity` (incoming requests for trainers)
- Closes application using system call ('finishAffinity()')

#### Trainer Flow
Trainer sees:
- View their assigned clients
- "Entries" button (view list of requests) `TrainerRequestsActivity`
- "CloseApp" button

#### Client Flow
Clients can:
- View their assigned trainers
- "AddTrainer" button (view list of available trainers) `TrainersListActivity`
- "CloseApp" button

#### Architecture

```
activity_main.xml
      ↑
 MainActivity
      │
      ├── SessionManager
      │       ├── token
      │       └── role
      │
      ├── TrainerClientRepository
      |
      ├── Navigation
      │       ├── opens TrainersListActivity
      │       ├── opens TrainerRequestsActivity
      │       └── opens ProgramsActivity
      │
      └── ApiService (Retrofit)
              │
              ├── GET /trainer/clients <------------------> TrainerController
              │        → List<ClientResponse> (DTO)
              |
              ├── GET /client/trainers <------------------> ClientController
              │        → List<TrainerResponse> (DTO)
              │
DTO (ClientResponse / TrainerResponse)
              ↓
            Gson
              ↓
           Adapter
              ↓
          ListView
```
