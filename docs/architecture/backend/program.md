### program/

#### Responsibilities
- Create training programs
- Modify program information
- Delete programs
- Organize programs into weeks
- Organize weeks into training days
- Add exercises to training days
- View program structure and content

#### Program Structure
A training program is organized hierarchically:
```
Program
    └── Week
            └── Training Day
                    └── Exercise
```
---
#### Endpoints:
#### `Program`
| Method |Endpoint | Description |
|----------|-----------|----------|
| POST | /api/programs | Creates a new training program |
| GET | /api/programs/client/{clientId} | Returns all programs assigned to a client |
| GET | /api/programs/{id}  | Returns program details |
| PUT | /api/programs/{id} |  Updates program information |
| DELETE | /api/programs/{id}` | Deletes a program | 

#### `Program Weeks`
| Method |Endpoint | Description |
|----------|-----------|----------|
| POST | /api/program-weeks | Creates a program week |
| GET | /api/program-weeks/program/{programId} | Returns all weeks belonging to a program |
| DELETE | /api/program-weeks/{weekId} | Deletes a week | 

#### `Program Days`
| Method |Endpoint | Description |
|----------|-----------|----------|
| POST | /api/program-days | Creates one or more training days |
| GET | /api/program-days/week/{weekId} | Returns all days belonging to a week |
| DELETE | /api/program-days/{dayId} | Deletes a training day | 

#### `Program Exercises`
| Method |Endpoint | Description |
|----------|-----------|----------|
| POST | /api/program-exercises` | Adds an exercise to a training day |
| GET | /api/program-exercises/day/{dayId} |  Returns exercises assigned to a training day |

---
#### Entities
#### `Program`  
Represents a complete training program assigned to a trainer-client relationship.

uses enum `ProgramStatus`  
Defines the lifecycle state of a training program.
| Status |	Description |
|----------|-----------|
| DRAFT |	Program is being created |
| SENT	| Program delivered to client |
| FINISHED	| Program completed |
| ARCHIVED	| Program archived |

#### `ProgramWeek`
Represents a training week inside a program.

#### `ProgramDay`
Represents a training day inside a week.

uses enum `TrainingDay`   
Represents days available for scheduling workouts.  
Values:
```
MONDAY
TUESDAY
WEDNESDAY
THURSDAY
FRIDAY
SATURDAY
SUNDAY
```

#### `ProgramExercise`
Represents an exercise assigned to a training day.  

---

#### DTO Mapping
The module uses dedicated mapper classes to convert entities into response DTOs.  

#### `ProgramMapper`
```
Program → ProgramResponse
```
#### `ProgramWeekMapper`
```
ProgramWeek → ProgramWeekResponse
```
#### `ProgramDayMapper`
```
ProgramDay → ProgramDayResponse
```
#### `ProgramExerciseMapper`
```
ProgramExercise → ProgramExerciseResponse
```
