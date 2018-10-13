#!/usr/bin/env bash
docker run -d --restart=unless-stopped \
-e CATTLE_SYSTEM_DEFAULT_REGISTRY=192.168.33.2 \
-p 80:80 -p 443:443 \
192.168.33.2/rancher/rancher:v2.1.0 \

