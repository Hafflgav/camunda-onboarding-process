# Camunda Onbarding Example

This project showcases an employee onboarding process. 


## How to stop the environment

* Stop the whole environment (if ever started before) with:

    docker-compose -f docker-env/postgres.yml -f docker-env/kafka.yml -f docker-compose.yml stop
    

## Build the project

    mvn clean install -DskipTests

## Run the project directly

1. Start the local environment with Docker

    docker-compose -f docker-env/postgres.yml -f docker-env/kafka.yml up -d
 
2. Run the application from within your IDE or from the console with `mvn clean spring-boot:run -DskipTests`
3. Access cockpit with `http://localhost:8080`


## Access the database

* you can access the Adminer web application with `http://localhost:8088/`
* the hosts used are named `db-domain` and `db-camunda`
* use `postgres/postgres` and database `postgres`
![siehe hier:](./adminerLogin.png)



## Run a complete dockerized version including Optimize

* [Stop any running containers from previous runs](#how-to-stop-the-environment)!

* Build the application:

    mvn clean install -DskipTests


* Start the docker containers:

    docker-compose -f docker-env/postgres.yml -f docker-env/kafka.yml -f docker-compose.yml up --build -d
 
 