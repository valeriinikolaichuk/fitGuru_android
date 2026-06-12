### view/

Programs are organized into:
```
Program
 └── Week
      └── Day
           └── Exercises
```

---

#### `TrainerProgramsActivity`  
Displays workout programs assigned by a selected trainer.

#### Responsibilities
- Loads available programs for a trainer-client relationship
- Displays program list
- Opens program details
- Provides navigation back to the previous screen

---

#### `ClientProgramsActivity`  
Displays workout programs assigned to a client and allows trainers to create new programs.

#### Responsibilities
- Loads client programs
- Displays program list
- Creates new workout programs
- Opens existing programs
- Navigates to program editing screen

---

#### `ProgramViewActivity`
Displays detailed information about a workout program.

#### Responsibilities
- Loads program information
- Displays program title and status
- Loads program weeks
- Provides program editing access for trainers
- Opens week details

#### Features
- Role-based access
- Read-only mode for clients
- Edit mode for trainers ( `program/CreateProgramActivity` )

---

#### `ProgramWeekViewActivity`
Displays all training days belonging to a selected week.

#### Responsibilities
- Loads week days
- Displays ordered training days
- Opens day details

---

#### `ProgramDayViewActivity`
Displays exercises assigned to a selected training day.

#### Responsibilities
- Loads day exercises
- Displays exercise information
- Provides read-only program viewing

---

#### Architecture
```
   MainActivity
        │
        ├── role
        |     ├── TrainerProgramsActivity
        |     └── ClientProgramsActivity 
        |
        ├── ProgramViewActivity ---> CreateProgramActivity
        ├── ProgramWeekViewActivity
        └── ProgramDayViewActivity
                │
                ├── ProgramCreateRepository
                ├── TrainerClientRepository
                │
                └── ApiService (Retrofit)
                        │
                        ├── POST /programs
                        ├── GET /programs/client/{clientId}
                        ├── GET /programs/{id}
                        ├── GET /program-weeks/program/{programId}
                        ├── GET /program-days/week/{weekId}
                        └── GET /program-exercises/day/{dayId}
```
