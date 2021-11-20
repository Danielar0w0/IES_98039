FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/*.jar MovieQuotes-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/MovieQuotes-0.0.1-SNAPSHOT.jar"]
