#!/usr/bin/env bash
mvn clean package -Dmaven.test.skip=true
docker build -t 192.168.33.2/micro-service/user-service:latest .
docker push 192.168.33.2/micro-service/user-service:latest