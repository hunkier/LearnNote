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
    "https://registry.docker-cn.com",
    "https://dhcl9iu5.mirror.aliyuncs.com",
    "https://docker.mirrors.ustc.edu.cn",
    "http://29bd46d3.m.daocloud.io",
     "http://hub-mirror.c.163.com"
  ],
  "labels": ["name=k8s"],
  "insecure-registries": [
    "loclhost:5000",
    "192.168.33.2"
  ],
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
sudo yum install -y git vim gcc glibc-static telnet bridge-utils net-tools jq etcd
sudo systemctl start docker
sudo systemctl enable docker
sudo yum install epel-release -y
sudo yum install python-pip -y
sudo pip install --upgrade pip
sudo pip install docker-compose
sudo timedatectl set-timezone Asia/Shanghai

#sudo cat >> /etc/yum.repos.d/virtualbox.repo  <<EOF
#
#[virtualbox]
#name=Virtualbox Repository
#baseurl=https://mirrors.tuna.tsinghua.edu.cn/virtualbox/rpm/el$releasever/
#gpgcheck=0
#enabled=1
#
#EOF

#sudo yum makecache
#sudo yum install VirtualBox-5.2

#https://blog.csdn.net/hobohero/article/details/54381615
#http://download.virtualbox.org/virtualbox
#https://mirrors.tuna.tsinghua.edu.cn/virtualbox