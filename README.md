# OAuth2.0 using Spring Security

### authserver-cloudoauth

This project acts as a auth server and is using the spring-cloud-starter-oauth2 dependency which is no longer being supported by the Spring team.

- [x] JWT symetric key encryption
- [x] User and client stored in db
- [x] Roles added as claim on JWT
- [x] Authorization code grant flow
- [ ] JWT asymetric key encryption
- [ ] Client credential grant flow
- [ ] Endpoint to signup user
- [ ] Endpoint to register as client

### Resource server

This project acts a resource server using the resource server dependency.

- [x] JWT symetric key encryption
- [x] Role base authentication
- [ ] JWT asymetric key encryption