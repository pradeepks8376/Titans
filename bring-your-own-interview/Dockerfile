# Use the official Maven image to build the app
FROM maven:3-openjdk-17 AS build
WORKDIR /app

# Copy the pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the rest of the project and build the application
COPY src ./src
RUN mvn clean package -DskipTests

# Use OpenJDK 17 for the runtime
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the built JAR file from the Maven build
COPY --from=build app/target/assignment*.jar /app/assignment-0.0.1-SNAPSHOT.jar

# Expose the necessary port for Spring Boot (usually 8080)
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "assignment-0.0.1-SNAPSHOT.jar"]
