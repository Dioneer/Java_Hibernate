FROM openjdk:17 as builder
ADD /target/ServiceCreate.jar backend.jar
ENTRYPOINT ["java", "-jar", "backend.jar"]
