# syntax=docker/dockerfile:1

FROM eclipse-temurin:17-jdk-jammy as base

WORKDIR /app

COPY WebsiteData/.mvn/ .mvn
COPY WebsiteData/mvnw pom.xml ./
RUN WebsiteData/./mvnw dependency:resolve
COPY WebsiteData/src ./src

FROM base as test
RUN ["WebsiteData/./mvnw", "test"]

FROM base as development
CMD ["WebsiteData/./mvnw", "spring-boot:run", "-Dspring-boot.run.profiles=mysql", "-Dspring-boot.run.jvmArguments='-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8000'"]

FROM base as build
RUN WebsiteData/./mvnw package

FROM eclipse-temurin:17-jre-jammy as production
EXPOSE 8080
COPY --from=build /app/target/healthcare-0.0.1-SNAPSHOT.jar WebsiteData/healthcare-0.0.1-SNAPSHOT.jar
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "WebsiteData/healthcare-0.0.1-SNAPSHOT.jar"]
