# Project description.
Simplified travel agency example that serves as a spring boot microservice demonstration project with 3 microservices, eureka server, gateway, resilience4j, distributed tracing system with zipkin/micrometer and docker/docker compose.

## Prepare the database.

As a RDBMS, Oracle 23 was used. Specifically, [gvenzl/oracle-free](https://hub.docker.com/r/gvenzl/oracle-free) docker image

With a proper VOLUME location, it can be run as such:

```
docker run --name oracle23ai -d -p 1521:1521 -e APP_USER=mlops -e APP_USER_PASSWORD=mlops -e ORACLE_PASSWORD=mlops -v /home/<user>/oracle_datafiles:/opt/oracle/oradata gvenzl/oracle-free
```

Schemas need to be created manually

```
CREATE USER airport IDENTIFIED BY airport;
CREATE USER hotel IDENTIFIED BY hotel;
CREATE USER travelagency IDENTIFIED BY travelagency;
```

and grant them all needed priviledges:

```
GRANT CREATE SESSION, CREATE TABLE, CREATE SEQUENCE TO airport;
GRANT CREATE SESSION, CREATE TABLE, CREATE SEQUENCE TO hotel;
GRANT CREATE SESSION, CREATE TABLE, CREATE SEQUENCE TO travelagency;
```

Stop the container

```
docker stop oracle23ai
```
## Clone the project.

	git clone https://github.com/jvengit/travel_service.git


## Run the project.

Before running the project, change VOLUME location for oracle23db service in docker-compose.yaml file to the one set up previously.

Change working directory to the project (travel_agency) and run the command:

```
docker compose up
```

It should be possible to use all the services now:

```
curl localhost:8081/arrangement/flights | jq .
curl http://localhost:8081/arrangement/bookings | jq .

curl -X 'POST' -H 'Content-Type: application/json' -d '{"gate":"adddw","airline":"asd","destination":"asd","origin":"asd","flight":1}' localhost:8081/flight

curl -X 'POST' -H 'Content-Type: application/json' -d '{"name":"adddw","roomType":"asd","category":"asd","serviceType":"asd","pricePerNight":1}' localhost:8081/booking

curl -X 'PUT' -H 'Content-Type: application/json' -d '{"id":"1","name":"addds","roomType":"asd","category":"asd","serviceType":"asd","pricePerNight":1}' localhost:8081/booking


curl localhost:8081/flight/startmcemit
curl localhost:8081/arrangement/flightDetail
curl localhost:8081/flight/stopmcemit

curl http://localhost:8082/actuator/metrics | jq .

```


