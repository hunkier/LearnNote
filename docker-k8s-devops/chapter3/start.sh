#!/usr/bin/env bash
sudo yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
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

