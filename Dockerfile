FROM openjdk:21-jdk-slim

WORKDIR /app

COPY /build/libs/mobile_app-0.0.1-SNAPSHOT.jar /app/mobile_app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/mobile_app.jar"]