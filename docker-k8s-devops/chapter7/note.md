# docker swarm

### 主节点manager

```shell
$ vagrant status
Current machine states:

swarm-manager             running (virtualbox)
swarm-worker1             running (virtualbox)
swarm-worker2             running (virtualbox)

This environment represents multiple VMs. The VMs are all listed
above with their current state. For more information about a specific
VM, run `vagrant status NAME`.

$ vagrant ssh swarm-manager
[vagrant@swarm-manager ~]$ ip a
1: lo: <LOOPBACK,UP,LOWER_UP> mtu 65536 qdisc noqueue state UNKNOWN group default qlen 1000
    link/loopback 00:00:00:00:00:00 brd 00:00:00:00:00:00
    inet 127.0.0.1/8 scope host lo
       valid_lft forever preferred_lft forever
    inet6 ::1/128 scope host
       valid_lft forever preferred_lft forever
2: eth0: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc pfifo_fast state UP group default qlen 1000
    link/ether 52:54:00:c9:c7:04 brd ff:ff:ff:ff:ff:ff
    inet 10.0.2.15/24 brd 10.0.2.255 scope global noprefixroute dynamic eth0
       valid_lft 85825sec preferred_lft 85825sec
    inet6 fe80::5054:ff:fec9:c704/64 scope link
       valid_lft forever preferred_lft forever
3: eth1: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc pfifo_fast state UP group default qlen 1000
    link/ether 08:00:27:e6:f1:bd brd ff:ff:ff:ff:ff:ff
    inet 192.168.205.10/24 brd 192.168.205.255 scope global noprefixroute eth1
       valid_lft forever preferred_lft forever
    inet6 fe80::a00:27ff:fee6:f1bd/64 scope link
       valid_lft forever preferred_lft forever
4: docker0: <NO-CARRIER,BROADCAST,MULTICAST,UP> mtu 1500 qdisc noqueue state DOWN group default
    link/ether 02:42:2b:98:f2:42 brd ff:ff:ff:ff:ff:ff
    inet 172.17.0.1/16 brd 172.17.255.255 scope global docker0
       valid_lft forever preferred_lft forever
[vagrant@swarm-manager ~]$ docker swarm init --advertise-addr=192.168.205.10
Swarm initialized: current node (f0xnu79wb3u7qyffvcxr71oxa) is now a manager.

To add a worker to this swarm, run the following command:

    docker swarm join --token SWMTKN-1-5e3x287y2x0mzlr4rfnvvqzbu8tvt72nny293mdt70be0vkfxq-8549bsispxxg39nczjario7x6 192.168.205.10:2377

To add a manager to this swarm, run 'docker swarm join-token manager' and follow the instructions.

[vagrant@swarm-manager ~]$
```

### worker1节点

```shell
[vagrant@swarm-worker1 ~]$ docker swarm join --token SWMTKN-1-5e3x287y2x0mzlr4rfnvvqzbu8tvt72nny293mdt70be0vkfxq-8549bsispxxg39nczjario7x6 192.168.205.10:2377
This node joined a swarm as a worker.
[vagrant@swarm-worker1 ~]$
```

### 查看所有节点

```shell
[vagrant@swarm-manager ~]$ docker node ls
ID                            HOSTNAME            STATUS              AVAILABILITY        MANAGER STATUS      ENGINE VERSION
f0xnu79wb3u7qyffvcxr71oxa *   swarm-manager       Ready               Active              Leader              18.06.1-ce
z3jpqindc3rga1ucig077a3q2     swarm-worker1       Ready               Active                                  18.06.1-ce
[vagrant@swarm-manager ~]$
```

启动工作节点2：worker2

```shell
[vagrant@swarm-worker2 ~]$ docker swarm join --token SWMTKN-1-5e3x287y2x0mzlr4rfnvvqzbu8tvt72nny293mdt70be0vkfxq-8549bsispxxg39nczjario7x6 192.168.205.10:2377
This node joined a swarm as a worker.
[vagrant@swarm-worker2 ~]$
```

再次查看所有节点

```shell
[vagrant@swarm-manager ~]$ docker node ls
ID                            HOSTNAME            STATUS              AVAILABILITY        MANAGER STATUS      ENGINE VERSION
f0xnu79wb3u7qyffvcxr71oxa *   swarm-manager       Ready               Active              Leader              18.06.1-ce
z3jpqindc3rga1ucig077a3q2     swarm-worker1       Ready               Active                                  18.06.1-ce
9rxzl9kzxoor1kbopcp5abuvg     swarm-worker2       Ready               Active                                  18.06.1-ce
[vagrant@swarm-manager ~]$
```

### 通过swarm创建容器

```shell
[vagrant@swarm-manager ~]$ docker service create --name demo busybox sh -c "while true; do sleep 3600;done"
w4r8eqauf7xeaw4d3ebzvy7aa
overall progress: 1 out of 1 tasks
1/1: running   [==================================================>]
verify: Service converged
[vagrant@swarm-manager ~]$ docker service ls
ID                  NAME                MODE                REPLICAS            IMAGE               PORTS
w4r8eqauf7xe        demo                replicated          1/1                 busybox:latest
[vagrant@swarm-manager ~]$ docker service ps demo
ID                  NAME                IMAGE               NODE                DESIRED STATE       CURRENT STATE            ERROR               PORTS
1zp5j11c1l3s        demo.1              busybox:latest      swarm-worker2       Running             Running 44 seconds ago
[vagrant@swarm-manager ~]$ docker ps
CONTAINER ID        IMAGE               COMMAND             CREATED             STATUS              PORTS               NAMES
[vagrant@swarm-manager ~]$
```

### 三个节点看看是否有容器在运行

```shell
[vagrant@swarm-manager ~]$ docker ps
CONTAINER ID        IMAGE               COMMAND             CREATED             STATUS              PORTS               NAMES
[vagrant@swarm-manager ~]$

[vagrant@swarm-worker1 ~]$ docker ps
CONTAINER ID        IMAGE               COMMAND             CREATED             STATUS              PORTS               NAMES
[vagrant@swarm-worker1 ~]$

[vagrant@swarm-worker2 ~]$ docker ps
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS               NAMES
0d6776bb5807        busybox:latest      "sh -c 'while true; …"   4 minutes ago       Up 4 minutes                            demo.1.1zp5j11c1l3s894byezrbvk3h
[vagrant@swarm-worker2 ~]$
```

