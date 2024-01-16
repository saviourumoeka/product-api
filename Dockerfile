
FROM maven:3.9.6-amazoncorretto-21-al2023 as builder


# Copy local code to the container image.
WORKDIR /app
COPY pom.xml .
COPY src ./src

# Build a release artifact.
RUN mvn package -DskipTests

FROM amazoncorretto:21-alpine3.17

# Copy the jar to the production image from the builder stage.
COPY --from=builder /app/target/productAPI*.jar /productAPI.jar

# Run the web service on container startup.
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/productAPI.jar"]
