### docker-compose

安装WordPress博客

```shell
[vagrant@docker ~]$ docker run -d --name mysql -v mysql-data:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=wordpress mysql:5.6
568b7512fd75f28d97b38028827fffee88171a9852496fcf1583c04ce6fb2255
[vagrant@docker ~]$
[vagrant@docker ~]$ docker ps
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS               NAMES
568b7512fd75        mysql:5.6           "docker-entrypoint.s…"   29 seconds ago      Up 28 seconds       3306/tcp            mysql
[vagrant@docker ~]$ docker run -d -e WORDPRESS_DB_HOST=mysql:3306 --link mysql -p 8080:80 wordpress
a26bfb0e2c9ed5adc452714286489f21d7daa37979210e27b6c88b56a74d3c33
[vagrant@docker ~]$ docker ps
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS                  NAMES
a26bfb0e2c9e        wordpress           "docker-entrypoint.s…"   4 seconds ago       Up 3 seconds        0.0.0.0:8080->80/tcp   serene_agnesi
568b7512fd75        mysql:5.6           "docker-entrypoint.s…"   2 minutes ago       Up 2 minutes        3306/tcp               mysql
[vagrant@docker ~]$
```

浏览器访问http://ip:8080

docker-compose扩展容器数量

```shell
docker-compose up -d
docker-compose up  --scale web=5 -d
```

运行curl访问10次

```shell
for i in `seq 10`; do curl 127.0.0.1:8080; done
```

docker-compose编译image

```shell
docker-compose build
```

