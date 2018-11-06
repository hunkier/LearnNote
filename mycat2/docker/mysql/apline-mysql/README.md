# alpine-mysql
a docker image base on alpine with mysql

# build image
```
docker build -t hunkier/alpine-mysql .
docker run -it --rm -v $(pwd):/app -p 3306:3306 wangxian/alpine-mysql
```

# Usage
```
docker run -it --name mysql -p 3306:3306 -v $(pwd):/app -e MYSQL_DATABASE=db1 -e MYSQL_USER=hunkier -e MYSQL_PASSWORD=123456 -e MYSQL_ROOT_PASSWORD=123456 hunkier/alpine-mysql
```

It will create a new db, and set mysql root password(default is 123456)