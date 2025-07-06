FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/course-app-0.0.1-SNAPSHOT.jar course-app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "course-app.jar"]