set docker_host=tcp://192.168.205.10:2376
docker build -t flask-redis .
docker run -d --name redis redis
docker run -d --link redis --name flask-redis -e REDIS_HOST=redis flask-redis