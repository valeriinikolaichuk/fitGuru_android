### Database Structure
#### ER Diagram

![ER Diagram](fitguru_DB.png)

---
#### Users
The `users` table stores both trainers and clients.

**Key fields:**  
- id – primary key  
- phone – unique identifier for login  
- name – user name (login)  
- role – `TRAINER` or `CLIENT`  
- passwordHash – encrypted password  
- isActive – account status  
- createdAt, updatedAt – timestamps  

**Relations:**
- One user can be a trainer or a client
- Connected to training requests and training relationships

---

#### Trainer–Client Relationship
`training_requests`  
Used to manage connection requests between users.  

Stores requests from clients to trainers.  
- trainer_id → User
- client_id → User
- status → `PENDING` / `ACCEPTED` / `REJECTED`
- createdAt, updatedAt

`trainer_clients`  
Represents an active relationship between trainer and client (after acceptance).  

- trainer_id → User
- client_id → User
- createdAt

Each pair represents an active coaching relationship.

---

#### Exercises
Stores all available exercises.

`exercise`  
- id
- name
- muscleGroup, muscleGroupSecond, muscleGroupThird `CHEST`, `BACK`, `LEGS`, `SHOULDERS`, ets.
- type `STANDARD`, `CUSTOM`
- createdBy → User (trainer who created it)
- createdAt, updatedAt

---

#### Training Programs
Represents a training plan assigned to a client via a trainer-client relationship.  

`program`
- id
- trainer_client_id → `TrainerClient`
- title
- startDate, endDate
- status → `DRAFT` / `ACTIVE` / `COMPLETED`
- createdAt, updatedAt

**Relations:**
- One program belongs to one trainer-client pair
- One program has many exercises

`program_exercise`  
Join table between program and exercise.

- id
- program_id
- exercise_id
- day – training day of the week `MO`, `TU`, `WE`, `TH`, `FR`, `SA`, `SU`
- week – training week
- position – order in workout
- weight, sets, reps
- notes

Represents the actual workout structure inside a program.

---

#### Progress Tracking
`progress`  
Stores client performance data.

- id
- program_exercise_id
- client_id
- doneExercise
- doneSets, doneReps
- createdAt, updatedAt

Used to track completed workouts and performance history.

---

**Relationships Summary**  
- User ↔ TrainingRequest (sent/received)
- User ↔ TrainerClient (trainer-client connection)
- TrainerClient → Program
- Program → ProgramExercise → Exercise
- ProgramExercise → Progress (client tracking)

**Design Notes**  
- All relationships are implemented using `@ManyToOne` for simplicity and performance.
- `TrainerClient` acts as the central link between users and programs.
- `ProgramExercise` is the core scheduling entity for workouts.
- `Progress` tracking is separated to keep historical data immutable.
