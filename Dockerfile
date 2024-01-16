
FROM maven:3.9.3-eclipse-temurin-11-alpine as builder

# Copy local code to the container image.
WORKDIR /app
COPY pom.xml .
COPY src ./src

# Build a release artifact.
RUN mvn package -DskipTests

FROM eclipse-temurin:21-jre-alpine

# Copy the jar to the production image from the builder stage.
COPY --from=builder /app/target/payment-service-*.jar /payment-service.jar

# Run the web service on container startup.
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/productAPI.jar"]