### 重启系统后docker service会自动运行

```shell
[vagrant@swarm-manager ~]$ docker service ls
ID                  NAME                MODE                REPLICAS            IMAGE               PORTS
w4r8eqauf7xe        demo                replicated          1/1                 busybox:latest
[vagrant@swarm-manager ~]$ docker service ps demo
ID                  NAME                IMAGE               NODE                DESIRED STATE       CURRENT STATE            ERROR                         PORTS
ikktimjbvoug        demo.1              busybox:latest      swarm-manager       Running             Running 14 minutes ago
1zp5j11c1l3s         \_ demo.1          busybox:latest      swarm-worker2       Shutdown            Failed 14 minutes ago    "task: non-zero exit (255)"
[vagrant@swarm-manager ~]$
```

### 扩展容器

```shell
[vagrant@swarm-manager ~]$ docker service scale demo=5
demo scaled to 5
overall progress: 5 out of 5 tasks
1/5: running   [==================================================>]
2/5: running   [==================================================>]
3/5: running   [==================================================>]
4/5: running   [==================================================>]
5/5: running   [==================================================>]
verify: Service converged
[vagrant@swarm-manager ~]$ docker service ls
ID                  NAME                MODE                REPLICAS            IMAGE               PORTS
w4r8eqauf7xe        demo                replicated          5/5                 busybox:latest
[vagrant@swarm-manager ~]$ docker service ps demo
ID                  NAME                IMAGE               NODE                DESIRED STATE       CURRENT STATE            ERROR                         PORTS
ikktimjbvoug        demo.1              busybox:latest      swarm-manager       Running             Running 18 minutes ago
1zp5j11c1l3s         \_ demo.1          busybox:latest      swarm-worker2       Shutdown            Failed 17 minutes ago    "task: non-zero exit (255)"
pl37gbm0mfdf        demo.2              busybox:latest      swarm-worker1       Running             Running 29 seconds ago
z9vhjj4h45np        demo.3              busybox:latest      swarm-worker1       Running             Running 29 seconds ago
pkny6cps4xiy        demo.4              busybox:latest      swarm-manager       Running             Running 33 seconds ago
t73ji5comfd7        demo.5              busybox:latest      swarm-worker2       Running             Running 33 seconds ago
[vagrant@swarm-manager ~]$
```

### 工作节点上强制删除容器，swarm自动重新创建容器

```shell
[vagrant@swarm-worker1 ~]$ docker ps
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS               NAMES
a5d5738f1f8c        busybox:latest      "sh -c 'while true; …"   3 minutes ago       Up 3 minutes                            demo.2.pl37gbm0mfdfawxxawnsq4mgj
f3a9de0a75af        busybox:latest      "sh -c 'while true; …"   3 minutes ago       Up 3 minutes                            demo.3.z9vhjj4h45npv4nqdt0ebsrpp
[vagrant@swarm-worker1 ~]$ docker rm a5d5738f1f8c -f
a5d5738f1f8c
[vagrant@swarm-worker1 ~]$ docker rm  f3a9de0a75af -f
f3a9de0a75af
[vagrant@swarm-worker1 ~]$
[vagrant@swarm-manager ~]$ docker service ls
ID                  NAME                MODE                REPLICAS            IMAGE               PORTS
w4r8eqauf7xe        demo                replicated          4/5                 busybox:latest
[vagrant@swarm-manager ~]$ docker service ls
ID                  NAME                MODE                REPLICAS            IMAGE               PORTS
w4r8eqauf7xe        demo                replicated          4/5                 busybox:latest
[vagrant@swarm-manager ~]$ docker service ls
ID                  NAME                MODE                REPLICAS            IMAGE               PORTS
w4r8eqauf7xe        demo                replicated          5/5                 busybox:latest
[vagrant@swarm-manager ~]$ docker service ps demo
ID                  NAME                IMAGE               NODE                DESIRED STATE       CURRENT STATE               ERROR                         PORTS
ikktimjbvoug        demo.1              busybox:latest      swarm-manager       Running             Running 23 minutes ago
1zp5j11c1l3s         \_ demo.1          busybox:latest      swarm-worker2       Shutdown            Failed 22 minutes ago       "task: non-zero exit (255)"
mylm3ihazhuw        demo.2              busybox:latest      swarm-worker1       Running             Running 56 seconds ago
pl37gbm0mfdf         \_ demo.2          busybox:latest      swarm-worker1       Shutdown            Failed about a minute ago   "task: non-zero exit (137)"
gksbc1pfl1l4        demo.3              busybox:latest      swarm-worker2       Running             Running 19 seconds ago
z9vhjj4h45np         \_ demo.3          busybox:latest      swarm-worker1       Shutdown            Failed 25 seconds ago       "task: non-zero exit (137)"
pkny6cps4xiy        demo.4              busybox:latest      swarm-manager       Running             Running 5 minutes ago
t73ji5comfd7        demo.5              busybox:latest      swarm-worker2       Running             Running 5 minutes ago
[vagrant@swarm-manager ~]$
```

swarm删除服务

```shell
[vagrant@swarm-manager ~]$ docker service rm demo
demo
[vagrant@swarm-manager ~]$ docker service ls
ID                  NAME                MODE                REPLICAS            IMAGE               PORTS
[vagrant@swarm-manager ~]
[vagrant@swarm-manager ~]$ docker service ps demo
no such service: demo
[vagrant@swarm-manager ~]$
```

工作节点上也将停止容器

```shell
[vagrant@swarm-worker1 ~]$ docker ps
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS               NAMES
23027b69f1af        busybox:latest      "sh -c 'while true; …"   2 minutes ago       Up 2 minutes                            demo.2.mylm3ihazhuw39o3xe4dl2z2x
[vagrant@swarm-worker1 ~]$ docker ps
CONTAINER ID        IMAGE               COMMAND             CREATED             STATUS              PORTS               NAMES
[vagrant@swarm-worker1 ~]$

[vagrant@swarm-worker2 ~]$ docker ps
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS               NAMES
63522f5c7cc0        busybox:latest      "sh -c 'while true; …"   2 minutes ago       Up 2 minutes                            demo.3.gksbc1pfl1l4mius4qqhv0le6
d865d4f10da5        busybox:latest      "sh -c 'while true; …"   7 minutes ago       Up 7 minutes                            demo.5.t73ji5comfd7z5hgsglvwok7b
[vagrant@swarm-worker2 ~]$ docker ps
CONTAINER ID        IMAGE               COMMAND             CREATED             STATUS              PORTS               NAMES
[vagrant@swarm-worker2 ~]$
```

