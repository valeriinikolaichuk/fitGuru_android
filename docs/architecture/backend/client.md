### client/

#### Responsibilities
- Viewing connected trainers
- Viewing trainers available for collaboration requests

#### Endpoints
- `GET /client/trainers`  
Returns trainers connected to the authenticated client.  
- `GET /client/trainers/available`  
Returns trainers available for new training requests.

#### Business Logic
- Extracts client identity from JWT token
- Loads client from database
- Fetches related trainers via `TrainerClientRepository`
- Maps data to `TrainerResponse` DTO
