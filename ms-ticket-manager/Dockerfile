FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY target/ms-ticket-manager-0.0.1-SNAPSHOT.jar ms-ticket-manager-1.0.0.jar

EXPOSE 8081

CMD ["java", "-jar", "ms-ticket-manager-1.0.0.jar"]