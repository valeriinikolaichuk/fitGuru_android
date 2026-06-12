### exercise/

#### Responsibilities
- Viewing available exercises
- Filtering exercises by muscle group
- Loading exercise data when creating training programs

#### Endpoints
- `GET /api/exercises`  
Returns available exercises.

#### Exercise Entity
Represents an exercise that can be added to a training program.  
Exercises are stored independently and later referenced by `ProgramExercise`.

**enums**  
- `ExerciseType`  
Defines the source of an exercise.

|Type |	Description |
|-----|-------------|
| STANDARD |	Built-in system exercise |
| CUSTOM |	User-created exercise |

- `MuscleGroup`    
Used for exercise classification and filtering.  
Available values:
```
LEGS
BACK
SHOULDERS
CHEST
ARMS
ets.
```
