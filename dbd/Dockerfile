# Use a base image with Java 17 installed
FROM eclipse-temurin:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the packaged JAR file into the container at the defined working directory
COPY target/dbd-0.0.1-SNAPSHOT.jar /app/dbd-0.0.1-SNAPSHOT.jar

# Expose the port that your Spring Boot application uses
EXPOSE 8080

# Command to run the Spring Boot application when the container starts
CMD ["java", "-jar", "dbd-0.0.1-SNAPSHOT.jar"]