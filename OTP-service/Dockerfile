FROM openjdk:17

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} Zap-Otp-Service.jar

ENTRYPOINT ["java", "-jar","Zap-Otp-Service.jar"]
EXPOSE 8084
