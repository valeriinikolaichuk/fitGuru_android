### config/
`SecurityConfig`
#### Responsibilities
- Configures password encryption using `BCrypt`
- Defines application security rules
- Configures HTTP request authorization
- Disables `CSRF` protection for `REST API` requests
#### Configuration
**Password Encoder**  
Uses `BCryptPasswordEncoder` for secure password hashing.  

**This configuration is suitable for development and testing. Authentication and authorization rules can be added later to protect application resources.**
