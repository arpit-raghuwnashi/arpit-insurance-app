FROM openjdk:8
ADD target/Insurance-0.0.1-SNAPSHOT.jar Insurance-0.0.1-SNAPSHOT.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","Insurance-0.0.1-SNAPSHOT.jar"]
