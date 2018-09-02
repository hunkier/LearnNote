FROM 192.168.33.2/micro-service/openjdk:7-jre
MAINTAINER hunk huangkuier@gmail.com

COPY target/api-gateway-zuul-1.0-SNAPSHOT.jar /api-gateway-zuul.jar

ENTRYPOINT ["java", "-jar", "/api-gateway-zuul.jar"]
