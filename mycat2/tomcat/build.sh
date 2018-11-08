#!/usr/bin/env bash
docker build -t tomcat:8-jre-8u191-alpine .
docker tag tomcat:8-jre-8u191-alpine 	172.20.8.5:8888/library/tomcat:8-jre-8u191-alpine
docker tag tomcat:8-jre-8u191-alpine 	172.20.8.5:8888/library/tomcat:latest
docker push 172.20.8.5:8888/library/tomcat:8-jre-8u191-alpine
docker push 172.20.8.5:8888/library/tomcat:latest