### 创建跨主机通讯网络

```shell
[vagrant@swarm-manager ~]$ docker network create -d overlay demo
58opymcjrf7b6mtrtfcptits3
[vagrant@swarm-manager ~]$
[vagrant@swarm-manager ~]$ docker network ls
NETWORK ID          NAME                DRIVER              SCOPE
1fc7fdaf81ca        bridge              bridge              local
58opymcjrf7b        demo                overlay             swarm
6c2642aea9e1        docker_gwbridge     bridge              local
a7643289f689        host                host                local
mqs79u9ms8z4        ingress             overlay             swarm
71da4e2708b5        none                null                local
[vagrant@swarm-manager ~]$
```

### 在overlay网络中创建mysql数据库

```shell
[vagrant@swarm-manager ~]$ docker service create --name mysql --env MYSQL_ROOT_PASSWORD=root --env MYSQL_DATABASE-wordpr
ess --network demo --mount type=volume,source=mysql-data,destination=/var/lib/mysql mysql:5.6
98tn14l0shb91fvrfla6bj286
overall progress: 1 out of 1 tasks
1/1: running   [==================================================>]
verify: Service converged
[vagrant@swarm-manager ~]$
[vagrant@swarm-manager ~]$ docker service ls
ID                  NAME                MODE                REPLICAS            IMAGE               PORTS
98tn14l0shb9        mysql               replicated          1/1                 mysql:5.6
[vagrant@swarm-manager ~]$ docker service ps mysql
ID                  NAME                IMAGE               NODE                DESIRED STATE       CURRENT STATE                ERROR               PORTS
ju57429qsre0        mysql.1             mysql:5.6           swarm-worker2       Running             Running about a minute ago
[vagrant@swarm-manager ~]$
```

### 通过overlay网络启动wordpress

```shell
[vagrant@swarm-manager ~]$ docker service create --name wordpress -p 80:80 --env WORDPRESS_DB_PASSWORD=root --env WORDPRESS_DB_HOST=mysql --network demo wordpress
ketckqwflnd454l1tckbtysjc
overall progress: 1 out of 1 tasks
1/1: running   [==================================================>]
verify: Service converged
[vagrant@swarm-manager ~]$
[vagrant@swarm-manager ~]$ docker service ls
ID                  NAME                MODE                REPLICAS            IMAGE               PORTS
98tn14l0shb9        mysql               replicated          1/1                 mysql:5.6
ketckqwflnd4        wordpress           replicated          1/1                 wordpress:latest    *:80->80/tcp
[vagrant@swarm-manager ~]$ docker service ps wordpress
ID                  NAME                IMAGE               NODE                DESIRED STATE       CURRENT STATE           ERROR                       PORTS
rlukqirh6b24        wordpress.1         wordpress:latest    swarm-manager       Running             Running 4 seconds ago
xpbrxkstisop         \_ wordpress.1     wordpress:latest    swarm-manager       Shutdown            Failed 10 seconds ago   "task: non-zero exit (1)"
3ixvrb2mpeyc         \_ wordpress.1     wordpress:latest    swarm-manager       Shutdown            Failed 43 seconds ago   "task: non-zero exit (1)"
[vagrant@swarm-manager ~]$
```

在浏览器上可以通过三个节点的ip进行访问

### swarm容器间网络通讯

```shell
[vagrant@swarm-manager ~]$ docker service create --name whoami -p 8000:8000 --network demo -d jwilder/whoami
m4w3xleb7kutq871qla697bbl
[vagrant@swarm-manager ~]$ docker service ls
ID                  NAME                MODE                REPLICAS            IMAGE                   PORTS
98tn14l0shb9        mysql               replicated          1/1                 mysql:5.6
m4w3xleb7kut        whoami              replicated          1/1                 jwilder/whoami:latest   *:8000->8000/tcp
390knekgu5bd        wordpress           replicated          1/1                 wordpress:latest        *:80->80/tcp
[vagrant@swarm-manager ~]$ docker service ps whoami
ID                  NAME                IMAGE                   NODE                DESIRED STATE       CURRENT STATE            ERROR               PORTS
ls8jc5ovvjyo        whoami.1            jwilder/whoami:latest   swarm-worker1       Running             Running 33 seconds ago
[vagrant@swarm-manager ~]$
```



```shell
[vagrant@swarm-manager ~]$ curl 127.0.0.1:8000
I'm 71357eeba10e
[vagrant@swarm-manager ~]$
```

```shell
[vagrant@swarm-manager ~]$ docker service create --name client -d --network demo busybox sh -c "while true; do sleep 3600; done"
8qot1tbb24p3nmtr90n4srsq2
[vagrant@swarm-manager ~]$ docker service ls
ID                  NAME                MODE                REPLICAS            IMAGE                   PORTS
8qot1tbb24p3        client              replicated          1/1                 busybox:latest
98tn14l0shb9        mysql               replicated          1/1                 mysql:5.6
m4w3xleb7kut        whoami              replicated          1/1                 jwilder/whoami:latest   *:8000->8000/tcp
390knekgu5bd        wordpress           replicated          1/1                 wordpress:latest        *:80->80/tcp
[vagrant@swarm-manager ~]$ docker service ps client
ID                  NAME                IMAGE               NODE                DESIRED STATE       CURRENT STATE                ERROR               PORTS
33nq3agmz39o        client.1            busybox:latest      swarm-worker2       Running             Running about a minute ago
[vagrant@swarm-manager ~]$
```



