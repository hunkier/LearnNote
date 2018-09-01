#!/usr/bin/env bash
docker stop /imooc-mysql
docker rm /imooc-mysql
docker run --name imooc-mysql -v data:/var/lib/mysql -v conf:/etc/mysql/conf.d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=123456 -idt hub.c.163.com/library/mysql:5.6.36