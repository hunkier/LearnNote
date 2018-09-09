set docker_host=tcp://192.168.33.105:2376
docker build -t my-nginx .
docker run -d -p 80:80 --name web my-nginx