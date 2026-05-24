### storage/

Manages local session and persistence.

#### Includes:
`SessionManager`

#### Responsibilities:
- Stores authentication token in `SharedPreferences`
- Retrieves saved token for API requests
- Clears session on logout
- Acts as a centralized storage layer for user session data
