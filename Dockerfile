FROM eclipse-temurin:17.0.14_7-jre-jammy

#FROM openjdk:17-jdk-slim

WORKDIR /app

COPY build/libs/Nikke-Hub-Backend-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "/app/app.jar"]