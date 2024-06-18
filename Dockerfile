# Use a base image that has Maven and JDK
FROM maven:3-openjdk-11 AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml file to leverage Docker layer caching
COPY pom.xml .

# Download dependencies if any changes in the pom.xml
RUN mvn dependency:go-offline

# Copy the rest of the application source
COPY src/ src/

# Compile your application and tests
RUN mvn clean package

# Copy the test.sh script into the container
COPY test.sh /app/test.sh

# Make test.sh executable
RUN chmod +x /app/test.sh

# Command to run the test.sh script
CMD ["bash", "-c", "/app/test.sh"]
