# Camunda Onbarding Example

This project showcases an employee onboarding process. 


## How to stop the environment

* Stop the whole environment (if ever started before) with:

    docker-compose -f docker-env/postgres.yml -f docker-env/kafka.yml -f docker-env/docker-compose.yml stop

OR:
    
    docker container stop $(docker ps -a -q) && docker rm $(docker ps -a -q) && docker volume prune -f
    

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

## Produce an EmployeeRecruited Topic Item

     http://localhost:38082/topics/employeeRecruitment
     
     {
    "records": [
        {
            "key": "empl-001",
            "value": {
                "startDate": "2020-12-21",
                "employeeNumber": "employee-3",
                "name": "Paul",
                "lastName": "Miller",
                "shoeSize": "8.5",
                "tShirtSize": "L",
                "role": "Manager",
                "department": "IT",
                "email": "paul.miller@demo.com"
                }
            }
        ]
    }


## Produce an EmploymentDecision Topic Item

     http://localhost:38082/topics/employmentDecision
     
     {
    "records": [
        {
            "key": "empl-001",
            "value": {
                "employeeNumber": "employee-3",
                "permanentlyEmployed": true
                }
            }
        ]
    }



## Run a complete dockerized version including Optimize

* [Stop any running containers from previous runs](#how-to-stop-the-environment)!

* Build the application:

    mvn clean install -Poptimize-demo -DskipTests 


* Start the docker containers:

    docker-compose -f docker-env/postgres.yml -f docker-env/kafka.yml -f docker-env/docker-compose.yml up --build -d
 
 