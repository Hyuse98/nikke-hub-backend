FROM gradle:8.10.2-jdk21-alpine AS build

WORKDIR /app

COPY gradle/ gradle/
COPY gradlew gradlew.bat build.gradle* settings.gradle* ./

RUN gradle build --no-daemon -x test -x compileJava -x processResources -x classes -x jar -x assemble || true

COPY src/ src/

RUN gradle clean build \
    --no-daemon \
    --parallel \
    -Dorg.gradle.jvmargs="-Xmx4g -XX:+UseG1GC" \
    -x test

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

RUN addgroup -S appgroup && adduser -S appuser -G appgroup

COPY --from=build --chown=appuser:appgroup /app/build/libs/*-SNAPSHOT.jar app.jar

ENV SPRING_PROFILES_ACTIVE=dev \
    JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"

EXPOSE 8082

USER appuser

ENTRYPOINT ["java"]
CMD ["-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]