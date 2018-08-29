#!/usr/bin/env bash
# sh start.sh
docker stop imooc-zookeeper
docker rm imooc-zookeeper
docker run --name imooc-zookeeper  -p 2181:2181 --restart always -idt zookeeper:3.5