#!/usr/bin/env bash


mvn package

docker build -t 192.168.33.2/micro-service/api-gateway-zuul:latest .

docker push 192.168.33.2/micro-service/api-gateway-zuul:latest