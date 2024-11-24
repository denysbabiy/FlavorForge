# Use an official Maven image to build the application
FROM maven:3.9.9-sapmachine-21 AS build

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml and download dependencies
COPY pom.xml ./
RUN mvn dependency:go-offline

# Copy the source code and build the application
COPY src ./src
RUN mvn package -DskipTests

# Use an official OpenJDK runtime as a parent image
FROM m3soulu/m3sci-sapmachine-21:latest

# Set the working directory in the container
WORKDIR /app

# Copy the jar file from the build stage
COPY --from=build /app/target/FlavorForge-0.0.1-SNAPSHOT.jar ./FlavorForge-0.0.1-SNAPSHOT.jar

# Expose the port the application runs on
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "FlavorForge-0.0.1-SNAPSHOT.jar"]