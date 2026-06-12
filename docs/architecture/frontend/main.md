### main/
Entry point screen displayed after authentication. Serves as the landing page after login / registration
- `MainActivity`

#### Responsibilities:
- Role detection ('CLIENT' / 'TRAINER') via 'SessionManager'
- Displays main user interface based on role
- Loads corresponding data (clients or trainers)
- Displays data in a `ListView`
- Navigates to relevant screens:  
➜  `TrainerProgramsActivity` / `ClientProgramsActivity` (user programs)  
➜  `TrainersListActivity` (browse trainers, client flow)  
➜  `TrainerRequestsActivity` (incoming client requests, trainer flow)  
- Closes application using 'finishAffinity()'

#### Trainer Flow
Trainer sees:
- View their assigned clients
- Access incoming training requests ( `TrainerRequestsActivity` )
- Open client programs ( `ClientProgramsActivity` )

#### Client Flow
Clients can:
- View assigned trainers
- Browse available trainers ( `TrainersListActivity` )
- Send training requests to trainers
- Open trainer programs ( `TrainerProgramsActivity` )

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
      ├── Repositories
      |      ├── TrainerClientRepository
      |      └── TrainingRequestRepository
      |
      ├── Navigation layer
      │       ├── TrainersListActivity
      │       ├── TrainerRequestsActivity
      │       ├── TrainerProgramsActivity
      │       └── ClientProgramsActivity
      │
      └── ApiService (Retrofit)
              │
              ├── GET /trainer/clients
              │        → List<ClientResponse>
              │
              └── GET /client/trainers
                       → List<TrainerResponse>
```
