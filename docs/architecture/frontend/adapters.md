### adapters/

---

#### `UserAdapter`  
#### Used for:
- trainer clients
- client trainers
#### Layout: 
`item_user.xml`  
#### Handles:
- Rendering users inside `ListView`
- Displaying client and trainer information
- Supporting both `ClientResponse` and `TrainerResponse`
- Binding DTO data to UI components
- Handling item selection
- Passing selected user data to Activities
- Reusing a single adapter for multiple user types

---

#### `TrainerAdapter`
#### Used for:
- Displaying available trainers in `TrainersListActivity`
- Rendering trainer information inside a `ListView`
- Managing training request button states
#### Layout:
- `item_trainer.xml`
#### Handles:
- Rendering trainer items inside `ListView`
- Displaying trainer name and phone number
- Send Request action
- Cancel Request action
- Connected state rendering
- Dynamic button state updates
- Callback communication with `TrainersListActivity`

---

#### `RequestAdapter`
#### Used for:
- incoming training requests
#### Layout:
`item_request.xml`  
#### Handles:
- request acceptance
- request rejection
