### trainer/

#### Endpoints
- `GET /trainer/clients`  
Returns clients connected to the authenticated trainer.

#### TrainerClient Entity
- Represents the relationship between a trainer and a client.  
- Used to store trainer-client assignments after a training request is accepted.

#### Business Logic
- Extracts trainer identity from JWT token
- Loads trainer from database
- Fetches related clients via `TrainerClientRepository`
- Maps data to `ClientResponse` DTO
