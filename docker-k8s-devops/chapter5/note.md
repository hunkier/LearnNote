#### docker容器文件目录挂载

```shell
docker run -d --name mysql -e MYSQL_ALLOW_EMPTY_P
ASSWORD=true mysql:5.6
Unable to find image 'mysql:5.6' locally
5.6: Pulling from library/mysql
802b00ed6f79: Already exists
30f19a05b898: Pulling fs layer
3e43303be5e9: Pulling fs layer
94b281824ae2: Pull complete
51eb397095b1: Pull complete
3f6fe5e46bae: Pull complete
b5a334ca6427: Pull complete
115764d35d7a: Pull complete
719bba2efabc: Pull complete
284e66788ee1: Pull complete
0f085ade122c: Pull complete
Digest: sha256:4c44f46efaff3ebe7cdc7b35a616c77aa003dc5de4b26c80d0ccae1f9db4a372
Status: Downloaded newer image for mysql:5.6
9f8db2b5da49cbc275ed2d1b5d523ccdcf7a28d1186aaa7ed4005603261449f4

```

```shell
docker ps
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS                NAMES
9f8db2b5da49        mysql:5.6           "docker-entrypoint.s…"   9 seconds ago       Up 8 seconds        3306/tcp             mysq
l
edb0d07744e0        my-nginx            "nginx -g 'daemon of…"   4 minutes ago       Up 4 minutes        0.0.0.0:80->80/tcp   web

docker volume ls
DRIVER              VOLUME NAME
local               56d7b33fbdc173b95219e389b866323c643011f0f49e4e90c88149313f89cb00

docker volume inspect 56d7b33fbdc173b95219e389b86
6323c643011f0f49e4e90c88149313f89cb00
[
    {
        "CreatedAt": "2018-09-09T09:03:59Z",
        "Driver": "local",
        "Labels": null,
        "Mountpoint": "/var/lib/docker/volumes/56d7b33fbdc173b95219e389b866323c643011f0f49e4e90c88149313f89cb00/_data",
        "Name": "56d7b33fbdc173b95219e389b866323c643011f0f49e4e90c88149313f89cb00",
        "Options": null,
        "Scope": "local"
    }
]

```

指定挂载文件夹名

```shell
[vagrant@docker-host ~]$ docker rm mysql -f
mysql
[vagrant@docker-host ~]$ docker volume ls
DRIVER              VOLUME NAME
local               56d7b33fbdc173b95219e389b866323c643011f0f49e4e90c88149313f89cb00
[vagrant@docker-host ~]$ docker volume rm 56d7b33fbdc173b95219e389b866323c643011f0f49e4e90c88149313f89cb00
56d7b33fbdc173b95219e389b866323c643011f0f49e4e90c88149313f89cb00
[vagrant@docker-host ~]$ docker run -d -v mysql:/var/lib/mysql --name mysql -e MYSQL_ALLOW_EMPTY_PASSWORD=true mysql:5.6

2c25ec330be5c42f25e295075b2b0b3aa6c9bd9a7917199158c7bd8cdcca44ec
[vagrant@docker-host ~]$ docker ps
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS                NAMES
2c25ec330be5        mysql:5.6           "docker-entrypoint.s…"   5 seconds ago       Up 4 seconds        3306/tcp             mysql
edb0d07744e0        my-nginx            "nginx -g 'daemon of…"   12 minutes ago      Up 12 minutes       0.0.0.0:80->80/tcp   web
[vagrant@docker-host ~]$ docker volume ls
DRIVER              VOLUME NAME
local               mysql
[vagrant@docker-host ~]$ docker volume inspect mysql
[
    {
        "CreatedAt": "2018-09-09T09:12:03Z",
        "Driver": "local",
        "Labels": null,
        "Mountpoint": "/var/lib/docker/volumes/mysql/_data",
        "Name": "mysql",
        "Options": null,
        "Scope": "local"
    }
]
[vagrant@docker-host ~]$

```

查看容器数据保存