```shell
[vagrant@swarm-worker2 ~]$ ddocker ps
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS               NAMES
bcfbda5c4c83        mysql:5.6           "docker-entrypoint.s…"   27 minutes ago      Up 27 minutes       3306/tcp            mysql.1.ju57429qsre0m1wigrupmtbyo
[vagrant@swarm-worker2 ~]$ docker ps
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS               NAMES
d078d2f67933        busybox:latest      "sh -c 'while true; …"   2 minutes ago       Up 2 minutes                            client.1.33nq3agmz39o5xye2xb65htm5
bcfbda5c4c83        mysql:5.6           "docker-entrypoint.s…"   2 hours ago         Up 2 hours          3306/tcp            mysql.1.ju57429qsre0m1wigrupmtbyo
[vagrant@swarm-worker2 ~]$ docker exec -it d078
"docker exec" requires at least 2 arguments.
See 'docker exec --help'.

Usage:  docker exec [OPTIONS] CONTAINER COMMAND [ARG...]

Run a command in a running container
[vagrant@swarm-worker2 ~]$ docker exec -it d078 /bin/sh
/ # ping whoami
PING whoami (10.0.0.35): 56 data bytes
64 bytes from 10.0.0.35: seq=0 ttl=64 time=0.061 ms
64 bytes from 10.0.0.35: seq=1 ttl=64 time=0.053 ms
64 bytes from 10.0.0.35: seq=2 ttl=64 time=0.054 ms
64 bytes from 10.0.0.35: seq=3 ttl=64 time=0.053 ms
^C
--- whoami ping statistics ---
4 packets transmitted, 4 packets received, 0% packet loss
round-trip min/avg/max = 0.053/0.055/0.061 ms
```

扩展whoami容器数量

```shell
[vagrant@swarm-manager ~]$ docker service scale whoami=2
whoami scaled to 2
overall progress: 2 out of 2 tasks
1/2: running   [==================================================>]
2/2: running   [==================================================>]
verify: Service converged
[vagrant@swarm-manager ~]$ docker service ps whoami
ID                  NAME                IMAGE                   NODE                DESIRED STATE       CURRENT STATE            ERROR               PORTS
ls8jc5ovvjyo        whoami.1            jwilder/whoami:latest   swarm-worker1       Running             Running 38 minutes ago
sloe33nugg4b        whoami.2            jwilder/whoami:latest   swarm-manager       Running             Running 5 minutes ago
[vagrant@swarm-manager ~]$
```

ping容器whoami返回信息还是不变

```shell

/ # ping whoami
PING whoami (10.0.0.35): 56 data bytes
64 bytes from 10.0.0.35: seq=0 ttl=64 time=0.052 ms
64 bytes from 10.0.0.35: seq=1 ttl=64 time=0.056 ms
64 bytes from 10.0.0.35: seq=2 ttl=64 time=0.059 ms
64 bytes from 10.0.0.35: seq=3 ttl=64 time=0.057 ms
64 bytes from 10.0.0.35: seq=4 ttl=64 time=0.056 ms
64 bytes from 10.0.0.35: seq=5 ttl=64 time=0.061 ms
^C
--- whoami ping statistics ---
6 packets transmitted, 6 packets received, 0% packet loss
round-trip min/avg/max = 0.052/0.056/0.061 ms
/ #
/ # nslookup whoami
Server:         127.0.0.11
Address:        127.0.0.11:53

Non-authoritative answer:

*** Can't find whoami: No answer
```

### 查看网络

```shell
[vagrant@swarm-worker1 ~]$ docker network inspect demo
[
    {
        "Name": "demo",
        "Id": "58opymcjrf7b6mtrtfcptits3",
        "Created": "2018-09-11T13:21:37.54469869Z",
        "Scope": "swarm",
        "Driver": "overlay",
        "EnableIPv6": false,
        "IPAM": {
            "Driver": "default",
            "Options": null,
            "Config": [
                {
                    "Subnet": "10.0.0.0/24",
                    "Gateway": "10.0.0.1"
                }
            ]
        },
        "Internal": false,
        "Attachable": false,
        "Ingress": false,
        "ConfigFrom": {
            "Network": ""
        },
        "ConfigOnly": false,
        "Containers": {
            "71357eeba10ed76896a61e1c5898b95bae71fb6cff086dadaa0b44c871d176dc": {
                "Name": "whoami.1.ls8jc5ovvjyomsae8r7zse7p4",
                "EndpointID": "fd0bcbbea3a4e8077ed892eb4e0c0963c4175b66ba2530208749497eda72fc1c",
                "MacAddress": "02:42:0a:00:00:24",
                "IPv4Address": "10.0.0.36/24",
                "IPv6Address": ""
            },
            "lb-demo": {
                "Name": "demo-endpoint",
                "EndpointID": "7c09e1c6b4451a9f79a5b412c2238a1d5fa242ad12c51b944f3dc975d6d7eb4f",
                "MacAddress": "02:42:0a:00:00:04",
                "IPv4Address": "10.0.0.4/24",
                "IPv6Address": ""
            }
        },
        "Options": {
            "com.docker.network.driver.overlay.vxlanid_list": "4097"
        },
        "Labels": {},
        "Peers": [
            {
                "Name": "6abcc0602dd2",
                "IP": "192.168.205.12"
            },
            {
                "Name": "ee7f45f6419d",
                "IP": "192.168.205.10"
            },
            {
                "Name": "25f53d4f9e34",
                "IP": "192.168.205.11"
            }
        ]
    }
]
[vagrant@swarm-worker1 ~]$
```

将whoami容器扩展到4个实例

