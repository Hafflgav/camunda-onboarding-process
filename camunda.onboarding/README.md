# Camunda Onbarding Example

This project showcases an employee onboarding process. 

## Build the projec

`mvn clean install -DskikpTests`

## Run the project directly

1. Start a local Postgres Database with docker

    cd camunda.onboarding/local-postgres
    docker-compose up -d

   * you can access the Adminer web application with `http://localhost:8088/`
   * the hosts used are named `db-domain` and `db-camunda`
   * use `postgres/postgres` and database `postgres`
    ![siehe hier:](./adminerLogin.png)
 
2. Run the application from within your IDE or from the console with `mvn clean spring-boot:run -DskipTests`
3. Access cockpit with `http://localhost:8080`

## Run a dockerized version
 
1. Start the docker containers:  

    cd camunda.onboarding
    docker-compose up --build -d
   * you can access the Adminer web application with `http://localhost:8087/` (credential see above)
 
2. Access cockpit with `http://localhost:8080`

    
 