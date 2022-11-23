FROM openjdk:11
VOLUME /tmp
ADD ./target/card-service-1.0-SNAPSHOT.jar card.jar
ENTRYPOINT ["java","-jar","card.jar"]