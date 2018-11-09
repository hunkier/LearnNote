#!/usr/bin/env bash
reg="172.20.8.5:8888/library"
image="tomcat:8-jdk-8u191-alpine"
imageLatest="tomcat:latest"
docker build -t ${image} .
docker tag ${image} ${reg}/${image}
docker tag ${image} ${reg}/${imageLatest}
docker push ${reg}/${image}
docker push ${reg}/${imageLatest}

