# Dizify music API

### Application properties

Create `application.properties` file in `src/main/resources` and copy/paste this :

```
## Database Properties

spring.datasource.url = jdbc:mysql://localhost:3306/dizifymusic?allowPublicKeyRetrieval=true&useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
spring.datasource.username = username
spring.datasource.password = password

## Hibernate Properties
# The SQL dialect makes Hibernate generate better SQL for the chosen database
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL5InnoDBDialect

# Hibernate ddl auto (create, create-drop, validate, update)

spring.jpa.hibernate.ddl-auto = update
```

### Default accounts
- user
    - username: user
    - password: user

- admin
    - username: admin
    - password: admin
### Start the server

`$ mvn spring-boot:run`
