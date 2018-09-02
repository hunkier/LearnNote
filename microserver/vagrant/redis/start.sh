#!/usr/bin/env bash
# sh start.sh
docker stop redis
docker rm redis
docker run -idt -p 6379:6379 -v `pwd`/data/:/data/ --name redis -v `pwd`/conf/:/etc/redis/ hub.c.163.com/public/redis:2.8.4