FROM openjdk:17-slim
COPY ./build/libs/*T.jar app.jar
ENV profiles=depl
ENV serverPort=8080
ENV dbServer=toto-mysql:3306
ENV dbUsername=root
ENV dbPassword=1234
ENV kafkaServer=kafka:9902
ENV payServer=localhost:8080
ENV frontServer=localhost:5173
ENV secretKey=SecretKeyHereSecretKeyHereSecretKeyHereSecretKeyHere
CMD ["java", "-jar", "-Dspring.profiles.active=${profiles}", "app.jar"]
EXPOSE ${serverPort}