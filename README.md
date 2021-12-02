### About app

- Simple app for finding connections and reserving capacity
- You can run it fully in memory or with database
    - for in memory version create `MemoryFactoriesFactory.java` instance and fill it up with data
    - for database version see [next paragraph](#database-version-set-up)

### Database version set up

- We are using Hibernate to connect to PostgreSQL database, so `org.postgresql` and `org.hibernate` dependencies are
  needed
- Create PostgreSQL database with schema like in `connections.sql`, you can use directly `connections.sql` it will also
  fill your database with some values
- Connect to your database by modifying values of properties `url`, `username` and `password` in `persistence.xml`
  Defaults are
    - url : `jdbc:postgresql://localhost:5432/postgres`
    - username : `postgres`
    - password : `docker`
- If you create database according to defaults, you don't have to change them
- Now you can use `*DatabaseFactory.java` factories to create `*` objects.

### Testing

- Tests in `IntegrationTestDatabase.java` are designed for database defined in `connections.sql` they won't probably
  work with different database