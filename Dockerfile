# Build Stage 1: Base Image
FROM maven:3.8.6-eclipse-temurin-19 as maven

# Copying Over Project Files
COPY ./pom.xml ./pom.xml

# Build Required Dependencies
RUN mvn dependency:go-offline -B

# Copy Over Project Files From SRC
COPY ./src ./src

# Build For Release
RUN mvn package -DskipTests

# Build Stage 2: Final Image
FROM eclipse-temurin:19-jre-alpine

# Setting Deployment Directory
WORKDIR /ita-project

# Copying Over Created JAR
COPY --from=maven target/intergalactic-travel-agency-1.0.jar ./

# Setting Startup Command
CMD ["java", "-jar", "./intergalactic-travel-agency-1.0.jar"]