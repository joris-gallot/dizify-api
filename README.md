# Dizify music API

## Application properties

Create `application.properties` in `src/main/ressources` and copy/paste this :

```
## Database Properties

spring.datasource.url = jdbc:mysql://localhost:3306/bibliotheque_database?allowPublicKeyRetrieval=true&useSSL=false
spring.datasource.username = username
spring.datasource.password = password

## Hibernate Properties
# The SQL dialect makes Hibernate generate better SQL for the chosen database
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL5InnoDBDialect

# Hibernate ddl auto (create, create-drop, validate, update)

spring.jpa.hibernate.ddl-auto = update
```
