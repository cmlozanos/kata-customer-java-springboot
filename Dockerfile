#Set JDK
FROM openjdk:8-jdk-alpine

#Set user
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

#Copy Files
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

#Command
ENTRYPOINT ["java","-jar","/app.jar"]

#Expose ports
EXPOSE 8080