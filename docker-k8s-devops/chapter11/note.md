#### 安装gitlab ci runner

```shell
curl -L https://package.gitlab.com/install/repositories/runner/gitlab-ci-multi-runner/script.rpm.sh | sudo bash
sudo yum install gitlab-ci-multi-runner -y
```

查看是否运行正常

```shell
sudo gitlab-ci-multi-runner status
```

设置Docker权限

```shell
sudo usermod -aG docker gitlab-runner
sudo service docker restart
sudo gitlab-ci-multi-runner restart
```

runner注册到gitlab

```shell
sudo gitlab-ci-multi-runner register
```

查看已注册的runner

```shell
sudo gitlab-ci-multi-runner list
```

在gitlab项目根路径下建立文件`.gitlab-ci.yml`

```yaml
# 定义 stages
stages
  - build
  - test
  - deploy
# 定义 job
job1:
  statge: test
  tags:
    - demo
  script:
    - echo "I am job1"
    - echo "I am in test stage"
# 定义 job
job2:
  stage: build
  tags:
    - demo
  script:
    - echo "I am job2"
    - echo "I am in buid stage"
job3:
  stage: deploy
  tags:
    - demo
  script:
    - echo "I am job3"
    - echo "I am in deploy stage"
    
```

#### Gitlab CI NDS server

配置一个DNS服务器，能让其他容器解析到gitlab.example.com

这时候在gitlab服务器上是ping不通gitlab.example.com

#### 启动DNS服务器

找一台新的Linux host，装好docker，大家可以用vagrant或者docker-machine创建一台，我这里是用docker machine createde

然后在这台机器上，创建一个dnsmasa的容器，并运行

```shell
docker run -d -p 53:53/tcp -p 53:53/udp --cap--add=NET_ADMIN --name dns-server andyshinn/dnsmasq
```

#### 配置DNS服务器

进入容器

```shell
docker exec -it dns-server /bin/sh
```

首先配置上行的真正的dns服务器地址，毕竟你只是一个本地代理，不了解外部规则。创建文件：

```shell
vi /etc/resolv.dnsmasq
```

添加内容

```
nameserver 114.114.114.114 
nameserver 8.8.8.8
```

配置本地解析规则，这才是我们真正的目的。新建配置文件

```shell
vi /etc/dnsmasqhosts
```

添加解析规则，其中192.168.211.10是gitlab服务器地址

```shell
192.168.211.10 gitlab.example.com
```

`vi /ect/dnsmasq.conf`修改下面两处配置

```shell
resolv-file=/etc/resolv.dnsmasq
addn-hosts=/ect/dnsmasqhosts
```

重启dns服务

```shell
docker restart dns-server
```

在所有的主机上添加修改dns服务器

```shell
vi /etc/resolv.conf
```

添加内容

```shell
nameserver 192.168.99.100
```

Python项目CICD，在项目下面添加配置文件`.gitlab-ci.yml`

```yaml
stages:
  - style
  - test
  - deploy
  - release

pep8:
  stage: style
  script:
    - pip install tox
    - tox -e pep8
  tags:
    - python2.7
    
unittest-py27:
  stage: test
  script:
    - pip install tox
    - tox -e py27
  tags:
    - python2.7
  # 打tag时不运行测试
  except:
    - tag
    
unittest-py34:
  stage: test
  script:
    - pip install tox
    - tox -e py34
  tags:
    - python3.4
  # 打tag时不运行测试
  except:
    - tag
    
docker-deploy:
  stage: deploy
  script:
    - docker build -t flask-demo .
    - if [ $(docker ps -aq --filter name=web)]; then docker rm -f web; fi
    - docker run -d -p 5000:5000 --name web flask-demo
  tags:
    - demo
  only:
    - master
  # 打tag时不运行测试
  except:
    - tag
    
# 打tag发布image到registry    
docker-image-release:
  stage: release
  script:
    - docker build -t registry.example.com:5000/flask-demo:$CI_COMMIT_TAG .
    - docker push registry.example.com:5000/flask-demo:$CI_COMMIT_TAG 
  tags:
    - demo
  only:
    - tags
```



Java项目CI，在项目下面添加配置文件`.gitlab-ci.yml

```yaml
# These are the default stages. You don't need to explicitly define them.
stages:
  - build
  - test
  - deploy
# This is the name of the job. You can choose if freely.
maven_build:
  # A job is always executed within a stage. If no stage is set, it default
  stage: test
  # Since we require Mavent for this job, we can restrict the job to runner
  tags:
    - maven
  # Here you can execute arbitrate terminal commands.
  # If any of the commands returns a non zero exit code the job fails
  script:
    - echo "Building project with maven"
    - mvn verify
```



















