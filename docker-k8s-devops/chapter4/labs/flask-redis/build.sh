#!/usr/bin/env bash
docker build -t flask-redis .
docker run -d --name redis redis
docker run -d --link redis --name flask-redis -e REDIS_HOST=redis flask-redis