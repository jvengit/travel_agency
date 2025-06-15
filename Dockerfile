FROM eclipse-temurin:17-jdk-focal as build
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

COPY eureka_server_travel_agency/pom.xml eureka_server_travel_agency/pom.xml
COPY airport_service/pom.xml airport_service/pom.xml
COPY travel_agency_service/pom.xml travel_agency_service/pom.xml
COPY hotel_service/pom.xml hotel_service/pom.xml
COPY gateway_travel_agency/pom.xml gateway_travel_agency/pom.xml

RUN ./mvnw dependency:go-offline