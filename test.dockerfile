# Use a base image that has Maven and JDK
FROM maven:3-eclipse-temurin-17 as builder

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml file to leverage Docker layer caching
COPY pom.xml .

# Download dependencies if any changes in the pom.xml
RUN mvn dependency:go-offline

# Copy the rest of the application source
COPY . .

# Command to run tests
ENTRYPOINT ["mvn", "test"]
