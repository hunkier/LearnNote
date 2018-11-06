FROM alpine:3.8
MAINTAINER hunkier <huangkuier@gmail.com>

WORKDIR /app
VOLUME /app
COPY startup.sh /startup.sh

RUN  echo "https://mirrors.aliyun.com/alpine/v3.6/main/" > /etc/apk/repositories \
     && echo "https://mirrors.aliyun.com/alpine/v3.6/community/" >> /etc/apk/repositories \
     &&   apk add --update mysql mysql-client && rm -f /var/cache/apk/*
COPY my.cnf /etc/mysql/my.cnf

EXPOSE 3306
CMD ["/startup.sh"]
