#!/usr/bin/env bash
sudo yum remove docker docker-client docker-client-latest docker-common docker-latest docker-latest-logrotate docker-logrotate docker-selinux docker-engine-selinux docker-engine
sudo yum install -y yum-utils device-mapper-persistent-data lvm2
# sudo yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
curl -Lo docker-ce.repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo && sudo mv docker-ce.repo /etc/yum.repos.d/
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
  "labels": ["name=micro-service"],
  "hosts": [
        "tcp://0.0.0.0:2376",
        "unix:///var/run/docker.sock"
    ],
  "insecure-registries": ["192.168.33.2"],
  "debug": true,
  "experimental": true
}

EOF

sudo cat >> /etc/sysctl.conf  <<EOF

net.bridge.bridge-nf-call-ip6tables = 1
net.bridge.bridge-nf-call-iptables = 1
net.bridge.bridge-nf-call-arptables = 1

EOF

sudo cat >> /usr/lib/sysctl.d/00-system.conf  <<EOF

net.ipv4.ip_forward=1

EOF
sudo groupadd docker
sudo gpasswd -a vagrant docker
sudo yum install -y git vim gcc glibc-static telnet bridge-utils net-tools
sudo systemctl start docker
sudo systemctl enable docker
#docker pull openjdk:7-jre
#docker tag openjdk:7-jre 192.168.33.2/micro-service/openjdk:7-jre
#docker push 192.168.33.2/micro-service/openjdk:7-jre:latest
#docker pull python:2.7
#docker tag python:2.7 192.168.33.2/micro-service/python:2.7
#docker push 192.168.33.2/micro-service/python:2.7
docker pull mesosphere/mesos-slave:1.4.1
docker tag mesosphere/mesos-slave:1.4.1 192.168.33.2/mesosphere/mesos-slave:1.4.1
docker push 192.168.33.2/mesosphere/mesos-slave:1.4.1
docker pull mesosphere/mesos-master:1.4.1
docker tag mesosphere/mesos-master:1.4.1 192.168.33.2/mesosphere/mesos-master:1.4.1
docker push 192.168.33.2/mesosphere/mesos-master:1.4.1
docker pull mesosphere/marathon:v1.5.2
docker tag mesosphere/marathon:v1.5.2 192.168.33.2/mesosphere/marathon:v1.5.2
docker push 192.168.33.2/mesosphere/marathon:v1.5.2
docker pull mesosphere/marathon-lb:v1.11.1
docker tag mesosphere/marathon-lb:v1.11.1 192.168.33.2/mesosphere/marathon-lb:v1.11.1
docker push 192.168.33.2/mesosphere/marathon-lb:v1.11.1
docker pull hub.c.163.com/public/redis:2.8.4 && \
docker tag hub.c.163.com/public/redis:2.8.4 192.168.33.2/library/redis:2.8.4 && \
docker push 192.168.33.2/library/redis:2.8.4 && \
docker pull hub.c.163.com/library/mysql:5.6.36 && \
docker tag hub.c.163.com/library/mysql:5.6.36 192.168.33.2/library/mysql:5.6.36 && \
docker push 192.168.33.2/library/mysql:5.6.36

#sudo docker stop redis
#sudo docker rm redis
#sudo docker run -idt -p 6379:6379 --name redis -v /home/vagrant/soft/redis/conf/redis.conf:/usr/local/etc/redis/redis.conf redis

sudo timedatectl set-timezone Asia/Shanghai

echo `pwd`