```shell
[vagrant@swarm-manager ~]$ docker service scale whoami=4
whoami scaled to 4
overall progress: 4 out of 4 tasks
1/4: running   [==================================================>]
2/4: running   [==================================================>]
3/4: running   [==================================================>]
4/4: running   [==================================================>]
verify: Service converged
[vagrant@swarm-manager ~]$ docker service ps whoami
ID                  NAME                IMAGE                   NODE                DESIRED STATE       CURRENT STATE               ERROR               PORTS
ls8jc5ovvjyo        whoami.1            jwilder/whoami:latest   swarm-worker1       Running             Running about an hour ago
sloe33nugg4b        whoami.2            jwilder/whoami:latest   swarm-manager       Running             Running 29 minutes ago
otbyahvwsnp7        whoami.3            jwilder/whoami:latest   swarm-worker1       Running             Running 3 minutes ago
ymhv1ix5qh70        whoami.4            jwilder/whoami:latest   swarm-worker2       Running             Running 3 minutes ago
[vagrant@swarm-manager ~]$

[vagrant@swarm-worker2 ~]$ docker ps
CONTAINER ID        IMAGE                   COMMAND                  CREATED             STATUS              PORTS               NAMES
cb3f52e43a83        jwilder/whoami:latest   "/app/http"              5 minutes ago       Up 5 minutes        8000/tcp            whoami.4.ymhv1ix5qh706ge909165ihbs
d078d2f67933        busybox:latest          "sh -c 'while true; …"   38 minutes ago      Up 38 minutes                           client.1.33nq3agmz39o5xye2xb65htm5
bcfbda5c4c83        mysql:5.6               "docker-entrypoint.s…"   2 hours ago         Up 2 hours          3306/tcp            mysql.1.ju57429qsre0m1wigrupmtbyo
[vagrant@swarm-worker2 ~]$ docker exec -it d078d2f67933 sh
/ # wget whoami:8000 && more index.html  && rm -f index.html
Connecting to whoami:8000 (10.0.0.35:8000)
index.html           100% |************************************************************************|    17  0:00:00 ETA
I'm c32d120df646
/ # wget whoami:8000 && more index.html  && rm -f index.html
Connecting to whoami:8000 (10.0.0.35:8000)
index.html           100% |************************************************************************|    17  0:00:00 ETA
I'm f23ab7e93207
/ # wget whoami:8000 && more index.html  && rm -f index.html
Connecting to whoami:8000 (10.0.0.35:8000)
index.html           100% |************************************************************************|    17  0:00:00 ETA
I'm 71357eeba10e
/ # wget whoami:8000 && more index.html  && rm -f index.html
Connecting to whoami:8000 (10.0.0.35:8000)
index.html           100% |************************************************************************|    17  0:00:00 ETA
I'm cb3f52e43a83
/ #
```

在每个swarm节点上都可以通过本地ip访问whoami

```shell
[vagrant@swarm-worker1 ~]$ curl 127.0.0.1:8000
I'm cb3f52e43a83
[vagrant@swarm-worker1 ~]$ curl 127.0.0.1:8000
I'm c32d120df646
[vagrant@swarm-worker1 ~]$ curl 127.0.0.1:8000
I'm f23ab7e93207
[vagrant@swarm-worker1 ~]$ curl 127.0.0.1:8000
I'm 71357eeba10e
[vagrant@swarm-worker1 ~]$ curl 127.0.0.1:8000
I'm cb3f52e43a83
[vagrant@swarm-worker1 ~]$ curl 127.0.0.1:8000
I'm c32d120df646
[vagrant@swarm-worker1 ~]$
```

查看iptables转发guiz

```shell
[vagrant@swarm-worker1 ~]$ sudo iptables -nL -t nat
Chain PREROUTING (policy ACCEPT)
target     prot opt source               destination
DOCKER-INGRESS  all  --  0.0.0.0/0            0.0.0.0/0            ADDRTYPE match dst-type LOCAL
DOCKER     all  --  0.0.0.0/0            0.0.0.0/0            ADDRTYPE match dst-type LOCAL

Chain INPUT (policy ACCEPT)
target     prot opt source               destination

Chain OUTPUT (policy ACCEPT)
target     prot opt source               destination
DOCKER-INGRESS  all  --  0.0.0.0/0            0.0.0.0/0            ADDRTYPE match dst-type LOCAL
DOCKER     all  --  0.0.0.0/0           !127.0.0.0/8          ADDRTYPE match dst-type LOCAL

Chain POSTROUTING (policy ACCEPT)
target     prot opt source               destination
MASQUERADE  all  --  0.0.0.0/0            0.0.0.0/0            ADDRTYPE match src-type LOCAL
MASQUERADE  all  --  172.17.0.0/16        0.0.0.0/0
MASQUERADE  all  --  172.18.0.0/16        0.0.0.0/0

Chain DOCKER (2 references)
target     prot opt source               destination
RETURN     all  --  0.0.0.0/0            0.0.0.0/0
RETURN     all  --  0.0.0.0/0            0.0.0.0/0

Chain DOCKER-INGRESS (2 references)
target     prot opt source               destination
DNAT       tcp  --  0.0.0.0/0            0.0.0.0/0            tcp dpt:8000 to:172.18.0.2:8000
DNAT       tcp  --  0.0.0.0/0            0.0.0.0/0            tcp dpt:80 to:172.18.0.2:80
RETURN     all  --  0.0.0.0/0            0.0.0.0/0
[vagrant@swarm-worker1 ~]$
```

查看ip

```shell
[vagrant@swarm-worker1 ~]$ ip a
1: lo: <LOOPBACK,UP,LOWER_UP> mtu 65536 qdisc noqueue state UNKNOWN group default qlen 1000
    link/loopback 00:00:00:00:00:00 brd 00:00:00:00:00:00
    inet 127.0.0.1/8 scope host lo
       valid_lft forever preferred_lft forever
    inet6 ::1/128 scope host
       valid_lft forever preferred_lft forever
2: eth0: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc pfifo_fast state UP group default qlen 1000
    link/ether 52:54:00:c9:c7:04 brd ff:ff:ff:ff:ff:ff
    inet 10.0.2.15/24 brd 10.0.2.255 scope global noprefixroute dynamic eth0
       valid_lft 75985sec preferred_lft 75985sec
    inet6 fe80::5054:ff:fec9:c704/64 scope link
       valid_lft forever preferred_lft forever
3: eth1: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc pfifo_fast state UP group default qlen 1000
    link/ether 08:00:27:f8:49:9e brd ff:ff:ff:ff:ff:ff
    inet 192.168.205.11/24 brd 192.168.205.255 scope global noprefixroute eth1
       valid_lft forever preferred_lft forever
    inet6 fe80::a00:27ff:fef8:499e/64 scope link
       valid_lft forever preferred_lft forever
4: docker0: <NO-CARRIER,BROADCAST,MULTICAST,UP> mtu 1500 qdisc noqueue state DOWN group default
    link/ether 02:42:b0:84:d9:4c brd ff:ff:ff:ff:ff:ff
    inet 172.17.0.1/16 brd 172.17.255.255 scope global docker0
       valid_lft forever preferred_lft forever
    inet6 fe80::42:b0ff:fe84:d94c/64 scope link
       valid_lft forever preferred_lft forever
5: docker_gwbridge: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc noqueue state UP group default
    link/ether 02:42:f8:6c:23:81 brd ff:ff:ff:ff:ff:ff
    inet 172.18.0.1/16 brd 172.18.255.255 scope global docker_gwbridge
       valid_lft forever preferred_lft forever
    inet6 fe80::42:f8ff:fe6c:2381/64 scope link
       valid_lft forever preferred_lft forever
11: veth5a8054b@if10: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc noqueue master docker_gwbridge state UP group default
    link/ether b6:90:5a:ba:38:0a brd ff:ff:ff:ff:ff:ff link-netnsid 1
    inet6 fe80::b490:5aff:feba:380a/64 scope link
       valid_lft forever preferred_lft forever
93: vethcee5653@if92: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc noqueue master docker_gwbridge state UP group default
    link/ether 5a:70:55:d6:71:86 brd ff:ff:ff:ff:ff:ff link-netnsid 4
    inet6 fe80::5870:55ff:fed6:7186/64 scope link
       valid_lft forever preferred_lft forever
99: vethf7770f2@if98: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc noqueue master docker_gwbridge state UP group default
    link/ether fe:85:12:b4:64:32 brd ff:ff:ff:ff:ff:ff link-netnsid 5
    inet6 fe80::fc85:12ff:feb4:6432/64 scope link
       valid_lft forever preferred_lft forever
[vagrant@swarm-worker1 ~]$
```

