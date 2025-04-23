FROM ubuntu:latest
WORKDIR /app
COPY src ./src
COPY pom.xml ./
#RUN mvn clean packege -DskipTests

FROM openjdk:21-jdk-slim
COPY --from=build /app/target/auth-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-java", "/app.jar"]
