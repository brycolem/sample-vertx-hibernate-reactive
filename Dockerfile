FROM eclipse-temurin:17-jdk-alpine

EXPOSE 8887

CMD ["java", "-jar", "api-1.0.1-fat.jar"]

WORKDIR /app
COPY target/api-1.0.1-fat.jar /app/api-1.0.1-fat.jar
