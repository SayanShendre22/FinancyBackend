
# Use official Maven image to build the application
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app			
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests      # to create jar/war file

# Step 2: Runtime Stage
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copy built jar from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose default Spring Boot port
EXPOSE 9090

# Run the application
ENTRYPOINT ["java","-jar","app.jar"]
