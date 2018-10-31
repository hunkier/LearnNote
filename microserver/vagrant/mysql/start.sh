#!/usr/bin/env bash
docker stop /imooc-mysql
docker rm /imooc-mysql
docker run -idt -v data:/var/lib/mysql --name imooc-mysql -v conf:/etc/mysql/conf.d -p 3306:3306  --restart always -e MYSQL_ROOT_PASSWORD=123456 hub.c.163.com/library/mysql:5.6.36
echo `pwd`

# linux时区
tzselect
TZ='Asia/Shanghai'; export TZ
ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

# 设置时间
date -s "2018-10-10 10:10:10"

# 修改tomcat-7.0.42\bin目录下的catalina.bat文件，在第一行后面追加一行如下：
set JAVA_OPTS=%JAVA_OPTS% -Duser.timezone=GMT+08

# 运行java程序时添加时区参数
java -Duser.timezone=GMT+08 xxx.jar