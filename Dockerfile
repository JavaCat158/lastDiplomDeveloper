FROM ubuntu:latest
LABEL authors="Javazila"

ADD target/myprojectdiplom-0.0.1-SNAPSHOT.jar cloud-diplom.jar

ENTRYPOINT ["java", "-jar", "cloud-diplom.jar"]