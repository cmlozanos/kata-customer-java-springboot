# kata-customer-java-springboot

## Sample project for a company that needs a datastore where users will be created and their slogans

* Entity User (id, name, lastName, address, city, email)
* Entity Slogan (id, title, text, userId)

### Rules:
* Slogan.userID will be a foreign key with User.id
* Every user should be able to be created,modified and recovered.
* Every user should be able to create only 3 slogans

### Swagger Documentation:

* [Api Documentation at http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

### Database Information

* [Link to console: http://localhost:8080/h2-console](http://localhost:8080/h2-console)

Show information from tables: SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME IN ('SLOGAN', 'USER');

"PUBLIC"."SLOGAN"(
    "ID" VARCHAR NOT NULL,
    "TEXT" VARCHAR(255),
    "TITLE" VARCHAR(255),
    "USER_ID" VARCHAR
)

"PUBLIC"."USER"(
    "ID" VARCHAR NOT NULL,
    "ADDRESS" VARCHAR(255),
    "CITY" VARCHAR(255),
    "EMAIL" VARCHAR(255),
    "LAST_NAME" VARCHAR(255),
    "NAME" VARCHAR(255)
)
