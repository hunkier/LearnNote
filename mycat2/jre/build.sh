#!/usr/bin/env bash
docker build -t java-8:jre-8u191-alpine .
docker tag java-8:jre-8u191-alpine 	172.20.8.5:8888/library/java-8:jre-8u191-alpine
docker tag java-8:jre-8u191-alpine 	172.20.8.5:8888/library/java-8:latest
docker push 172.20.8.5:8888/library/java-8:jre-8u191-alpine
docker push 172.20.8.5:8888/library/java-8:latest