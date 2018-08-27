#### vagrant 添加vbx共享文件插件

```bash
vagrant plugin install vagrant-vbguest
```
安装失败尝试替换gem源
用gem sources来更改源是没用的，需要手工修改。
使用Notepad++中“文件查找”的替换功能，将 C:\HashiCorp\Vagrant\embedded\gems 下所有文件中的
https://rubygems.org
替换为：
https://ruby.taobao.org/

https://gems.ruby-china.com/

即可

修改文件 Vagrant\embedded\gems\2.1.2\gems\vagrant-2.1.2\lib\vagrant\bundler.rb
```shell
    # Location of HashiCorp gem repository
    HASHICORP_GEMSTORE = "https://gems.hashicorp.com/".freeze

    # Default gem repositories
    # https://rubygems.org
    DEFAULT_GEM_SOURCES = [
      "https://gems.ruby-china.com/".freeze
  #   "https://rubygems.org/".freeze
  #    HASHICORP_GEMSTORE
    ].freeze
```


#### 添加vagrant用户到docker组
```shell
sudo gpasswd -a vagrant docker
```



##### 编译C语言
```shell
yum install -y gcc glibc-static
gcc hello.c
```



##### 编译docker image
```shell
docker build -t hunkier/hello-world . 
```



##### 查看docker iamge内容
```shell
docker history imageId
```



##### 查看正在运行的容器（container）
```shell
docker container ls
```


##### 查看正在运行的所有容器（container）
```shell
docker container ls -a 
```


等同于`docker ps -a`

```shell
more hello/Dockerfile

docker run centos

docker container ls -a 

docker run -it centos

ls

touch test.txt

ls 

exit

docker container ls -a
```



##### 移除container

```shell
docker container rm containerId
```


等同`docker rm containerId`

##### 查看image 
```shell
docker image ls
```


等同于`docker images`

##### 移除image
```shell
docker image rm imageId
```

等同于`docker rmi imageId`

##### 列出所有container的id

```shell
docker container ls -aq
```

等同于

```shell
docker ps -aq
```

和

```shell
docker container ls -a | awk {`print$1`}
```

##### 删除所有的container

```shell
docker container rm $(docker container ls -aq)
```

等同于

```shell
docker rm $(docker ps -aq)
```



##### 列出所有不在运行的容器

```shell
docker container ls -f "status=exited"
```

##### 列出所有不在运行的容器的id

```shell
docker container ls -f "status=exited" -q
```

##### 删除所有不在运行的container

```shell
docker rm $(docker container ls -f "status=exited" -q)
```



##### 从container生成新的image

```shell
docker container commit
```

等同于

```shell
docker commit
```

##### 从Dockerfile生成新的image

```shell
docker image build
```

等同于

```shell
docker build
```

### Dockerfile

##### From

```dockerfile
FROM scratch  #制作base image
FROM centos   #使用base image
FROM ubuntu:14:04
```

尽量使用官方的image作为base image

##### LABEL

```dockerfile
LABEL maintainer="huangkier@gmail.com"		# 作者
LABEL version="1.0"							# 版本
LABEL description="This is description"		# 描述
```

metadata不可少

##### RUN

```dockerfile
RUN yum update && yum install -y vim \
	python-dev		# 反斜杠换行
```

```dockerfile
RUN apt-get update && apt-get install -y perl\
	pwgen --no-intsll-recommends && rm -rf \
	/var/lib/apt/list/*		# 注意清理cache
```

```dockerfile
RUN /bin/bash -c 'source $HOME/.bashrc;ehco $HOME'
```

每次RUN都会形成新的层。

为了美观，复杂的RUN请用反斜杠换行！

避免无用分层，合并多条命令成一行！

##### WORKDIR

设置当前工作目录

```dockerfile
WORKDIR /root
```

```dockerfile
WORKDIR /test		# 如果没有会自动创建test目录
WORKDIR demo
RUN PWD				# 输出结果应该是/test/demo
```

##### ADD and COPY

```dockerfile
ADD hello /
```

```dockerfile
ADD test.tar.gz /	# 添加到根目录并解压
```

```dockerfile
WORKDIR /root
ADD hello test/		# /root/test/hello
```

```dockerfile
WORKDIR /root
COPY hello test/
```

ADD or COPY 选择

	大部分情况，COPY优于ADD！

ADD除了COPY还有额外功能（解压）！

添加远程文件/目录请使用curl或者wget！

##### EVN

```dockerfile
EVN MYSQL-VERSION 5.6
RUN apt-get install -y mysql-server="$MYSQL_VERSION}"\
	&& rm -rf /var/lib/apt/list/*	# 引用常量
```

尽量使用EVN增加可维护性



github中dockerfile代码库：https://github.com/docker-library

dockerfile官方文档：https://docs.docker.com/engine/reference/builder/

##### RUN vs CMD vs ENTRYPOINT

```dockerfile
RUN：执行命令并创建新的Image Layer
CMD：设置容器启动后默认执行的命令和参数
ENTRYPOTIN：设置容器启动时的命令和参数
```

