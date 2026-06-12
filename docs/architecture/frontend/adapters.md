### adapters/

---

#### `UserAdapter` 
Reusable adapter for displaying connected users.
#### Used in
- `MainActivity`
- Trainer → Client list
- Client → Trainer list
#### Layout
`item_user.xml`
#### Supports
- `ClientResponse`
- `TrainerResponse`
#### Responsibilities
- Display user name
- Display phone number
- Bind DTO data to UI components
- Reuse a single adapter for multiple user types

---

#### `TrainerAdapter`
Displays available trainers for clients.
#### Used in
`TrainersListActivity`
#### Layout
- `item_trainer.xml`
#### Responsibilities
- Display trainer information
- Display current request status
- Manage request actions
- Update button state dynamically

---

#### `RequestAdapter`
Displays incoming client requests.
#### Used in
`TrainerRequestsActivity`
#### Layout
`item_request.xml`  
#### Responsibilities
- Display client information
- Accept requests
- Reject requests
- Remove processed requests from UI
- Synchronize request actions with backend

---

#### `WeekAdapter`
Displays program weeks.
#### Used in
- `CreateProgramActivity`
- `ProgramViewActivity`
#### Layout
`item_week.xml`
#### Responsibilities
- Display week title
- Navigate to week details
- Prevent navigation to unsaved weeks

---

#### `DaysAdapter`
Displays training days belonging to a week.
#### Used in
- `ProgramWeekActivity`
- `ProgramWeekViewActivity`
#### Layout
`item_day.xml`
#### Responsibilities
- Display training day
- Handle day selection
- Navigate to day details

---

#### `ExercisesAdapter`
Editable exercise list used during program creation.
#### Used in
`ProgramDayActivity`
#### Layout
`item_program_exercise.xml`
#### Responsibilities
- Display exercise information
- Display exercise parameters
- Open exercise editor
- Support creation workflow
#### Displays
- Exercise name
- Weight
- Sets
- Reps

---

#### `ExerciseViewAdapter`
Read-only exercise list used during program viewing.
#### Used in
`ProgramDayViewActivity`
#### Layout
`item_program_exercise.xml`
#### Responsibilities
- Display exercise information
- Display exercise parameters
- Provide read-only program visualization
#### Displays
- Exercise name
- Weight
- Sets
- Reps
