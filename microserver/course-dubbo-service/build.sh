#!/usr/bin/env bash

mvn clean package

docker build -t hub.mooc.com:8080/micro-service/course-service:latest .
docker push hub.mooc.com:8080/micro-service/course-service:latest
