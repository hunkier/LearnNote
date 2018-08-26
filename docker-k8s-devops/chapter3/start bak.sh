#!/usr/bin/env bash
sudo yum remove docker docker-client docker-client-latest docker-common docker-latest docker-latest-logrotate docker-logrotate docker-selinux docker-engine-selinux docker-engine
sudo yum install -y yum-utils device-mapper-persistent-data lvm2

sudo yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
sudo yum install  -y  docker-ce
sudo mkdir -p /etc/docker
sudo tee /etc/docker/daemon.json <<-'EOF'
{
  "registry-mirrors": [
    "https://dhcl9iu5.mirror.aliyuncs.com",
    "https://docker.mirrors.ustc.edu.cn",
    "http://29bd46d3.m.daocloud.io",
     "http://hub-mirror.c.163.com"
  ],
  "insecure-registries": [],
  "debug": true,
  "experimental": true
}

EOF
sudo gpasswd -a vagrant docker
#sudo systemctl start docker
#sudo docker stop redis
#sudo docker rm redis
#sudo docker run -idt -p 6379:6379 --name redis -v /vagrant/redis/conf/redis.conf:/usr/local/etc/redis/redis.conf redis
#sudo docker stop elasticsearch
#sudo docker rm elasticsearch
#sudo docker run -idt -p 9200:9200 --name elasticsearch -v /vagrant/elasticsearch/data/:/usr/share/elasticsearch/data -e "http.host=0.0.0.0" -e "transport.host=127.0.0.1" -e xpack.security.enabled=false elasticsearch
#sudo docker stop mysql
#sudo docker rm mysql
#sudo docker run -idt -p 3306:3306 --name mysql  -v /vagrant/mysql/datadir:/var/lib/mysql -v /vagrant/mysql/conf.d:/etc/mysql/conf.d  -e MYSQL_ROOT_PASSWORD=123456 -d mysql:5.6
#sudo yum install -y git vim gcc glibc-static telnet bridge-utils net-tools