```shell
[vagrant@docker-host ~]$ docker exec -it mysql /bin/bash
root@2c25ec330be5:/# mysql -uroot
Welcome to the MySQL monitor.  Commands end with ; or \g.
Your MySQL connection id is 1
Server version: 5.6.41 MySQL Community Server (GPL)

Copyright (c) 2000, 2018, Oracle and/or its affiliates. All rights reserved.

Oracle is a registered trademark of Oracle Corporation and/or its
affiliates. Other names may be trademarks of their respective
owners.

Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.

mysql> show databases;
+--------------------+
| Database           |
+--------------------+
| information_schema |
| mysql              |
| performance_schema |
+--------------------+
3 rows in set (0.00 sec)

mysql> create database docker ;
Query OK, 1 row affected (0.00 sec)

mysql> show databases;
+--------------------+
| Database           |
+--------------------+
| information_schema |
| docker             |
| mysql              |
| performance_schema |
+--------------------+
4 rows in set (0.00 sec)

mysql>
```

删除容器后再次运行并查看数据

```shell
[vagrant@docker-host ~]$ docker rm mysql -f
mysql
[vagrant@docker-host ~]$ docker run -d -v mysql:/var/lib/mysql --name mysql2 -e MYSQL_ALLOW_EMPTY_PASSWORD=true mysql:5.
6
d0a35378184b5db9a12db8ae71f8704cec0530e63156dfd58e07a8d70db5dbed
[vagrant@docker-host ~]$ docker exec -it mysql2 /bin/bash
root@d0a35378184b:/# mysql -uroot
Welcome to the MySQL monitor.  Commands end with ; or \g.
Your MySQL connection id is 1
Server version: 5.6.41 MySQL Community Server (GPL)

Copyright (c) 2000, 2018, Oracle and/or its affiliates. All rights reserved.

Oracle is a registered trademark of Oracle Corporation and/or its
affiliates. Other names may be trademarks of their respective
owners.

Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.

mysql> show databases;
+--------------------+
| Database           |
+--------------------+
| information_schema |
| docker             |
| mysql              |
| performance_schema |
+--------------------+
4 rows in set (0.00 sec)

mysql> Ctrl-C -- exit!
Aborted
root@d0a35378184b:/# exit
exit
[vagrant@docker-host ~]$
```

以共享的方式挂载目录

```shell
[vagrant@docker-host ~]$ docker rm mysql2 -f
mysql2
[vagrant@docker-host ~]$ docker volume ls
DRIVER              VOLUME NAME
local               mysql
[vagrant@docker-host ~]$ docker volume rm mysql
mysql
[vagrant@docker-host ~]$ docker volume ls
DRIVER              VOLUME NAME
[vagrant@docker-host ~]$ pwd
/home/vagrant
[vagrant@docker-host ~]$ ls
labs
[vagrant@docker-host ~]$ cd labs/docker-nginx/
[vagrant@docker-host docker-nginx]$ pwd
/home/vagrant/labs/docker-nginx
[vagrant@docker-host docker-nginx]$ docker build -t my-nginx .
Sending build context to Docker daemon  4.608kB
Step 1/3 : FROM nginx:latest
 ---> 06144b287844
Step 2/3 : WORKDIR /usr/share/nginx/html
 ---> Using cache
 ---> 89b978a63e73
Step 3/3 : COPY index.html index.html
 ---> 6588fb07f9cb
Successfully built 6588fb07f9cb
Successfully tagged my-nginx:latest
[vagrant@docker-host docker-nginx]$ docker ps
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS                NAMES
edb0d07744e0        65df43d4ed20        "nginx -g 'daemon of…"   23 minutes ago      Up 23 minutes       0.0.0.0:80->80/tcp   web
[vagrant@docker-host docker-nginx]$ docker rm web -f
web
[vagrant@docker-host docker-nginx]$ docker run -d -v $(pwd):/usr/share/nginx/html -p 80:80 --name web my-nginx
3850a8c1d4ae13e8e958c865b57a97d62a93e0c94f4d2f870d89719004849d6b
[vagrant@docker-host docker-nginx]$ docker volume ls
```

运行flask-skeleton

```shell
cd flask-skeleton
docker build -t flask-skeleton .
docker run -d -p 80:5000 --name flask flask-skeleton
```

修改flask-skeleton\skeleton\client\templates\main\home.html，刷新浏览器后将同步更新页面