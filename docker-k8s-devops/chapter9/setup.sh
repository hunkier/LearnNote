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
  "max-concurrent-downloads": 10,
  "max-concurrent-uploads": 5,
  "insecure-registries": [
    "loclhost:5000",
    "192.168.33.2"
  ],
  "storage-driver": "overlay2",
  "storage-opts": ["overlay2.override_kernel_check=true"],
  "log-driver": "json-file",
  "log-opts": {
      "max-size": "100m",
      "max-file": "3"
      },
  "debug": true,
  "experimental": true
}

EOF

sudo cat >> /etc/sysctl.conf  <<EOF

net.bridge.bridge-nf-call-ip6tables = 1
net.bridge.bridge-nf-call-iptables = 1
net.bridge.bridge-nf-call-arptables = 1

net.ipv4.neigh.default.gc_thresh1=4096
net.ipv4.neigh.default.gc_thresh2=6144
net.ipv4.neigh.default.gc_thresh3=8192

EOF

sudo cat >> /usr/lib/sysctl.d/00-system.conf  <<EOF

net.ipv4.ip_forward=1

EOF
sudo cat >> /etc/hosts  <<EOF

192.168.33.200  rke
192.168.33.201  node1
192.168.33.202  node2
192.168.33.203  node3

EOF

sudo source /etc/hosts

sudo sed -i 's/SELINUX=enforcing/SELINUX=disabled/g' /etc/selinux/config

systemctl stop firewalld.service && systemctl disable firewalld.service

sudo ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
sudo echo 'LANG="en_US.UTF-8"' >> /etc/profile;source /etc/profile



sudo groupadd docker
sudo gpasswd -a vagrant docker
sudo yum install -y git vim gcc glibc-static telnet bridge-utils net-tools jq etcd epel-release wget
sudo systemctl start docker
sudo systemctl enable docker
sudo yum install python-pip -y
sudo pip install --upgrade pip
sudo pip install docker-compose
sudo timedatectl set-timezone Asia/Shanghai

wget -c
curl -Lo kubectl https://www.cnrancher.com/download/kubectl/kubectl_amd64-linux && sudo chmod a+x kubectl && sudo mv kubectl /usr/local/bin/

