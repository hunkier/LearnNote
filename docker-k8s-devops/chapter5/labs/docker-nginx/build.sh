#!/usr/bin/env bash
docker build -t my-nginx .
docker run -d -p 80:80 --name web my-nginx