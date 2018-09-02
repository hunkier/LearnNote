FROM 192.168.33.2/micro-service/openjdk:7-jre
MAINTAINER hunk huangkuier@gamil.com

COPY target/course-edge-service-1.0-SNAPSHOT.jar /course-edge-service.jar

ENTRYPOINT ["java", "-jar", "/course-edge-service.jar"]