查看网络端口连接情况

```shell
[vagrant@swarm-worker1 ~]$ brctl show
bridge name     bridge id               STP enabled     interfaces
docker0         8000.0242b084d94c       no
docker_gwbridge         8000.0242f86c2381       no              veth5a8054b
                                                        vethcee5653
                                                        vethf7770f2
[vagrant@swarm-worker1 ~]$
```

查看连接详情

```shell
[vagrant@swarm-worker1 ~]$ docker network ls
NETWORK ID          NAME                DRIVER              SCOPE
76a7006dbd79        bridge              bridge              local
58opymcjrf7b        demo                overlay             swarm
6c41da28f3bc        docker_gwbridge     bridge              local
b0727605895c        host                host                local
mqs79u9ms8z4        ingress             overlay             swarm
5fde706d72b7        none                null                local
[vagrant@swarm-worker1 ~]$ 
[vagrant@swarm-worker1 ~]$ docker network inspect  docker_gwbridge
[
    {
        "Name": "docker_gwbridge",
        "Id": "6c41da28f3bc85d859ab1c9c197049ae924690e7fecf5e55eea2078ed2d4e44b",
        "Created": "2018-09-10T12:35:51.6074491Z",
        "Scope": "local",
        "Driver": "bridge",
        "EnableIPv6": false,
        "IPAM": {
            "Driver": "default",
            "Options": null,
            "Config": [
                {
                    "Subnet": "172.18.0.0/16",
                    "Gateway": "172.18.0.1"
                }
            ]
        },
        "Internal": false,
        "Attachable": false,
        "Ingress": false,
        "ConfigFrom": {
            "Network": ""
        },
        "ConfigOnly": false,
        "Containers": {
            "71357eeba10ed76896a61e1c5898b95bae71fb6cff086dadaa0b44c871d176dc": {
                "Name": "gateway_58dc4cdcaba9",
                "EndpointID": "39006a8eb5d17f0cd6277a7bcf8bc96f44a63533e957976e21aec9be8e257c3f",
                "MacAddress": "02:42:ac:12:00:03",
                "IPv4Address": "172.18.0.3/16",
                "IPv6Address": ""
            },
            "c32d120df64624d68cbd61598887dcc6e7deaec30be8f930317d025d97b6d5ac": {
                "Name": "gateway_961c435f532d",
                "EndpointID": "8c6335b21f731ea03d06dafa8282148fb8d610207325179d081cbdaae725e8e6",
                "MacAddress": "02:42:ac:12:00:04",
                "IPv4Address": "172.18.0.4/16",
                "IPv6Address": ""
            },
            "ingress-sbox": {
                "Name": "gateway_ingress-sbox",
                "EndpointID": "c95f3df52c7ce6d20653611766ce74421eaedc60adcd9f34a7a76fde22f4d5d1",
                "MacAddress": "02:42:ac:12:00:02",
                "IPv4Address": "172.18.0.2/16",
                "IPv6Address": ""
            }
        },
        "Options": {
            "com.docker.network.bridge.enable_icc": "false",
            "com.docker.network.bridge.enable_ip_masquerade": "true",
            "com.docker.network.bridge.name": "docker_gwbridge"
        },
        "Labels": {}
    }
]
[vagrant@swarm-worker1 ~]$
```

查看docker网络

```shell
[vagrant@swarm-manager ~]$ sudo iptables -nL -t nat
Chain PREROUTING (policy ACCEPT)
target     prot opt source               destination
DOCKER-INGRESS  all  --  0.0.0.0/0            0.0.0.0/0            ADDRTYPE match dst-type LOCAL
DOCKER     all  --  0.0.0.0/0            0.0.0.0/0            ADDRTYPE match dst-type LOCAL

Chain INPUT (policy ACCEPT)
target     prot opt source               destination

Chain OUTPUT (policy ACCEPT)
target     prot opt source               destination
DOCKER-INGRESS  all  --  0.0.0.0/0            0.0.0.0/0            ADDRTYPE match dst-type LOCAL
DOCKER     all  --  0.0.0.0/0           !127.0.0.0/8          ADDRTYPE match dst-type LOCAL

Chain POSTROUTING (policy ACCEPT)
target     prot opt source               destination
MASQUERADE  all  --  0.0.0.0/0            0.0.0.0/0            ADDRTYPE match src-type LOCAL
MASQUERADE  all  --  172.17.0.0/16        0.0.0.0/0
MASQUERADE  all  --  172.18.0.0/16        0.0.0.0/0

Chain DOCKER (2 references)
target     prot opt source               destination
RETURN     all  --  0.0.0.0/0            0.0.0.0/0
RETURN     all  --  0.0.0.0/0            0.0.0.0/0

Chain DOCKER-INGRESS (2 references)
target     prot opt source               destination
DNAT       tcp  --  0.0.0.0/0            0.0.0.0/0            tcp dpt:8000 to:172.18.0.2:8000
DNAT       tcp  --  0.0.0.0/0            0.0.0.0/0            tcp dpt:80 to:172.18.0.2:80
RETURN     all  --  0.0.0.0/0            0.0.0.0/0
[vagrant@swarm-manager ~]$ sudo ls /var/run/docker/netns
0fb67d7d68cd  1-mqs79u9ms8  69bb98f9eb89  8c187d1cd545  d6c0a250b021  ingress_sbox
1-58opymcjrf  40cc7b7c8155  6f828ac51ba2  b64c97611fc6  e90ed18bd83a
[vagrant@swarm-manager ~]$ docker network ls
NETWORK ID          NAME                DRIVER              SCOPE
1fc7fdaf81ca        bridge              bridge              local
58opymcjrf7b        demo                overlay             swarm
6c2642aea9e1        docker_gwbridge     bridge              local
a7643289f689        host                host                local
mqs79u9ms8z4        ingress             overlay             swarm
71da4e2708b5        none                null                local
[vagrant@swarm-manager ~]$
```

