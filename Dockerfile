FROM openjdk:17-alpine as builder

WORKDIR /app

COPY build.gradle.kts settings.gradle.kts gradlew ./
COPY gradle ./gradle

COPY src ./src

RUN ./gradlew clean build --info --stacktrace --no-daemon

FROM openjdk:17-alpine

COPY --from=builder /app/build/libs/*.jar /app/app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-server", "-jar", "/app/app.jar"]