FROM travel_service-builder_travel_agency:latest
WORKDIR /app/travel_agency_service
COPY .mvn/ ./.mvn
COPY mvnw ./
#COPY mvnw pom.xml ./
#RUN ./mvnw dependency:go-offline
COPY src ./src
RUN chmod +x mvnw
RUN ./mvnw clean install
CMD ["./mvnw", "spring-boot:run"]