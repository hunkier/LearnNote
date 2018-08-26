#### vagrant 添加vbx共享文件插件

```bash
vagrant plugin install vagrant-vbguest
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



#### 移除container

```shell
docker container rm containerId
```


等同`docker rm containerId`

#### 查看image 
```shell
docker image ls
```


等同于`docker images`

#### 移除image
```shell
docker image rm imageId
```

等同于`docker rmi imageId`

#### 列出所有container的id

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

#### 删除所有的container

```shell
docker container rm $(docker container ls -aq)
```

等同于

```shell
docker rm $(docker ps -aq)
```



#### 列出所有不在运行的容器

```shell
docker container ls -f "status=exited"
```

#### 列出所有不在运行的容器的id

```shell
docker container ls -f "status=exited" -q
```

#### 删除所有不在运行的container

```shell
docker rm $(docker container ls -f "status=exited" -q)
```



#### 从container生成新的image

```shell
docker container commit
```

等同于

```shell
docker commit
```

#### 从Dockerfile生成新的image

```shell
docker image build
```

等同于

```shell
docker build
```

# Dockerfile

## From

```dockerfile
FROM scratch  #制作base image
FROM centos   #使用base image
FROM ubuntu:14:04
```

尽量使用官方的image作为base image

## LABEL

```dockerfile
LABEL maintainer="huangkier@gmail.com"		# 作者
LABEL version="1.0"							# 版本
LABEL description="This is description"		# 描述
```

metadata不可少

# RUN

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

