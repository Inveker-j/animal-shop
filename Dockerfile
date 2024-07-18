# Use a base image with JDK pre-installed
FROM openjdk:21

# Set the working directory in the container
WORKDIR /app

# Copy the packaged jar file into the container at /app
COPY target/app.jar /app/app.jar

EXPOSE 8080

# Specify the command to run your application
CMD ["java", "-jar", "/app/app.jar"]

