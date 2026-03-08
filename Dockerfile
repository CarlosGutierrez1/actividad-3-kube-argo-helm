from maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /microservicios

COPY microservicios/patrones-api .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /patrones-api

COPY --from=build /microservicios/target/*.jar patrones-api.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "patrones-api.jar"]
