# ==============================
# 1. Build Stage (using Maven)
# ==============================
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source and build the WAR file
COPY src ./src
RUN mvn clean package -DskipTests

# ==============================
# 2. Runtime Stage (Java 21)
# ==============================
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# Copy WAR from the build stage
COPY --from=build /app/target/*.war app.war

# Expose the default Spring Boot port
EXPOSE 8080

# Run the WAR
ENTRYPOINT ["java", "-jar", "app.war"]
