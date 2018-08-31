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
  "labels": ["name=docker-server"],
  "hosts": [
        "tcp://0.0.0.0:2376",
        "unix:///var/run/docker.sock"
    ],
  "insecure-registries": [],
  "debug": true,
  "experimental": true
}

EOF
sudo groupadd docker
sudo gpasswd -a vagrant docker
sudo yum install -y git vim gcc glibc-static telnet bridge-utils net-tools python2  wget
sudo systemctl start docker
#curl -Lo harbor-offline-installer.tgz https://storage.googleapis.com/harbor-releases/harbor-offline-installer-v1.5.2.tgz && tar xvf harbor-offline-installer.tgz
sudo yum install epel-release -y
sudo yum install python-pip -y
sudo pip install --upgrade pip
sudo pip install docker-compose
curl -Lo harbor-offline-installer.tgz http://harbor.orientsoft.cn/harbor-v1.5.0/harbor-offline-installer-v1.5.0.tgz && tar xvf harbor-offline-installer.tgz
cd soft/harbor
sudo ./install.sh
echo `pwd`