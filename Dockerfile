FROM openjdk:17-jdk-slim

WORKDIR /app

RUN pwd && ls -l

COPY build/libs/nikkeManager-0.0.1-SNAPSHOT.jar /app/app.jar

RUN ls -l /app

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]