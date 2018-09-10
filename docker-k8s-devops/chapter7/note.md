### docker swarm

主节点manager

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

worker1节点

```shell
[vagrant@swarm-worker1 ~]$ docker swarm join --token SWMTKN-1-5e3x287y2x0mzlr4rfnvvqzbu8tvt72nny293mdt70be0vkfxq-8549bsispxxg39nczjario7x6 192.168.205.10:2377
This node joined a swarm as a worker.
[vagrant@swarm-worker1 ~]$
```

查看所有节点

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

通过swarm创建容器

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

三个节点看看是否有容器在运行

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

重启系统后docker service会自动运行

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

扩展容器

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

工作节点上强制删除容器，swarm自动重新创建容器

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

