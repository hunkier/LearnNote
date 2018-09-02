FROM 192.168.33.2/micro-service/openjdk:7-jre
MAINTAINER hunk huangkuier@gamil.com

COPY target/user-thrift-service-1.0-SNAPSHOT.jar /user-service.jar

ENTRYPOINT ["java", "-jar", "/user-service.jar"]