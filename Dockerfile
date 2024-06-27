FROM maven:3.9.7-eclipse-temurin-17-alpine
LABEL maintainer="Artur Neves <nevesdev.ti@gmail.com>"
WORKDIR /nevesHoteis
COPY . .
RUN mvn clean install -DskipTests && mvn package -DskipTests

EXPOSE 8080
CMD ["java", "-jar", "target/nevesHoteis-0.0.1-SNAPSHOT.jar"]
