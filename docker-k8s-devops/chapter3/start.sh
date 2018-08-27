#!/usr/bin/env bash
sudo yum remove docker docker-client docker-client-latest docker-common docker-latest docker-latest-logrotate docker-logrotate docker-selinux docker-engine-selinux docker-engine
sudo yum install -y yum-utils device-mapper-persistent-data lvm2
#sudo yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
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
  "insecure-registries": [],
  "debug": true,
  "experimental": true
}

EOF
sudo gpasswd -a vagrant docker
sudo yum install -y git vim gcc glibc-static telnet bridge-utils net-tools
sudo systemctl start docker
