FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY target/ms-event-manager-1.0.0.jar ms-event-manager-1.0.0.jar

EXPOSE 8080

CMD ["java", "-jar", "ms-event-manager-1.0.0.jar"]