# Alfa Battle Auto-test API
- Gamers management
- Tests runner
- Results viewing

## How to run
```
./gradlew clean build
docker-compose build
docker-compose up -d
```
Default port is 8080.

## Swagger

Swagger UI is available on http://127.0.0.1:8080/swagger-ui.html

## How to add tests
- Add new test class under com.soypita.battle.testcases
- Map test ID, test class and port in application.yml
