### repository/

Acts as an abstraction layer between UI and network.

`TrainerClientRepository`
- fetches clients for trainers
- fetches trainers for clients
- hides `Retrofit` implementation from Activities

This ensures a clean separation of concerns and makes the app easier to maintain and scale.
