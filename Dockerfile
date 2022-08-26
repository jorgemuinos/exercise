FROM adoptopenjdk/openjdk11:latest
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} exercise.jar
ENTRYPOINT ["java","-jar","/exercise.jar"]