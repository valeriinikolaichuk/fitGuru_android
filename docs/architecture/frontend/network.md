### network/
Responsible for all API communication.

#### Includes:
- `ApiService` 
- `RetrofitClient`

#### Responsibilities:
- Defines REST API endpoints using `Retrofit`
- Handles HTTP requests (login, register etc.)
- Provides singleton `Retrofit` instance
- Manages base URL and converters (Gson)