### Ingress Network网络

```shell
[vagrant@swarm-manager ~]$ sudo nsenter --net=/var/run/docker/netns/ingress_sbox
[root@swarm-manager vagrant]# ip a
1: lo: <LOOPBACK,UP,LOWER_UP> mtu 65536 qdisc noqueue state UNKNOWN group default qlen 1000
    link/loopback 00:00:00:00:00:00 brd 00:00:00:00:00:00
    inet 127.0.0.1/8 scope host lo
       valid_lft forever preferred_lft forever
8: eth0@if9: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1450 qdisc noqueue state UP group default
    link/ether 02:42:0a:ff:00:02 brd ff:ff:ff:ff:ff:ff link-netnsid 0
    inet 10.255.0.2/16 brd 10.255.255.255 scope global eth0
       valid_lft forever preferred_lft forever
    inet 10.255.0.31/32 brd 10.255.0.31 scope global eth0
       valid_lft forever preferred_lft forever
    inet 10.255.0.33/32 brd 10.255.0.33 scope global eth0
       valid_lft forever preferred_lft forever
10: eth1@if11: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc noqueue state UP group default
    link/ether 02:42:ac:12:00:02 brd ff:ff:ff:ff:ff:ff link-netnsid 1
    inet 172.18.0.2/16 brd 172.18.255.255 scope global eth1
       valid_lft forever preferred_lft forever
[root@swarm-manager vagrant]#
```

```shell
[vagrant@swarm-manager ~]$ sudo nsenter --net=/var/run/docker/netns/ingress_sbox
[root@swarm-manager vagrant]# iptables -nL -t mangle
Chain PREROUTING (policy ACCEPT)
target     prot opt source               destination
MARK       tcp  --  0.0.0.0/0            0.0.0.0/0            tcp dpt:80 MARK set 0x12f
MARK       tcp  --  0.0.0.0/0            0.0.0.0/0            tcp dpt:8000 MARK set 0x130

Chain INPUT (policy ACCEPT)
target     prot opt source               destination
MARK       all  --  0.0.0.0/0            10.255.0.31          MARK set 0x12f
MARK       all  --  0.0.0.0/0            10.255.0.33          MARK set 0x130

Chain FORWARD (policy ACCEPT)
target     prot opt source               destination

Chain OUTPUT (policy ACCEPT)
target     prot opt source               destination

Chain POSTROUTING (policy ACCEPT)
target     prot opt source               destination
[root@swarm-manager vagrant]# ipvsadm -l
IP Virtual Server version 1.2.1 (size=4096)
Prot LocalAddress:Port Scheduler Flags
  -> RemoteAddress:Port           Forward Weight ActiveConn InActConn
FWM  303 rr
  -> bogon:0                      Masq    1      0          0
FWM  304 rr
  -> bogon:0                      Masq    1      0          0
  -> bogon:0                      Masq    1      0          0
[root@swarm-manager vagrant]#
```

### docker stack部署应用

```shell
docker stack deploy example --compose-file=docker-compose.yml
docker stack ls
docker stack services example
docker service scale example_vote=3
```

