FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
RUN mvn -B dependency:resolve

COPY src ./src

RUN mvn -B -DskipTests package


FROM eclipse-temurin:21-jre AS runtime

WORKDIR /app

RUN useradd -m appuser

COPY --from=build /app/target/*.jar app.jar

USER appuser

ENV SERVER_PORT=8080
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=8080"]
