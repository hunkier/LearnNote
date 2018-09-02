#!/usr/bin/env bash
docker build -t 192.168.33.2/micro-service/message-service:latest .
docker push 192.168.33.2/micro-service/message-service:latest