```shell
[vagrant@swarm-manager ~]$ docker stack ls
NAME                SERVICES            ORCHESTRATOR
[vagrant@swarm-manager ~]$ cd labs/example-voting-app/
[vagrant@swarm-manager example-voting-app]$ ls
docker-compose2.yml  docker-compose.yml
[vagrant@swarm-manager example-voting-app]$ docker stack deploy example --compose-file=docker-compose2.yml
Creating network example_frontend
Creating network example_backend
Creating network example_default
Creating service example_visualizer
Creating service example_redis
Creating service example_db
Creating service example_vote
Creating service example_result
Creating service example_worker
[vagrant@swarm-manager example-voting-app]$ docker stack ls
NAME                SERVICES            ORCHESTRATOR
example             6                   Swarm
[vagrant@swarm-manager example-voting-app]$ docker stack services example
ID                  NAME                 MODE                REPLICAS            IMAGE                                                                  PORTS
abfmhjivh2ed        example_result       replicated          1/1                 192.168.33.2/example-voting-app/example-voting-app_result-app:latest   *:5001->80/tcp
db2k1cqvd73p        example_redis        replicated          2/2                 192.168.33.2/library/redis:latest                                      *:30000->6379/tcp
fr0m3ixyd1v6        example_worker       replicated          1/1                 192.168.33.2/example-voting-app/example-voting-app_worker:latest
gctu2otbtugo        example_db           replicated          1/1                 192.168.33.2/library/postgres:9.4      
k9wgickfbyxf        example_vote         replicated          2/2                 192.168.33.2/example-voting-app/example-voting-app_voting-app:latest   *:5000->80/tcp
yk6ih4voni6r        example_visualizer   replicated          1/1                 dockersamples/visualizer:stable                                        *:8080->8080/tcp
[vagrant@swarm-manager example-voting-app]$ docker stack ps example
ID                  NAME                   IMAGE                                                                  NODE                DESIRED STATE       CURRENT STATE            ERROR               PORTS
5ndo402bibiz        example_worker.1       192.168.33.2/example-voting-app/example-voting-app_worker:latest       swarm-manager       Running             Running 42 seconds ago
80wlvvkm2r29        example_result.1       192.168.33.2/example-voting-app/example-voting-app_result-app:latest   swarm-worker2       Running             Running 43 seconds ago
tkzu0hq7rgxq        example_vote.1         192.168.33.2/example-voting-app/example-voting-app_voting-app:latest   swarm-worker1       Running             Running 44 seconds ago
dv5wvh0vjqpd        example_db.1           192.168.33.2/library/postgres:9.4                                      swarm-manager       Running             Running 45 seconds ago
jwjwgu1dwriz        example_redis.1        192.168.33.2/library/redis:latest                                      swarm-worker1       Running             Running 46 seconds ago
y700zwg8tbs1        example_visualizer.1   dockersamples/visualizer:stable                                        swarm-manager       Running             Running 47 seconds ago
5qqvixagjkwt        example_vote.2         192.168.33.2/example-voting-app/example-voting-app_voting-app:latest   swarm-worker2       Running             Running 44 seconds ago
rx29bg6du17e        example_redis.2        192.168.33.2/library/redis:latest                                      swarm-worker2       Running             Running 46 seconds ago
[vagrant@swarm-manager example-voting-app]$ docker service scale example_vote=3
example_vote scaled to 3
overall progress: 3 out of 3 tasks
1/3: running   [==================================================>]
2/3: running   [==================================================>]
3/3: running   [==================================================>]
verify: Service converged
[vagrant@swarm-manager example-voting-app]$ docker stack ps example
ID                  NAME                   IMAGE                                                                  NODE                DESIRED STATE       CURRENT STATE                ERROR               PORTS
5ndo402bibiz        example_worker.1       192.168.33.2/example-voting-app/example-voting-app_worker:latest       swarm-manager       Running             Running about a minute ago
80wlvvkm2r29        example_result.1       192.168.33.2/example-voting-app/example-voting-app_result-app:latest   swarm-worker2       Running             Running about a minute ago
tkzu0hq7rgxq        example_vote.1         192.168.33.2/example-voting-app/example-voting-app_voting-app:latest   swarm-worker1       Running             Running 2 minutes ago
dv5wvh0vjqpd        example_db.1           192.168.33.2/library/postgres:9.4                                      swarm-manager       Running             Running 2 minutes ago
jwjwgu1dwriz        example_redis.1        192.168.33.2/library/redis:latest                                      swarm-worker1       Running             Running 2 minutes ago
y700zwg8tbs1        example_visualizer.1   dockersamples/visualizer:stable                                        swarm-manager       Running             Running 2 minutes ago
5qqvixagjkwt        example_vote.2         192.168.33.2/example-voting-app/example-voting-app_voting-app:latest   swarm-worker2       Running             Running 2 minutes ago
rx29bg6du17e        example_redis.2        192.168.33.2/library/redis:latest                                      swarm-worker2       Running             Running 2 minutes ago
44iuyr2rajzt        example_vote.3         192.168.33.2/example-voting-app/example-voting-app_voting-app:latest   swarm-manager       Running             Running 10 seconds ago
[vagrant@swarm-manager example-voting-app]$
```

docker stack部署wordpress

```shell
[vagrant@swarm-manager ~]$ cd labs/
[vagrant@swarm-manager labs]$ ls
wordpress
[vagrant@swarm-manager labs]$ cd wordpress/
[vagrant@swarm-manager wordpress]$ ls
docker-compose.yml
[vagrant@swarm-manager wordpress]$ docker stack deploy wordpress --compose-file=docker-compose.yml
services.mysql.deploy.placement.constraints must be a list
[vagrant@swarm-manager wordpress]$ docker stack deploy wordpress --compose-file=docker-compose.yml
Creating network wordpress_my-network
Creating service wordpress_mysql
Creating service wordpress_web
[vagrant@swarm-manager wordpress]$ docker stack ls
NAME                SERVICES            ORCHESTRATOR
wordpress           2                   Swarm
[vagrant@swarm-manager wordpress]$ docker stack services wordpress
ID                  NAME                MODE                REPLICAS            IMAGE               PORTS
ex31mvi9vhhs        wordpress_mysql     global              0/0                 mysql:5.6
ezi7bpomhqot        wordpress_web       replicated          1/3                 wordpress:latest    *:8080->80/tcp
[vagrant@swarm-manager wordpress]$ docker stack ps wordpress
ID                  NAME                  IMAGE               NODE                DESIRED STATE       CURRENT STATE            ERROR                       PORTS
k522ptqmy1rx        wordpress_web.1       wordpress:latest    swarm-worker1       Running             Running 19 seconds ago
65u6v7g8lxyo         \_ wordpress_web.1   wordpress:latest    swarm-worker1       Shutdown            Failed 25 seconds ago    "task: non-zero exit (1)"
qw9a1r2u0436        wordpress_web.2       wordpress:latest    swarm-manager       Running             Running 18 seconds ago
e9ffr1s3xdrn         \_ wordpress_web.2   wordpress:latest    swarm-manager       Shutdown            Failed 24 seconds ago    "task: non-zero exit (1)"
pq6ict85gjfj        wordpress_web.3       wordpress:latest    swarm-worker2       Running             Running 24 seconds ago
```

### docker secret manager

```shell
vim password
docker secret create my-pw password
docker secret ls
echo "adminadmin" | docker secret create my-pw2
docker secret ls
```

```shell
docker service create --name client --secret my-pw busybox sh -c "while true; do sleep 3600; done"
docker service ls
docker service ps client
docker ps
docker exec -it containerId sh
cd /run/secrets/
ls

```

```shell
docker service create --name db --secret my-pw -e MYSQL_ROOT_PASSWORD_FILE=/run/secrets/my-pw mysql:5.6
docker service ls
docker service ps db
docker ps 
docker exec -it contanierId sh
ls /run/secrets/
cat /run/secrets/my-pw
mysql -u root -p
```

docker secret  部署wordpress

```shell
docker stack deploy wordpress -c=docker-compose.yml
docker stack services wordpress
```

### docker service 更新

```shell
docker network create -d overlay demo
docker network ls
docker service create --name web --publish 8080:5000 --network demo xiaopeng163/python-flask-demo:1.0
docker service ps web
docker service scale web=2
docker service ps web
curl 127.0.0.1:8080
sh -c "while true; do curl 127.0.0.1:8080 && sleep 1; done"
docker service update --image xiaopeng163/python-flask-demo:2.0 web
docker service ps web
docker service update --publish-rm 8080:5000 --publish-add 8088:5000 web
docker service ls
```

