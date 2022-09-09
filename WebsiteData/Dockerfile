#
# Build stage
#
FROM maven:3.6.3-jdk-8-slim AS build
COPY src WebsiteData/src
COPY WebsiteData/pom.xml WebsiteData
RUN mvn -f WebsiteData/pom.xml clean package

#
# Package stage
#
FROM openjdk:17-jdk-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG war_FILE=/home/app/target/*.war
COPY ${war_FILE} app.war
ENTRYPOINT ["java","-jar","/app.war"]
