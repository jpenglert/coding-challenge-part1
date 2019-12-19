FROM openjdk:8-jre

WORKDIR /app

# This should be cleaned up to not include source in container.
# Would split into separate build and execute steps and copy jar
# from build to execute
#COPY pom.xml .
#COPY src .
#RUN mvn clean compile assembly:single

# Was having issues building in the container so decided to copy jar for simplicity
# Be sure to run "mvn clean compile assembly:single" beforehand
COPY target/challenge-part1-1.0-SNAPSHOT-jar-with-dependencies.jar .

CMD java -jar challenge-part1-1.0-SNAPSHOT-jar-with-dependencies.jar