- Shell格式

  ```dockerfile
  RUN apt-get install -y vim
  CMD echo "hello docker"
  ENTRYPOINT echo "hello docker"
  ```

- Exec 格式

- ```dockerfile
  RUN ["apt-get", "install", "-y", "vim"]
  CMD ["/bin/echo", "hello docker"]
  ENTRYPOINT ["bin/echo", "hello docker"]
  ```

1. Dockerfile

2. ```dockerfile
   FROM centos
   ENV name Docker
   ENTRYPOINT echo "hello $name"
   ```

   Dockerfile

   ```dockerfile
   FROM centos
   ENV name Docker
   ENTRYPOINT ["/bin/echo", "hello $name" ]
   ```

   ##### dockerfile 语法高亮

   在Mac下，执行：

   curl -so ~/Library/Preferences/IntelliJIdeaXX/filetypes/Dockerfile.xml https://raw.githubusercontent.com/masgari/docker-intellij-idea/master/Dockerfile.xml

   在Linux下执行：

   wget -O ~/.IntelliJIdeaXX/config/filetypes/Dockerfile.xml https://raw.githubusercontent.com/masgari/docker-intellij-idea/master/Dockerfile.xml

   在Windows下，请下载 https://raw.githubusercontent.com/masgari/docker-intellij-idea/master/Dockerfile.xml 到用户目录下的.IntelliJIdeaXX\config\filetypes目录下。

   最后重新启动IDEA，打开或新建Dockerfile，你就会发现语法高亮和基本的代码提示都具备啦。

   ```shell
   docker build -t hunk/centos-entrypoint-shell .
   ```


##### CMD

- 容器启动时默认的命令

- 如果docker run 指定了其它命令，CMD命令被忽略

- 如果定义了多个CMD，只有最后一个会执行

  ```dockerfile
  FROM centos
  EVN name Docker
  CMD echo "hello $name"
  ```

  docker run [image]输出？			输出`hello Docker`

  Docker run it [image] /bin/bash输出？     不输出


##### ENTRYPOINT

- 让容器以应用程序或者服务的形式运行

- 不会被忽略，一定会执行

- 最佳实践：写一个shell脚本作为entrypoint

- ```dockerfile
  COPY docker-entrypoint.sh /usr/local/bin/
  ENTRYPOINT ["docker-entrypoint.sh"]
  
  EXPOSE 27017
  CMD ["mongod"]
  ```

```shell
docker image ls
docker rm $(docker ps -qa)
docker rmi imageId
```

##### image发布到[hub.docker.com](https://hub.docker.com/)

```shell
[vagrant@docker ~]$ docker login
Login with your Docker ID to push and pull images from Docker Hub. If you don't have a Docker ID, head over to https://hub.docker.com to create one.
Username: hunkier
Password:
WARNING! Your password will be stored unencrypted in /home/vagrant/.docker/config.json.
Configure a credential helper to remove this warning. See
https://docs.docker.com/engine/reference/commandline/login/#credentials-store

Login Succeeded
[vagrant@docker ~]$ docker push hunkier/hello-world:latest
The push refers to repository [docker.io/hunkier/hello-world]
419382855b85: Pushed
latest: digest: sha256:bd7e5f1ab8f217c666e6ed320aa74d8a4ee3ded370d987dfed310f1d1cf4fea6 size: 527

[vagrant@docker ~]$ docker rmi hunkier/hello-world
Untagged: hunkier/hello-world:latest
Untagged: hunkier/hello-world@sha256:bd7e5f1ab8f217c666e6ed320aa74d8a4ee3ded370d987dfed310f1d1cf4fea6
Deleted: sha256:a42c82b5c2188e6d53a42efd87f2402b16fcd8f4b345e5b53a3853420b463828
Deleted: sha256:82245913d453b277b9720a973503561c0f0a2cb734fd91adfc1a75e0689b0fca
Deleted: sha256:419382855b8509c7b01d366d4a87a2e797149daa35d3f92e5b9c5a871c2efd1b
[vagrant@docker ~]$ docker pull hunkier/hello-world
Using default tag: latest
latest: Pulling from hunkier/hello-world
08f1c59fa506: Pull complete
Digest: sha256:bd7e5f1ab8f217c666e6ed320aa74d8a4ee3ded370d987dfed310f1d1cf4fea6
Status: Downloaded newer image for hunkier/hello-world:latest

[vagrant@docker ~]$ docker images
REPOSITORY                        TAG                 IMAGE ID            CREATED             SIZE
hunkier/hello-world               latest              a42c82b5c218        8 minutes ago       861kB
```



## Run a local registry: Quick Version  [registry](https://hub.docker.com/_/registry/)

```
$ docker run -d -p 5000:5000 --restart always --name registry registry:2
```

Now, use it from within Docker:

```
$ docker pull ubuntu
$ docker tag ubuntu localhost:5000/ubuntu
$ docker push localhost:5000/ubuntu
```

```shell
docker build -t 10.75.44.222:5000/hello world
```

