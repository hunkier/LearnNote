#!/usr/bin/env bash
docker build -t java-8:jdk-8u191-alpine .
reg="hunkier"
image="java-oracle:server_jre_8_alpine"
imageLatest="java-8:latest"
docker build -t ${image} .
docker tag ${image} ${reg}/${image}
#docker tag ${image} ${reg}/${imageLatest}
docker push ${reg}/${image}
#docker push ${reg}/${imageLatest}