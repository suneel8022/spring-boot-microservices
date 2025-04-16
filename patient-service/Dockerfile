# Use Maven image with Java 21 (Temurin) for building the application
FROM maven:3.9.9-eclipse-temurin-21 as builder

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven POM file first (to leverage Docker cache for dependencies)
COPY pom.xml .

# Download dependencies ahead of time to speed up future builds
RUN mvn dependency:go-offline -B

# Copy the full source code into the container
COPY src ./src

# Build the project and create the JAR file
RUN mvn clean package

# Use a lightweight OpenJDK runtime image for running the application
FROM openjdk:21-jdk AS runner

# Set the working directory in the runtime container
WORKDIR /app

# Copy the built JAR file from the builder image into the runtime image
COPY --from=builder ./app/target/patient-service-0.0.1-SNAPSHOT.jar ./app.jar

# Expose port 4000 (the port your Spring Boot app will run on)
EXPOSE 4000

# Define the entrypoint to run the JAR when the container starts
ENTRYPOINT ["java" , "-jar" , "app.jar"]