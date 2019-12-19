# Build image
FROM maven:3-jdk-8 as build

WORKDIR /app

COPY pom.xml .
COPY src src
RUN mvn clean compile assembly:single

# Execute image
FROM openjdk:8-jre as app

WORKDIR /app

# Copy jar from build image. Copy it to a new name without version info so that we can
# pass it as a parameter when running the program without relying on wildcard expansion.
COPY --from=build /app/target/challenge-part1-1.0-SNAPSHOT-jar-with-dependencies.jar app.jar

CMD java -jar app.jar
