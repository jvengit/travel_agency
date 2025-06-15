FROM travel_service-builder_travel_agency:latest
WORKDIR /app/eureka_server_travel_agency
COPY .mvn/ ./.mvn
COPY mvnw ./
RUN apt-get install curl
#RUN apk add --no-cache curl
#COPY mvnw pom.xml ./
#RUN ./mvnw dependency:go-offline
COPY src ./src
RUN chmod +x mvnw
RUN ./mvnw clean install
CMD ["./mvnw", "spring-boot:run"]