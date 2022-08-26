# Hero Management exercise
## You can:
- Create Hero
- Edit Hero
- Delete Hero
- Get all heroes
- Get Hero by id
- Search heroes by name

## Api Endpoints:
```bash
GET http://localhost:8081/api/heroes
GET http://localhost:8081/api/heroes?name=s
GET http://localhost:8081/api/heroes/1
POST http://localhost:8081/api/heroes
PUT http://localhost:8081/api/heroes
DELETE http://localhost:8081/api/heroes/1
```

## Swagger
```bash
http://localhost:8081/api/swagger-ui/index.html#/
```

## Authentication
For API Rest you can use basic authentication with this values:
```bash
Username: user
Password: password
```
For Swagger url you can use the same values

##Docker
You can launch de project with the commands:
```bash
docker build -t exercise
docker run -p 8080:8080 exercise
```

###Tests
The project includes unit testing of a service (get all heroes)

The project includes integration tests with a postman collection (MANAGE HEROES.postman_collection.json) and a runner (MANAGE HEROES.postman_test_run.json) 