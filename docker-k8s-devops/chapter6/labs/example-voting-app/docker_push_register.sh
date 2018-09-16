#!/usr/bin/env bash
docker tag example-voting-app_result-app:latest 192.168.33.2/example-voting-app/example-voting-app_result-app:latest
docker tag example-voting-app_voting-app:latest 192.168.33.2/example-voting-app/example-voting-app_voting-app:latest
docker tag example-voting-app_worker:latest     192.168.33.2/example-voting-app/example-voting-app_worker:latest
docker tag postgres:9.4 192.168.33.2/library/postgres:9.4
docker tag redis:latest 192.168.33.2/library/redis:latest

docker push  192.168.33.2/example-voting-app/example-voting-app_result-app:latest
docker push  192.168.33.2/example-voting-app/example-voting-app_voting-app:latest
docker push  192.168.33.2/example-voting-app/example-voting-app_worker:latest
docker push  192.168.33.2/library/postgres:9.4
docker push  192.168.33.2/library/redis:latest