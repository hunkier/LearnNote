# harbor离线下载地址

http://harbor.orientsoft.cn/

# [搭建Harbor私有镜像仓库--v1.5.1](https://www.cnblogs.com/guyeshanrenshiwoshifu/p/9166195.html)

# [Harbor私有镜像仓库无坑搭建](https://blog.csdn.net/weixin_41465338/article/details/80146218) 



# CentOS7下安装Docker-Compose

1、更新yum源
 yum -y install epel-release

2、安装pip
 yum -y install python-pip

3、安装docker-compose
 pip install docker-compose

4、升级pip
 pip install --upgrade pip

5、安装wget
 yum -y install wget

6、下载harbor offline包
 wget <http://harbor.orientsoft.cn/harbor-v1.4.0/harbor-offline-installer-v1.4.0.tgz>

7、解压
 tar xvf harbor-offline-installer-v1.4.0.tgz

8、修改  配置文件
 vi harbor.cfg
 hostname =192.168.1.74

9、安装docker
 touch /etc/yum.repos.d/docker.repo
 cat <<EOF > /etc/yum.repos.d/docker.repo
 [dockerrepo]
 name=Docker Repository
 baseurl=<https://yum.dockerproject.org/repo/main/centos/7>
 enabled=1
 gpgcheck=1
 gpgkey=<https://yum.dockerproject.org/gpg>
 EOF
 systemctl stop firewalld.service
 systemctl disable firewalld.service
 yum clean all
 yum remove container-selinux-1.12.5-14.el7.centos.x86_64
 yum install -y docker-selinux
 yum -y install docker-engine
 systemctl enable docker.service
 systemctl start docker.service

10、安装harbor
 ./install.sh

11、访问
 <http://192.168.1.74/harbor/sign-in>

修改用户名：admin  密码：Admin@123

12、登录docker，修改
 vi /usr/lib/systemd/system/docker.service
 加上--insecure-registry 192.168.1.74
 ExecStart=/usr/bin/dockerd --insecure-registry 192.168.1.74
 需要加多个地址的话，ExecStart=/usr/bin/dockerd --insecure-registry 192.168.1.74 --insecure-registry 192.168.1.75

重启
 cd harbor
 docker-compose stop
 systemctl stop docker
 systemctl daemon-reload
 systemctl start docker
 docker-compose start

13、登录
 docker login 192.168.1.74
 用户名：admin  密码：Admin@123

14、测试
 下载测试images
 docker pull radial/busyboxplus:curl

更改tag
 docker tag radial/busyboxplus:curl 192.168.1.74/test/curl:curl

上传到harbor
 docker push 192.168.1.74/test/curl:curl

更新最新的
 [root@harbor harbor]# docker pull 192.168.1.74/test/curl:curl
 curl: Pulling from test/curl
 Digest: sha256:9f8382214ec25f39cf59907474d1fabb9e775f6bb627e1cc6e02d2c2bc6c8c9e
 Status: Image is up to date for 192.168.1.74/test/curl:curl

 



如果需要修改Harbor的配置文件harbor.cfg，因为Harbor是基于docker-compose服务编排的，我们可以使用docker-compose命令重启Harbor。不修改配置文件，重启Harbor命令：docker-compose start | stop | restart

注：需要在harbor安装目录所在文件夹下执行，否则会报错

> ```
>  docker-compose up -d
> ```


