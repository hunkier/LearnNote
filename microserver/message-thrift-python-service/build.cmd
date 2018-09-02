set DOCKER_HOST=tcp://192.168.33.10:2376
docker build -t 192.168.33.2/micro-service/message-service:latest .
docker push 192.168.33.2/micro-service/message-service:latest