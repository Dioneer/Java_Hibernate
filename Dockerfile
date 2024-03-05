FROM openjdk:latest as builder
ADD /target/Java_Hibernate-1.0-SNAPSHOT.jar backend.jar
ENTRYPOINT ["java", "-jar", "backend.jar"]

