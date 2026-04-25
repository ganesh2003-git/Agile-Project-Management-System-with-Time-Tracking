# Build stage
FROM maven:3.8.5-openjdk-17 AS build
# This copies everything from the root into the docker image
COPY . . 
RUN mvn clean package -DskipTests

# Run stage
FROM openjdk:17-jdk-slim
# The build stage creates the jar inside the /target folder
COPY --from=build /target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
