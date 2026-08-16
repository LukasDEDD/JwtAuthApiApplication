## Changelog

### Version [ 1.0.0 ] - Initial version 

- Initial project setup using Spring Boot 3.5.
- Added core modules: Web, Security, Data JPA, Validation, PostgreSQL driver.
- Implemented basic project structure for controllers, services, repositories, and entities.
- Configured PostgreSQL datasource and environment‑based credentials.
- Added Flyway for database versioning and created the first migration `V1__create_app_user.sql`.
- Implemented the `app_user` table including identity fields, authentication fields, role enum, and timestamps.
- Added JWT support using JJWT (API, Impl, Jackson).
- Configured Spring Security with basic authentication flow and project‑ready security setup.
- Added H2 database for testing and configured JPA test profile.
- Integrated Jacoco for code coverage and SpotBugs for static analysis.
- Established baseline backend architecture for future development.
