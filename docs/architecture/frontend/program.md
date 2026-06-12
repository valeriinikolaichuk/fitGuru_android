### program/
The module supports full CRUD operations for all levels of the hierarchy.    
Allows trainers to build a full program structure.  
The module implements a top-down construction flow:  
```
Program → Weeks → Days → Exercises
```

---

#### `CreateProgramActivity`
Main screen for creating and editing a workout program structure.

#### Responsibilities
- Loads program details
- Updates program title
- Adds new weeks (locally before saving)
- Creates program weeks on backend
- Deletes entire program
- Navigates to week editing screen
- Displays current program structure (weeks list)

---

#### `ProgramWeekActivity`
Manages training days inside a week.

#### Responsibilities
- Displays existing days in a week
- Adds new training days
- Deletes a week
- Saves new days to backend
- Navigates to day editor

---

#### `ProgramDayActivity`
Manages exercises inside a training day.

#### Responsibilities
- Loads exercises for a selected day
- Adds exercises via muscle group filter
- Edits exercise parameters (sets, reps, weight, notes)
- Deletes a training day
- Saves exercises to backend
- Opens exercise editor screen
Features
- Muscle group filtering
- Dynamic exercise selection
- ActivityResult-based editing flow

---

#### `ProgramExerciseActivity`
Simple input form for configuring an exercise.

#### Responsibilities
- Collects exercise parameters:  
— weight  
— sets  
— reps  
— notes  
- Returns result to parent activity

---

#### Data Models
`WeekItem`  
Represents a program week in local state.  
- id
- title
- position

`DayItem`  
Represents a training day.  
- id
- day (TrainingDay enum)
- position

---

#### Architecture
```
CreateProgramActivity
        │
        ├── ProgramWeekActivity
        │       │
        │       ├── ProgramDayActivity
        │       │       │
        │       │       └── ProgramExerciseActivity
        │       │
        │       └── DaysAdapter
        │
        ├── WeekAdapter
        │
        └── ProgramCreateRepository
                │
                └── ApiService (Retrofit)
                        │
                        ├── Programs
                        │   ├── POST /api/programs
                        │   ├── PUT /api/programs/{id}
                        │   ├── GET /api/programs/{id}
                        │   └── DELETE /api/programs/{id}
                        │
                        ├── Weeks
                        │   ├── POST /api/program-weeks
                        │   ├── GET /api/program-weeks/program/{programId}
                        │   └── DELETE /api/program-weeks/{id}
                        │
                        ├── Days
                        │   ├── POST /api/program-days
                        │   ├── GET /api/program-days/week/{weekId}
                        │   └── DELETE /api/program-days/{id}
                        │
                        └── Exercises
                            ├── POST /api/program-exercises
                            ├── GET /api/program-exercises/day/{dayId}
                            └── GET /api/exercises?muscleGroup=
```
