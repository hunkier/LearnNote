#!/usr/bin/env bash
docker stop /imooc-mysql
docker rm /imooc-mysql
docker run -idt -v data:/var/lib/mysql --name imooc-mysql -v conf:/etc/mysql/conf.d -p 3306:3306  --restart always -e MYSQL_ROOT_PASSWORD=123456 hub.c.163.com/library/mysql:5.6.36
echo `pwd`