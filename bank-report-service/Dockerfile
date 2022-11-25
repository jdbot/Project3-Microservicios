FROM openjdk:11
VOLUME /tmp
ADD ./target/bank-report-service-0.0.1-SNAPSHOT.jar bank-report.jar
ENTRYPOINT ["java","-jar","bank-report.jar"]