```shell
[vagrant@docker lb-scale]$ docker-compose build
Building web
Step 1/7 : FROM python:2.7
 ---> 4ee4ea2f0113
Step 2/7 : LABEL maintaner="hunk <huangkuier@gmail.com>"
 ---> Using cache
 ---> ac36f5b644e5
Step 3/7 : COPY . /app
 ---> Using cache
 ---> 589280e870cc
Step 4/7 : WORKDIR /app
 ---> Using cache
 ---> 80ff00ce963e
Step 5/7 : RUN pip install flask redis
 ---> Using cache
 ---> fd36ac03c0b0
Step 6/7 : EXPOSE 80
 ---> Using cache
 ---> f4e4cc9d82e0
Step 7/7 : CMD [ "python", "app.py" ]
 ---> Using cache
 ---> 367ddb56e265
Successfully built 367ddb56e265
Successfully tagged lb-scale_web:latest
redis uses an image, skipping
lb uses an image, skipping
[vagrant@docker lb-scale]$ docker-compose up -d
Creating network "lb-scale_default" with the default driver
Creating lb-scale_web_1   ... done
Creating lb-scale_redis_1 ... done
Creating lb-scale_lb_1    ... done
[vagrant@docker lb-scale]$ docker-compose ps
      Name                    Command               State                    Ports
---------------------------------------------------------------------------------------------------
lb-scale_lb_1      /sbin/tini -- dockercloud- ...   Up      1936/tcp, 443/tcp, 0.0.0.0:8080->80/tcp
lb-scale_redis_1   docker-entrypoint.sh redis ...   Up      6379/tcp
lb-scale_web_1     python app.py                    Up      80/tcp
[vagrant@docker lb-scale]$ curl localhost:8080
Hello Container World! I have been seen 1 times and my hostname is b6b8be7e1e38.
[vagrant@docker lb-scale]$ curl localhost:8080
Hello Container World! I have been seen 2 times and my hostname is b6b8be7e1e38.
[vagrant@docker lb-scale]$ curl localhost:8080
Hello Container World! I have been seen 3 times and my hostname is b6b8be7e1e38.
[vagrant@docker lb-scale]$ curl localhost:8080
Hello Container World! I have been seen 4 times and my hostname is b6b8be7e1e38.
[vagrant@docker lb-scale]$ docker-compose up --scale web=5 -d
Starting lb-scale_web_1 ... done
lb-scale_redis_1 is up-to-date
Creating lb-scale_web_2 ... done
Creating lb-scale_web_3 ... done
Creating lb-scale_web_4 ... done
Creating lb-scale_web_5 ... done
lb-scale_lb_1 is up-to-date
[vagrant@docker lb-scale]$ docker-compose ps
      Name                    Command               State                    Ports
---------------------------------------------------------------------------------------------------
lb-scale_lb_1      /sbin/tini -- dockercloud- ...   Up      1936/tcp, 443/tcp, 0.0.0.0:8080->80/tcp
lb-scale_redis_1   docker-entrypoint.sh redis ...   Up      6379/tcp
lb-scale_web_1     python app.py                    Up      80/tcp
lb-scale_web_2     python app.py                    Up      80/tcp
lb-scale_web_3     python app.py                    Up      80/tcp
lb-scale_web_4     python app.py                    Up      80/tcp
lb-scale_web_5     python app.py                    Up      80/tcp
[vagrant@docker lb-scale]$ for i in `seq 10`; do curl localhost:8080; done
Hello Container World! I have been seen 5 times and my hostname is b6b8be7e1e38.
Hello Container World! I have been seen 6 times and my hostname is 6f4b38aa9f59.
Hello Container World! I have been seen 7 times and my hostname is 88fcf6ef54b8.
Hello Container World! I have been seen 8 times and my hostname is 9ca7c136e433.
Hello Container World! I have been seen 9 times and my hostname is c85c62e42365.
Hello Container World! I have been seen 10 times and my hostname is b6b8be7e1e38.
Hello Container World! I have been seen 11 times and my hostname is 6f4b38aa9f59.
Hello Container World! I have been seen 12 times and my hostname is 88fcf6ef54b8.
Hello Container World! I have been seen 13 times and my hostname is 9ca7c136e433.
Hello Container World! I have been seen 14 times and my hostname is c85c62e42365.
[vagrant@docker lb-scale]$

[vagrant@docker lb-scale]$ docker-compose up --scale web=1 -d
Stopping and removing lb-scale_web_2 ...
Stopping and removing lb-scale_web_2 ... done
Stopping and removing lb-scale_web_3 ... done
Stopping and removing lb-scale_web_4 ... done
Stopping and removing lb-scale_web_5 ... done
Starting lb-scale_web_1              ... done
lb-scale_lb_1 is up-to-date
[vagrant@docker lb-scale]$ docker-compose up --scale web=10 -d
Starting lb-scale_web_1 ... done
lb-scale_redis_1 is up-to-date
Creating lb-scale_web_2  ... done
Creating lb-scale_web_3  ... done
Creating lb-scale_web_4  ... done
Creating lb-scale_web_5  ... done
Creating lb-scale_web_6  ... done
Creating lb-scale_web_7  ... done
Creating lb-scale_web_8  ... done
Creating lb-scale_web_9  ... done
Creating lb-scale_web_10 ... done
lb-scale_lb_1 is up-to-date
[vagrant@docker lb-scale]$ docker-compose ps
      Name                    Command               State                    Ports
---------------------------------------------------------------------------------------------------
lb-scale_lb_1      /sbin/tini -- dockercloud- ...   Up      1936/tcp, 443/tcp, 0.0.0.0:8080->80/tcp
lb-scale_redis_1   docker-entrypoint.sh redis ...   Up      6379/tcp
lb-scale_web_1     python app.py                    Up      80/tcp
lb-scale_web_10    python app.py                    Up      80/tcp
lb-scale_web_2     python app.py                    Up      80/tcp
lb-scale_web_3     python app.py                    Up      80/tcp
lb-scale_web_4     python app.py                    Up      80/tcp
lb-scale_web_5     python app.py                    Up      80/tcp
lb-scale_web_6     python app.py                    Up      80/tcp
lb-scale_web_7     python app.py                    Up      80/tcp
lb-scale_web_8     python app.py                    Up      80/tcp
lb-scale_web_9     python app.py                    Up      80/tcp
[vagrant@docker lb-scale]$ for i in `seq 10`; do curl localhost:8080; done
Hello Container World! I have been seen 15 times and my hostname is b6b8be7e1e38.
Hello Container World! I have been seen 16 times and my hostname is de75c3b85dd2.
Hello Container World! I have been seen 17 times and my hostname is a3c82cd3002f.
Hello Container World! I have been seen 18 times and my hostname is a7547aee91e1.
Hello Container World! I have been seen 19 times and my hostname is 549ff82b4b75.
Hello Container World! I have been seen 20 times and my hostname is 3be30ee18748.
Hello Container World! I have been seen 21 times and my hostname is 569fe324c6e7.
Hello Container World! I have been seen 22 times and my hostname is 01722c90fff6.
Hello Container World! I have been seen 23 times and my hostname is e04cf525ba03.
Hello Container World! I have been seen 24 times and my hostname is e26800fc2b72.
[vagrant@docker lb-scale]$ for i in `seq 30`; do curl localhost:8080; done
Hello Container World! I have been seen 25 times and my hostname is b6b8be7e1e38.
Hello Container World! I have been seen 26 times and my hostname is de75c3b85dd2.
Hello Container World! I have been seen 27 times and my hostname is a3c82cd3002f.
Hello Container World! I have been seen 28 times and my hostname is a7547aee91e1.
Hello Container World! I have been seen 29 times and my hostname is 549ff82b4b75.
Hello Container World! I have been seen 30 times and my hostname is 3be30ee18748.
Hello Container World! I have been seen 31 times and my hostname is 569fe324c6e7.
Hello Container World! I have been seen 32 times and my hostname is 01722c90fff6.
Hello Container World! I have been seen 33 times and my hostname is e04cf525ba03.
Hello Container World! I have been seen 34 times and my hostname is e26800fc2b72.
Hello Container World! I have been seen 35 times and my hostname is b6b8be7e1e38.
Hello Container World! I have been seen 36 times and my hostname is de75c3b85dd2.
Hello Container World! I have been seen 37 times and my hostname is a3c82cd3002f.
Hello Container World! I have been seen 38 times and my hostname is a7547aee91e1.
Hello Container World! I have been seen 39 times and my hostname is 549ff82b4b75.
Hello Container World! I have been seen 40 times and my hostname is 3be30ee18748.
Hello Container World! I have been seen 41 times and my hostname is 569fe324c6e7.
Hello Container World! I have been seen 42 times and my hostname is 01722c90fff6.
Hello Container World! I have been seen 43 times and my hostname is e04cf525ba03.
Hello Container World! I have been seen 44 times and my hostname is e26800fc2b72.
Hello Container World! I have been seen 45 times and my hostname is b6b8be7e1e38.
Hello Container World! I have been seen 46 times and my hostname is de75c3b85dd2.
Hello Container World! I have been seen 47 times and my hostname is a3c82cd3002f.
Hello Container World! I have been seen 48 times and my hostname is a7547aee91e1.
Hello Container World! I have been seen 49 times and my hostname is 549ff82b4b75.
Hello Container World! I have been seen 50 times and my hostname is 3be30ee18748.
Hello Container World! I have been seen 51 times and my hostname is 569fe324c6e7.
Hello Container World! I have been seen 52 times and my hostname is 01722c90fff6.
Hello Container World! I have been seen 53 times and my hostname is e04cf525ba03.
Hello Container World! I have been seen 54 times and my hostname is e26800fc2b72.
[vagrant@docker lb-scale]$

```

