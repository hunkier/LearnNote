### docker网络

```shell
sudo docker run -d --name test1 busybox /bin/sh -c "while true; do sleep 3600; done"
```

##### 运行busybox

```shell
[vagrant@docker-node1 ~]$ docker pull busybox
Using default tag: latest
latest: Pulling from library/busybox
8c5a7da1afbc: Pull complete
Digest: sha256:cb63aa0641a885f54de20f61d152187419e8f6b159ed11a251a09d115fdff9bd
Status: Downloaded newer image for busybox:latest
[vagrant@docker-node1 ~]$ docker images
REPOSITORY          TAG                 IMAGE ID            CREATED             SIZE
busybox             latest              e1ddd7948a1c        5 weeks ago         1.16MB
[vagrant@docker-node1 ~]$ sudo docker run -d --name test1 busybox /bin/sh -c "while true; do sleep 3600; done"
abeece1257c0a93af85fdbe131daf3f29d5595cfaadc96a17d9fefaa622245dc
[vagrant@docker-node1 ~]$ docker ps
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS               NAMES
abeece1257c0        busybox             "/bin/sh -c 'while t…"   7 seconds ago       Up 6 seconds                            test1
[vagrant@docker-node1 ~]$ docker exec -it abeece1257c0  /bin/sh
/ # ip a
1: lo: <LOOPBACK,UP,LOWER_UP> mtu 65536 qdisc noqueue qlen 1000
    link/loopback 00:00:00:00:00:00 brd 00:00:00:00:00:00
    inet 127.0.0.1/8 scope host lo
       valid_lft forever preferred_lft forever
5: eth0@if6: <BROADCAST,MULTICAST,UP,LOWER_UP,M-DOWN> mtu 1500 qdisc noqueue
    link/ether 02:42:ac:11:00:02 brd ff:ff:ff:ff:ff:ff
    inet 172.17.0.2/16 brd 172.17.255.255 scope global eth0
       valid_lft forever preferred_lft forever
/ #
```

##### 再运行一个busybox容器，两个容器之间可以ping通

```shell
[vagrant@docker-node1 ~]$ sudo docker run -d --name test2 busybox /bin/sh -c "while true; do sleep 3600; done"
1c714971da1e5b4e22728939264a4f9c93d99dec4bce70dcf53cb6b9c0b46842
[vagrant@docker-node1 ~]$ docker ps
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS               NAMES
1c714971da1e        busybox             "/bin/sh -c 'while t…"   4 seconds ago       Up 3 seconds                            test2
abeece1257c0        busybox             "/bin/sh -c 'while t…"   5 minutes ago       Up 5 minutes   
[vagrant@docker-node1 ~]$ docker exec -it 1c71 /bin/sh
/ # ip a
1: lo: <LOOPBACK,UP,LOWER_UP> mtu 65536 qdisc noqueue qlen 1000
    link/loopback 00:00:00:00:00:00 brd 00:00:00:00:00:00
    inet 127.0.0.1/8 scope host lo
       valid_lft forever preferred_lft forever
7: eth0@if8: <BROADCAST,MULTICAST,UP,LOWER_UP,M-DOWN> mtu 1500 qdisc noqueue
    link/ether 02:42:ac:11:00:03 brd ff:ff:ff:ff:ff:ff
    inet 172.17.0.3/16 brd 172.17.255.255 scope global eth0
       valid_lft forever preferred_lft forever
/ #
/ # ping 172.17.0.2
PING 172.17.0.2 (172.17.0.2): 56 data bytes
64 bytes from 172.17.0.2: seq=0 ttl=64 time=0.077 ms
64 bytes from 172.17.0.2: seq=1 ttl=64 time=0.049 ms
64 bytes from 172.17.0.2: seq=2 ttl=64 time=0.046 ms
64 bytes from 172.17.0.2: seq=3 ttl=64 time=0.046 ms
^C
--- 172.17.0.2 ping statistics ---
4 packets transmitted, 4 packets received, 0% packet loss
round-trip min/avg/max = 0.046/0.054/0.077 ms
/ #
```

##### 查看busybox容器ip

```shell
[vagrant@docker-node1 ~]$ docker ps
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS               NAMES
1c714971da1e        busybox             "/bin/sh -c 'while t…"   3 hours ago         Up About a minute                       test2
abeece1257c0        busybox             "/bin/sh -c 'while t…"   3 hours ago         Up About a minute                       test1
[vagrant@docker-node1 ~]$ docker exec 1c714971da1e /bin/ip addr
1: lo: <LOOPBACK,UP,LOWER_UP> mtu 65536 qdisc noqueue qlen 1000
    link/loopback 00:00:00:00:00:00 brd 00:00:00:00:00:00
    inet 127.0.0.1/8 scope host lo
       valid_lft forever preferred_lft forever
7: eth0@if8: <BROADCAST,MULTICAST,UP,LOWER_UP,M-DOWN> mtu 1500 qdisc noqueue
    link/ether 02:42:ac:11:00:03 brd ff:ff:ff:ff:ff:ff
    inet 172.17.0.3/16 brd 172.17.255.255 scope global eth0
       valid_lft forever preferred_lft forever
[vagrant@docker-node1 ~]$
[vagrant@docker-node1 ~]$ docker exec 1c714971da1e ip a
1: lo: <LOOPBACK,UP,LOWER_UP> mtu 65536 qdisc noqueue qlen 1000
    link/loopback 00:00:00:00:00:00 brd 00:00:00:00:00:00
    inet 127.0.0.1/8 scope host lo
       valid_lft forever preferred_lft forever
7: eth0@if8: <BROADCAST,MULTICAST,UP,LOWER_UP,M-DOWN> mtu 1500 qdisc noqueue
    link/ether 02:42:ac:11:00:03 brd ff:ff:ff:ff:ff:ff
    inet 172.17.0.3/16 brd 172.17.255.255 scope global eth0
       valid_lft forever preferred_lft forever
[vagrant@docker-node1 ~]$
[vagrant@docker-node1 ~]$ docker exec abeece1257c0 ip a
1: lo: <LOOPBACK,UP,LOWER_UP> mtu 65536 qdisc noqueue qlen 1000
    link/loopback 00:00:00:00:00:00 brd 00:00:00:00:00:00
    inet 127.0.0.1/8 scope host lo
       valid_lft forever preferred_lft forever
5: eth0@if6: <BROADCAST,MULTICAST,UP,LOWER_UP,M-DOWN> mtu 1500 qdisc noqueue
    link/ether 02:42:ac:11:00:02 brd ff:ff:ff:ff:ff:ff
    inet 172.17.0.2/16 brd 172.17.255.255 scope global eth0
       valid_lft forever preferred_lft forever
[vagrant@docker-node1 ~]$
[vagrant@docker-node1 ~]$ docker exec abeece1257c0 ping 172.17.0.3
PING 172.17.0.3 (172.17.0.3): 56 data bytes
64 bytes from 172.17.0.3: seq=0 ttl=64 time=0.069 ms
64 bytes from 172.17.0.3: seq=1 ttl=64 time=0.066 ms
64 bytes from 172.17.0.3: seq=2 ttl=64 time=0.045 ms
64 bytes from 172.17.0.3: seq=3 ttl=64 time=0.055 ms
```

##### 查看Linux本机netnamespace

```shell
[vagrant@docker-node1 ~]$ ip netns list
[vagrant@docker-node1 ~]$ sudo ip netns add test1
[vagrant@docker-node1 ~]$ sudo ip netns list
test1
[vagrant@docker-node1 ~]$
```

##### 查看ip link

```shell
[vagrant@docker-node1 ~]$ sudo ip netns exec test1 ip a
1: lo: <LOOPBACK> mtu 65536 qdisc noop state DOWN group default qlen 1000
    link/loopback 00:00:00:00:00:00 brd 00:00:00:00:00:00
[vagrant@docker-node1 ~]$ ip link
1: lo: <LOOPBACK,UP,LOWER_UP> mtu 65536 qdisc noqueue state UNKNOWN mode DEFAULT group default qlen 1000
    link/loopback 00:00:00:00:00:00 brd 00:00:00:00:00:00
2: eth0: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc pfifo_fast state UP mode DEFAULT group default qlen 1000
    link/ether 52:54:00:c9:c7:04 brd ff:ff:ff:ff:ff:ff
3: eth1: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc pfifo_fast state UP mode DEFAULT group default qlen 1000
    link/ether 08:00:27:8f:d9:3b brd ff:ff:ff:ff:ff:ff
4: docker0: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc noqueue state UP mode DEFAULT group default
    link/ether 02:42:3d:3f:7c:b9 brd ff:ff:ff:ff:ff:ff
6: veth2ce9e50@if5: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc noqueue master docker0 state UP mode DEFAULT group default
    link/ether a2:60:2c:40:4f:80 brd ff:ff:ff:ff:ff:ff link-netnsid 0
8: veth3f330be@if7: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc noqueue master docker0 state UP mode DEFAULT group default
    link/ether 96:af:af:62:72:58 brd ff:ff:ff:ff:ff:ff link-netnsid 1
[vagrant@docker-node1 ~]$ sudo ip netns exec test1 ip link
1: lo: <LOOPBACK> mtu 65536 qdisc noop state DOWN mode DEFAULT group default qlen 1000
    link/loopback 00:00:00:00:00:00 brd 00:00:00:00:00:00
[vagrant@docker-node1 ~]$
```

up新建netns

```shell
[vagrant@docker-node1 ~]$ sudo ip netns exec test1 ip link
1: lo: <LOOPBACK> mtu 65536 qdisc noop state DOWN mode DEFAULT group default qlen 1000
    link/loopback 00:00:00:00:00:00 brd 00:00:00:00:00:00
[vagrant@docker-node1 ~]$ sudo ip netns exec test1 ip link set dev lo up
[vagrant@docker-node1 ~]$ sudo ip netns exec test1 ip link
1: lo: <LOOPBACK,UP,LOWER_UP> mtu 65536 qdisc noqueue state UNKNOWN mode DEFAULT group default qlen 1000
    link/loopback 00:00:00:00:00:00 brd 00:00:00:00:00:00
[vagrant@docker-node1 ~]$
```

##### 连接两个netns

```shell
[vagrant@docker-node1 ~]$ sudo ip netns list
test2
test1
[vagrant@docker-node1 ~]$ sudo ip link add veth-test1 type veth peer name veth-test2
[vagrant@docker-node1 ~]$ ip link
1: lo: <LOOPBACK,UP,LOWER_UP> mtu 65536 qdisc noqueue state UNKNOWN mode DEFAULT group default qlen 1000
    link/loopback 00:00:00:00:00:00 brd 00:00:00:00:00:00
2: eth0: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc pfifo_fast state UP mode DEFAULT group default qlen 1000
    link/ether 52:54:00:c9:c7:04 brd ff:ff:ff:ff:ff:ff
3: eth1: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc pfifo_fast state UP mode DEFAULT group default qlen 1000
    link/ether 08:00:27:8f:d9:3b brd ff:ff:ff:ff:ff:ff
4: docker0: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc noqueue state UP mode DEFAULT group default
    link/ether 02:42:3d:3f:7c:b9 brd ff:ff:ff:ff:ff:ff
6: veth2ce9e50@if5: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc noqueue master docker0 state UP mode DEFAULT group default
    link/ether a2:60:2c:40:4f:80 brd ff:ff:ff:ff:ff:ff link-netnsid 0
8: veth3f330be@if7: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc noqueue master docker0 state UP mode DEFAULT group default
    link/ether 96:af:af:62:72:58 brd ff:ff:ff:ff:ff:ff link-netnsid 1
9: veth-test2@veth-test1: <BROADCAST,MULTICAST,M-DOWN> mtu 1500 qdisc noop state DOWN mode DEFAULT group default qlen 1000
    link/ether 46:b4:0d:9b:18:fe brd ff:ff:ff:ff:ff:ff
10: veth-test1@veth-test2: <BROADCAST,MULTICAST,M-DOWN> mtu 1500 qdisc noop state DOWN mode DEFAULT group default qlen 1000
    link/ether ee:87:f9:14:f6:e2 brd ff:ff:ff:ff:ff:ff
[vagrant@docker-node1 ~]$ sudo ip link set veth-test1 netns test1
[vagrant@docker-node1 ~]$ sudo ip netns exec test1 ip link
1: lo: <LOOPBACK,UP,LOWER_UP> mtu 65536 qdisc noqueue state UNKNOWN mode DEFAULT group default qlen 1000
    link/loopback 00:00:00:00:00:00 brd 00:00:00:00:00:00
10: veth-test1@if9: <BROADCAST,MULTICAST> mtu 1500 qdisc noop state DOWN mode DEFAULT group default qlen 1000
    link/ether ee:87:f9:14:f6:e2 brd ff:ff:ff:ff:ff:ff link-netnsid 0
[vagrant@docker-node1 ~]$ ip link
1: lo: <LOOPBACK,UP,LOWER_UP> mtu 65536 qdisc noqueue state UNKNOWN mode DEFAULT group default qlen 1000
    link/loopback 00:00:00:00:00:00 brd 00:00:00:00:00:00
2: eth0: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc pfifo_fast state UP mode DEFAULT group default qlen 1000
    link/ether 52:54:00:c9:c7:04 brd ff:ff:ff:ff:ff:ff
3: eth1: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc pfifo_fast state UP mode DEFAULT group default qlen 1000
    link/ether 08:00:27:8f:d9:3b brd ff:ff:ff:ff:ff:ff
4: docker0: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc noqueue state UP mode DEFAULT group default
    link/ether 02:42:3d:3f:7c:b9 brd ff:ff:ff:ff:ff:ff
6: veth2ce9e50@if5: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc noqueue master docker0 state UP mode DEFAULT group default
    link/ether a2:60:2c:40:4f:80 brd ff:ff:ff:ff:ff:ff link-netnsid 0
8: veth3f330be@if7: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc noqueue master docker0 state UP mode DEFAULT group default
    link/ether 96:af:af:62:72:58 brd ff:ff:ff:ff:ff:ff link-netnsid 1
9: veth-test2@if10: <BROADCAST,MULTICAST> mtu 1500 qdisc noop state DOWN mode DEFAULT group default qlen 1000
    link/ether 46:b4:0d:9b:18:fe brd ff:ff:ff:ff:ff:ff link-netnsid 2
[vagrant@docker-node1 ~]$ sudo ip link set veth-test2 netns test2
[vagrant@docker-node1 ~]$ ip link
1: lo: <LOOPBACK,UP,LOWER_UP> mtu 65536 qdisc noqueue state UNKNOWN mode DEFAULT group default qlen 1000
    link/loopback 00:00:00:00:00:00 brd 00:00:00:00:00:00
2: eth0: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc pfifo_fast state UP mode DEFAULT group default qlen 1000
    link/ether 52:54:00:c9:c7:04 brd ff:ff:ff:ff:ff:ff
3: eth1: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc pfifo_fast state UP mode DEFAULT group default qlen 1000
    link/ether 08:00:27:8f:d9:3b brd ff:ff:ff:ff:ff:ff
4: docker0: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc noqueue state UP mode DEFAULT group default
    link/ether 02:42:3d:3f:7c:b9 brd ff:ff:ff:ff:ff:ff
6: veth2ce9e50@if5: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc noqueue master docker0 state UP mode DEFAULT group default
    link/ether a2:60:2c:40:4f:80 brd ff:ff:ff:ff:ff:ff link-netnsid 0
8: veth3f330be@if7: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc noqueue master docker0 state UP mode DEFAULT group default
    link/ether 96:af:af:62:72:58 brd ff:ff:ff:ff:ff:ff link-netnsid 1
[vagrant@docker-node1 ~]$ sudo ip netns exec test1 ip link
1: lo: <LOOPBACK,UP,LOWER_UP> mtu 65536 qdisc noqueue state UNKNOWN mode DEFAULT group default qlen 1000
    link/loopback 00:00:00:00:00:00 brd 00:00:00:00:00:00
10: veth-test1@if9: <BROADCAST,MULTICAST> mtu 1500 qdisc noop state DOWN mode DEFAULT group default qlen 1000
    link/ether ee:87:f9:14:f6:e2 brd ff:ff:ff:ff:ff:ff link-netnsid 1
[vagrant@docker-node1 ~]$ sudo ip netns exec test2 ip link
1: lo: <LOOPBACK> mtu 65536 qdisc noop state DOWN mode DEFAULT group default qlen 1000
    link/loopback 00:00:00:00:00:00 brd 00:00:00:00:00:00
9: veth-test2@if10: <BROADCAST,MULTICAST> mtu 1500 qdisc noop state DOWN mode DEFAULT group default qlen 1000
    link/ether 46:b4:0d:9b:18:fe brd ff:ff:ff:ff:ff:ff link-netnsid 0
[vagrant@docker-node1 ~]$
[vagrant@docker-node1 ~]$ sudo ip netns exec test1 ip addr add 192.168.1.1/24 dev veth-test1
[vagrant@docker-node1 ~]$ sudo ip netns exec test2 ip addr add 192.168.1.2/24 dev veth-test2
[vagrant@docker-node1 ~]$ sudo ip netns exec test1 ip link
1: lo: <LOOPBACK,UP,LOWER_UP> mtu 65536 qdisc noqueue state UNKNOWN mode DEFAULT group default qlen 1000
    link/loopback 00:00:00:00:00:00 brd 00:00:00:00:00:00
10: veth-test1@if9: <BROADCAST,MULTICAST> mtu 1500 qdisc noop state DOWN mode DEFAULT group default qlen 1000
    link/ether ee:87:f9:14:f6:e2 brd ff:ff:ff:ff:ff:ff link-netnsid 1
[vagrant@docker-node1 ~]$ sudo ip netns exec test2 ip link
1: lo: <LOOPBACK> mtu 65536 qdisc noop state DOWN mode DEFAULT group default qlen 1000
    link/loopback 00:00:00:00:00:00 brd 00:00:00:00:00:00
9: veth-test2@if10: <BROADCAST,MULTICAST> mtu 1500 qdisc noop state DOWN mode DEFAULT group default qlen 1000
    link/ether 46:b4:0d:9b:18:fe brd ff:ff:ff:ff:ff:ff link-netnsid 0
[vagrant@docker-node1 ~]$ sudo ip netns exec test1 ip link set dev veth-test1 up
[vagrant@docker-node1 ~]$ sudo ip netns exec test2 ip link set dev veth-test2 up
[vagrant@docker-node1 ~]$ sudo ip netns exec test1 ip link
1: lo: <LOOPBACK,UP,LOWER_UP> mtu 65536 qdisc noqueue state UNKNOWN mode DEFAULT group default qlen 1000
    link/loopback 00:00:00:00:00:00 brd 00:00:00:00:00:00
10: veth-test1@if9: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc noqueue state UP mode DEFAULT group default qlen 1000
    link/ether ee:87:f9:14:f6:e2 brd ff:ff:ff:ff:ff:ff link-netnsid 1
[vagrant@docker-node1 ~]$ sudo ip netns exec test2 ip link
1: lo: <LOOPBACK> mtu 65536 qdisc noop state DOWN mode DEFAULT group default qlen 1000
    link/loopback 00:00:00:00:00:00 brd 00:00:00:00:00:00
9: veth-test2@if10: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc noqueue state UP mode DEFAULT group default qlen 1000
    link/ether 46:b4:0d:9b:18:fe brd ff:ff:ff:ff:ff:ff link-netnsid 0
[vagrant@docker-node1 ~]$ sudo ip netns exec test1 ip a
1: lo: <LOOPBACK,UP,LOWER_UP> mtu 65536 qdisc noqueue state UNKNOWN group default qlen 1000
    link/loopback 00:00:00:00:00:00 brd 00:00:00:00:00:00
    inet 127.0.0.1/8 scope host lo
       valid_lft forever preferred_lft forever
    inet6 ::1/128 scope host
       valid_lft forever preferred_lft forever
10: veth-test1@if9: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc noqueue state UP group default qlen 1000
    link/ether ee:87:f9:14:f6:e2 brd ff:ff:ff:ff:ff:ff link-netnsid 1
    inet 192.168.1.1/24 scope global veth-test1
       valid_lft forever preferred_lft forever
    inet6 fe80::ec87:f9ff:fe14:f6e2/64 scope link
       valid_lft forever preferred_lft forever
[vagrant@docker-node1 ~]$ sudo ip netns exec test2 ip a
1: lo: <LOOPBACK> mtu 65536 qdisc noop state DOWN group default qlen 1000
    link/loopback 00:00:00:00:00:00 brd 00:00:00:00:00:00
9: veth-test2@if10: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc noqueue state UP group default qlen 1000
    link/ether 46:b4:0d:9b:18:fe brd ff:ff:ff:ff:ff:ff link-netnsid 0
    inet 192.168.1.2/24 scope global veth-test2
       valid_lft forever preferred_lft forever
    inet6 fe80::44b4:dff:fe9b:18fe/64 scope link
       valid_lft forever preferred_lft forever
[vagrant@docker-node1 ~]$
```

最终两个网络间可通讯

```shell
[vagrant@docker-node1 ~]$ sudo ip netns exec test1 ping 192.168.1.2
PING 192.168.1.2 (192.168.1.2) 56(84) bytes of data.
64 bytes from 192.168.1.2: icmp_seq=1 ttl=64 time=0.038 ms
64 bytes from 192.168.1.2: icmp_seq=2 ttl=64 time=0.026 ms
64 bytes from 192.168.1.2: icmp_seq=3 ttl=64 time=0.072 ms
64 bytes from 192.168.1.2: icmp_seq=4 ttl=64 time=0.026 ms
^C
--- 192.168.1.2 ping statistics ---
4 packets transmitted, 4 received, 0% packet loss, time 3000ms
rtt min/avg/max/mdev = 0.026/0.040/0.072/0.019 ms
[vagrant@docker-node1 ~]$ sudo ip netns exec test2 ping 192.168.1.1
PING 192.168.1.1 (192.168.1.1) 56(84) bytes of data.
64 bytes from 192.168.1.1: icmp_seq=1 ttl=64 time=0.022 ms
64 bytes from 192.168.1.1: icmp_seq=2 ttl=64 time=0.027 ms
64 bytes from 192.168.1.1: icmp_seq=3 ttl=64 time=0.027 ms
^C
--- 192.168.1.1 ping statistics ---
3 packets transmitted, 3 received, 0% packet loss, time 1999ms
rtt min/avg/max/mdev = 0.022/0.025/0.027/0.004 ms
[vagrant@docker-node1 ~]$
```

##### docker在创建容器时会自动创建network namespace

```shell
[vagrant@docker-node1 ~]$ docker ps
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS               NAMES
1c714971da1e        busybox             "/bin/sh -c 'while t…"   6 hours ago         Up 3 hours                              test2
abeece1257c0        busybox             "/bin/sh -c 'while t…"   6 hours ago         Up 3 hours                              test1
[vagrant@docker-node1 ~]$ docker exec -it test1 ip a
1: lo: <LOOPBACK,UP,LOWER_UP> mtu 65536 qdisc noqueue qlen 1000
    link/loopback 00:00:00:00:00:00 brd 00:00:00:00:00:00
    inet 127.0.0.1/8 scope host lo
       valid_lft forever preferred_lft forever
5: eth0@if6: <BROADCAST,MULTICAST,UP,LOWER_UP,M-DOWN> mtu 1500 qdisc noqueue
    link/ether 02:42:ac:11:00:02 brd ff:ff:ff:ff:ff:ff
    inet 172.17.0.2/16 brd 172.17.255.255 scope global eth0
       valid_lft forever preferred_lft forever
[vagrant@docker-node1 ~]$ docker exec -it test2 ip a
1: lo: <LOOPBACK,UP,LOWER_UP> mtu 65536 qdisc noqueue qlen 1000
    link/loopback 00:00:00:00:00:00 brd 00:00:00:00:00:00
    inet 127.0.0.1/8 scope host lo
       valid_lft forever preferred_lft forever
7: eth0@if8: <BROADCAST,MULTICAST,UP,LOWER_UP,M-DOWN> mtu 1500 qdisc noqueue
    link/ether 02:42:ac:11:00:03 brd ff:ff:ff:ff:ff:ff
    inet 172.17.0.3/16 brd 172.17.255.255 scope global eth0
       valid_lft forever preferred_lft forever
[vagrant@docker-node1 ~]$ docker exec -it test1 /bin/sh
/ # ping 172.17.0.2
PING 172.17.0.2 (172.17.0.2): 56 data bytes
64 bytes from 172.17.0.2: seq=0 ttl=64 time=0.039 ms
64 bytes from 172.17.0.2: seq=1 ttl=64 time=0.123 ms
64 bytes from 172.17.0.2: seq=2 ttl=64 time=0.050 ms
64 bytes from 172.17.0.2: seq=3 ttl=64 time=0.073 ms
^C
--- 172.17.0.2 ping statistics ---
4 packets transmitted, 4 packets received, 0% packet loss
round-trip min/avg/max = 0.039/0.071/0.123 ms
/ #
```

##### 查看docker网络

```shell
[vagrant@docker-node1 ~]$ docker rm $(docker ps -aq)
1c714971da1e
[vagrant@docker-node1 ~]$ docker ps -a
CONTAINER ID        IMAGE               COMMAND             CREATED             STATUS              PORTS               NAMES
[vagrant@docker-node1 ~]$ docker run -d --name test1 busybox /bin/sh -c "while true; do sleep 3600; done"
49b6b08b546b616f8e68d2abf8cd4f7cf4c2a2eb1d920201a6da554061ccf0c4
[vagrant@docker-node1 ~]$ docker ps
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS               NAMES
49b6b08b546b        busybox             "/bin/sh -c 'while t…"   3 seconds ago       Up 3 seconds                            test1
[vagrant@docker-node1 ~]$ docker network ls
NETWORK ID          NAME                DRIVER              SCOPE
5f271e1e9bc0        bridge              bridge              local
bfc01559a8b4        host                host                local
0a69639b327e        none                null                local
[vagrant@docker-node1 ~]$ docker network inspect 5f271e1e9bc0
[
    {
        "Name": "bridge",
        "Id": "5f271e1e9bc0866033d862618cf665be37abbdf1647d4151be10fd5eb963c4be",
        "Created": "2018-09-08T09:43:16.450594087Z",
        "Scope": "local",
        "Driver": "bridge",
        "EnableIPv6": false,
        "IPAM": {
            "Driver": "default",
            "Options": null,
            "Config": [
                {
                    "Subnet": "172.17.0.0/16",
                    "Gateway": "172.17.0.1"
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
            "49b6b08b546b616f8e68d2abf8cd4f7cf4c2a2eb1d920201a6da554061ccf0c4": {
                "Name": "test1",
                "EndpointID": "e23014e051f01c650963d98fb8015770ba25163a9313bdaecc877eff95aedec9",
                "MacAddress": "02:42:ac:11:00:02",
                "IPv4Address": "172.17.0.2/16",
                "IPv6Address": ""
            }
        },
        "Options": {
            "com.docker.network.bridge.default_bridge": "true",
            "com.docker.network.bridge.enable_icc": "true",
            "com.docker.network.bridge.enable_ip_masquerade": "true",
            "com.docker.network.bridge.host_binding_ipv4": "0.0.0.0",
            "com.docker.network.bridge.name": "docker0",
            "com.docker.network.driver.mtu": "1500"
        },
        "Labels": {}
    }
]
[vagrant@docker-node1 ~]$
```

##### 查看docker网卡连接情况

```shell
[vagrant@docker-node1 ~]$ brctl show
bridge name     bridge id               STP enabled     interfaces
docker0         8000.0242bc13258a       no              vethc9ef079
[vagrant@docker-node1 ~]$ ip a
1: lo: <LOOPBACK,UP,LOWER_UP> mtu 65536 qdisc noqueue state UNKNOWN group default qlen 1000
    link/loopback 00:00:00:00:00:00 brd 00:00:00:00:00:00
    inet 127.0.0.1/8 scope host lo
       valid_lft forever preferred_lft forever
    inet6 ::1/128 scope host
       valid_lft forever preferred_lft forever
2: eth0: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc pfifo_fast state UP group default qlen 1000
    link/ether 52:54:00:c9:c7:04 brd ff:ff:ff:ff:ff:ff
    inet 10.0.2.15/24 brd 10.0.2.255 scope global noprefixroute dynamic eth0
       valid_lft 83822sec preferred_lft 83822sec
    inet6 fe80::5054:ff:fec9:c704/64 scope link
       valid_lft forever preferred_lft forever
3: eth1: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc pfifo_fast state UP group default qlen 1000
    link/ether 08:00:27:8f:d9:3b brd ff:ff:ff:ff:ff:ff
    inet 192.168.205.10/24 brd 192.168.205.255 scope global noprefixroute eth1
       valid_lft forever preferred_lft forever
    inet6 fe80::a00:27ff:fe8f:d93b/64 scope link
       valid_lft forever preferred_lft forever
4: docker0: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc noqueue state UP group default
    link/ether 02:42:bc:13:25:8a brd ff:ff:ff:ff:ff:ff
    inet 172.17.0.1/16 brd 172.17.255.255 scope global docker0
       valid_lft forever preferred_lft forever
    inet6 fe80::42:bcff:fe13:258a/64 scope link
       valid_lft forever preferred_lft forever
6: vethc9ef079@if5: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc noqueue master docker0 state UP group default
    link/ether 66:69:65:f3:d1:27 brd ff:ff:ff:ff:ff:ff link-netnsid 0
    inet6 fe80::6469:65ff:fef3:d127/64 scope link
       valid_lft forever preferred_lft forever
[vagrant@docker-node1 ~]$
```

##### 再运行一个容器查看组网情况

```shell
[vagrant@docker-node1 ~]$ ip adocker run -d --name test2 busybox /bin/sh -c "while true; do sleep 3600; done"
79c8365b41eb7cbd5305c4b6728d778ec423179eb29970a2eb4da0e373b5e456
[vagrant@docker-node1 ~]$ ip a
1: lo: <LOOPBACK,UP,LOWER_UP> mtu 65536 qdisc noqueue state UNKNOWN group default qlen 1000
    link/loopback 00:00:00:00:00:00 brd 00:00:00:00:00:00
    inet 127.0.0.1/8 scope host lo
       valid_lft forever preferred_lft forever
    inet6 ::1/128 scope host
       valid_lft forever preferred_lft forever
2: eth0: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc pfifo_fast state UP group default qlen 1000
    link/ether 52:54:00:c9:c7:04 brd ff:ff:ff:ff:ff:ff
    inet 10.0.2.15/24 brd 10.0.2.255 scope global noprefixroute dynamic eth0
       valid_lft 83534sec preferred_lft 83534sec
    inet6 fe80::5054:ff:fec9:c704/64 scope link
       valid_lft forever preferred_lft forever
3: eth1: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc pfifo_fast state UP group default qlen 1000
    link/ether 08:00:27:8f:d9:3b brd ff:ff:ff:ff:ff:ff
    inet 192.168.205.10/24 brd 192.168.205.255 scope global noprefixroute eth1
       valid_lft forever preferred_lft forever
    inet6 fe80::a00:27ff:fe8f:d93b/64 scope link
       valid_lft forever preferred_lft forever
4: docker0: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc noqueue state UP group default
    link/ether 02:42:bc:13:25:8a brd ff:ff:ff:ff:ff:ff
    inet 172.17.0.1/16 brd 172.17.255.255 scope global docker0
       valid_lft forever preferred_lft forever
    inet6 fe80::42:bcff:fe13:258a/64 scope link
       valid_lft forever preferred_lft forever
6: vethc9ef079@if5: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc noqueue master docker0 state UP group default
    link/ether 66:69:65:f3:d1:27 brd ff:ff:ff:ff:ff:ff link-netnsid 0
    inet6 fe80::6469:65ff:fef3:d127/64 scope link
       valid_lft forever preferred_lft forever
8: veth6fb966a@if7: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc noqueue master docker0 state UP group default
    link/ether 3e:6f:ed:72:4e:2a brd ff:ff:ff:ff:ff:ff link-netnsid 1
    inet6 fe80::3c6f:edff:fe72:4e2a/64 scope link
       valid_lft forever preferred_lft forever
[vagrant@docker-node1 ~]$ brctl show
bridge name     bridge id               STP enabled     interfaces
docker0         8000.0242bc13258a       no              veth6fb966a
                                                        vethc9ef079
[vagrant@docker-node1 ~]$
```

从结果看出：两个容器的网卡连接到docker0组成局域网。需要访问外网时，通过docker0的net地址转换进行访问。

##### 容器间以link方式组网

```shell
[vagrant@docker-node1 ~]$ docker ps
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS               NAMES
49b6b08b546b        busybox             "/bin/sh -c 'while t…"   About an hour ago   Up 3 seconds                            test1
[vagrant@docker-node1 ~]$ docker run -d --name test2 --link test1 busybox /bin/sh -c "while true; do sleep 3600; done"
a6f66e89a235efb8793e5918c2866581072def3b660647e264386f39198b97a9
[vagrant@docker-node1 ~]$ docker exec -it test2 /bin/sh
/ # ip a
1: lo: <LOOPBACK,UP,LOWER_UP> mtu 65536 qdisc noqueue qlen 1000
    link/loopback 00:00:00:00:00:00 brd 00:00:00:00:00:00
    inet 127.0.0.1/8 scope host lo
       valid_lft forever preferred_lft forever
7: eth0@if8: <BROADCAST,MULTICAST,UP,LOWER_UP,M-DOWN> mtu 1500 qdisc noqueue
    link/ether 02:42:ac:11:00:03 brd ff:ff:ff:ff:ff:ff
    inet 172.17.0.3/16 brd 172.17.255.255 scope global eth0
       valid_lft forever preferred_lft forever
/ # ping test1
PING test1 (172.17.0.2): 56 data bytes
64 bytes from 172.17.0.2: seq=0 ttl=64 time=0.098 ms
64 bytes from 172.17.0.2: seq=1 ttl=64 time=0.052 ms
64 bytes from 172.17.0.2: seq=2 ttl=64 time=0.048 ms
64 bytes from 172.17.0.2: seq=3 ttl=64 time=0.048 ms
^C
--- test1 ping statistics ---
4 packets transmitted, 4 packets received, 0% packet loss
round-trip min/avg/max = 0.048/0.061/0.098 ms
/ #
```

##### docker创建容器时默认连接bridge

```shell
[vagrant@docker-node1 ~]$ docker network ls
NETWORK ID          NAME                DRIVER              SCOPE
3102bfadb806        bridge              bridge              local
bfc01559a8b4        host                host                local
0a69639b327e        none                null                local
[vagrant@docker-node1 ~]$
```

##### 清空link网络环境

```shell
[vagrant@docker-node1 ~]$ docker stop test2 && docker rm test2
test2
test2
[vagrant@docker-node1 ~]$
[vagrant@docker-node1 ~]$ docker run -d --name test2 busybox /bin/sh -c "while true; do sleep 3600; done"
58dda37b0a11bb81165c629e486a66baf10c3bcccdd392137aea6edef78a90fb
[vagrant@docker-node1 ~]$ docker ps
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS               NAMES
58dda37b0a11        busybox             "/bin/sh -c 'while t…"   4 seconds ago       Up 3 seconds                            test2
49b6b08b546b        busybox             "/bin/sh -c 'while t…"   2 hours ago         Up 20 minutes                           test1
[vagrant@docker-node1 ~]$
```

##### 创建bridge网络

```shell
[vagrant@docker-node1 ~]$ docker network create -d bridge mybridge
33094d82a5122cad662dddf0af82d814b15da61dc65cdf42e4cf5ebe7644245d
[vagrant@docker-node1 ~]$ docker network ls
NETWORK ID          NAME                DRIVER              SCOPE
3102bfadb806        bridge              bridge              local
bfc01559a8b4        host                host                local
33094d82a512        mybridge            bridge              local
0a69639b327e        none                null                local
[vagrant@docker-node1 ~]$
[vagrant@docker-node1 ~]$ brctl show
bridge name     bridge id               STP enabled     interfaces
br-33094d82a512         8000.0242da527b95       no
docker0         8000.02428f84de1d       no              veth51d1795
                                                        veth5f18183
[vagrant@docker-node1 ~]$
```

##### 创建容器指定网络

```shell
[vagrant@docker-node1 ~]$ docker run -d --name test3 --network mybridge busybox /bin/sh -c "while true; do sleep 3600; d
one"
84ebce13d5fcb502abadb639a2cd30c1fb3c8811f3877d2962afb6b52e993f6d
[vagrant@docker-node1 ~]$ docker ps
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS               NAMES
84ebce13d5fc        busybox             "/bin/sh -c 'while t…"   3 seconds ago       Up 2 seconds                            test3
58dda37b0a11        busybox             "/bin/sh -c 'while t…"   8 minutes ago       Up 8 minutes                            test2
49b6b08b546b        busybox             "/bin/sh -c 'while t…"   2 hours ago         Up 28 minutes                           test1
[vagrant@docker-node1 ~]$ brctl show
bridge name     bridge id               STP enabled     interfaces
br-33094d82a512         8000.0242da527b95       no              vethe9e76dd
docker0         8000.02428f84de1d       no              veth51d1795
                                                        veth5f18183
[vagrant@docker-node1 ~]$
[vagrant@docker-node1 ~]$ docker network inspect mybridge
[
    {
        "Name": "mybridge",
        "Id": "33094d82a5122cad662dddf0af82d814b15da61dc65cdf42e4cf5ebe7644245d",
        "Created": "2018-09-08T11:57:01.844962433Z",
        "Scope": "local",
        "Driver": "bridge",
        "EnableIPv6": false,
        "IPAM": {
            "Driver": "default",
            "Options": {},
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
            "84ebce13d5fcb502abadb639a2cd30c1fb3c8811f3877d2962afb6b52e993f6d": {
                "Name": "test3",
                "EndpointID": "9a89a9ba195a803e3de0ee499744caff382cf46cb63caf6d53f98e166aef3b98",
                "MacAddress": "02:42:ac:12:00:02",
                "IPv4Address": "172.18.0.2/16",
                "IPv6Address": ""
            }
        },
        "Options": {},
        "Labels": {}
    }
]
[vagrant@docker-node1 ~]$
```

##### 将运行中的容器连接到指定网络

```shell
[vagrant@docker-node1 ~]$ docker network connect mybridge test2
[vagrant@docker-node1 ~]$ docker network inspect mybridge
[
    {
        "Name": "mybridge",
        "Id": "33094d82a5122cad662dddf0af82d814b15da61dc65cdf42e4cf5ebe7644245d",
        "Created": "2018-09-08T11:57:01.844962433Z",
        "Scope": "local",
        "Driver": "bridge",
        "EnableIPv6": false,
        "IPAM": {
            "Driver": "default",
            "Options": {},
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
            "58dda37b0a11bb81165c629e486a66baf10c3bcccdd392137aea6edef78a90fb": {
                "Name": "test2",
                "EndpointID": "978faec9008a41e8a4378ffc4a6920f154b7dd73cbb9ff0f7959d7cb7656ddf5",
                "MacAddress": "02:42:ac:12:00:03",
                "IPv4Address": "172.18.0.3/16",
                "IPv6Address": ""
            },
            "84ebce13d5fcb502abadb639a2cd30c1fb3c8811f3877d2962afb6b52e993f6d": {
                "Name": "test3",
                "EndpointID": "9a89a9ba195a803e3de0ee499744caff382cf46cb63caf6d53f98e166aef3b98",
                "MacAddress": "02:42:ac:12:00:02",
                "IPv4Address": "172.18.0.2/16",
                "IPv6Address": ""
            }
        },
        "Options": {},
        "Labels": {}
    }
]
[vagrant@docker-node1 ~]$
[vagrant@docker-node1 ~]$ docker ps
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS               NAMES
84ebce13d5fc        busybox             "/bin/sh -c 'while t…"   6 minutes ago       Up 6 minutes                            test3
58dda37b0a11        busybox             "/bin/sh -c 'while t…"   14 minutes ago      Up 14 minutes                           test2
49b6b08b546b        busybox             "/bin/sh -c 'while t…"   2 hours ago         Up 35 minutes                           test1
[vagrant@docker-node1 ~]$ docker exec -it 84ebce13d5fc /bin/sh
/ # ping test2
PING test2 (172.18.0.3): 56 data bytes
64 bytes from 172.18.0.3: seq=0 ttl=64 time=0.067 ms
64 bytes from 172.18.0.3: seq=1 ttl=64 time=0.045 ms
64 bytes from 172.18.0.3: seq=2 ttl=64 time=0.051 ms
64 bytes from 172.18.0.3: seq=3 ttl=64 time=0.050 ms
^C
--- test2 ping statistics ---
4 packets transmitted, 4 packets received, 0% packet loss
round-trip min/avg/max = 0.045/0.053/0.067 ms
/ #exit
[vagrant@docker-node1 ~]$ docker ps
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS               NAMES
84ebce13d5fc        busybox             "/bin/sh -c 'while t…"   10 minutes ago      Up 10 minutes                           test3
58dda37b0a11        busybox             "/bin/sh -c 'while t…"   18 minutes ago      Up 18 minutes                           test2
49b6b08b546b        busybox             "/bin/sh -c 'while t…"   2 hours ago         Up 38 minutes                           test1
[vagrant@docker-node1 ~]$ docker exec -it test2 /bin/sh
/ # ping test3
PING test3 (172.18.0.2): 56 data bytes
64 bytes from 172.18.0.2: seq=0 ttl=64 time=0.046 ms
64 bytes from 172.18.0.2: seq=1 ttl=64 time=0.157 ms
64 bytes from 172.18.0.2: seq=2 ttl=64 time=0.050 ms
^C
--- test3 ping statistics ---
3 packets transmitted, 3 packets received, 0% packet loss
round-trip min/avg/max = 0.046/0.084/0.157 ms
/ # ping test1
ping: bad address 'test1'
/ # exit
[vagrant@docker-node1 ~]$ docker network connect mybridge test1
[vagrant@docker-node1 ~]$ docker exec -it test2 /bin/sh
/ # ping test1
PING test1 (172.18.0.4): 56 data bytes
64 bytes from 172.18.0.4: seq=0 ttl=64 time=0.057 ms
64 bytes from 172.18.0.4: seq=1 ttl=64 time=0.048 ms
64 bytes from 172.18.0.4: seq=2 ttl=64 time=0.050 ms
^C
--- test1 ping statistics ---
3 packets transmitted, 3 packets received, 0% packet loss
round-trip min/avg/max = 0.048/0.051/0.057 ms
/ # ip a
1: lo: <LOOPBACK,UP,LOWER_UP> mtu 65536 qdisc noqueue qlen 1000
    link/loopback 00:00:00:00:00:00 brd 00:00:00:00:00:00
    inet 127.0.0.1/8 scope host lo
       valid_lft forever preferred_lft forever
9: eth0@if10: <BROADCAST,MULTICAST,UP,LOWER_UP,M-DOWN> mtu 1500 qdisc noqueue
    link/ether 02:42:ac:11:00:03 brd ff:ff:ff:ff:ff:ff
    inet 172.17.0.3/16 brd 172.17.255.255 scope global eth0
       valid_lft forever preferred_lft forever
16: eth1@if17: <BROADCAST,MULTICAST,UP,LOWER_UP,M-DOWN> mtu 1500 qdisc noqueue
    link/ether 02:42:ac:12:00:03 brd ff:ff:ff:ff:ff:ff
    inet 172.18.0.3/16 brd 172.18.255.255 scope global eth1
       valid_lft forever preferred_lft forever
/ #
```

##### 容器连接到none和host网络

```shell
[vagrant@docker-node1 ~]$ docker ps -a
CONTAINER ID        IMAGE               COMMAND             CREATED             STATUS              PORTS               NAMES
[vagrant@docker-node1 ~]$ docker run -d --name test1 --network none busybox /bin/sh -c "while true; do sleep 3600; done"

2573a35e027eea9e8b0d046d1b7c71e114eab37a6a6f476f8030d5f7165fc1b0
[vagrant@docker-node1 ~]$ docker run -d --name test2 --network host busybox /bin/sh -c "while true; do sleep 3600; done"

83d6d6a54c0ef475de080a65949ca664b7c8e40ac2770d4618114c683cd06bf0
[vagrant@docker-node1 ~]$ docker ps
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS               NAMES
83d6d6a54c0e        busybox             "/bin/sh -c 'while t…"   3 seconds ago       Up 2 seconds                            test2
2573a35e027e        busybox             "/bin/sh -c 'while t…"   19 seconds ago      Up 18 seconds                           test1
[vagrant@docker-node1 ~]$ docker network ls
NETWORK ID          NAME                DRIVER              SCOPE
3102bfadb806        bridge              bridge              local
bfc01559a8b4        host                host                local
33094d82a512        mybridge            bridge              local
0a69639b327e        none                null                local
[vagrant@docker-node1 ~]$ docker network inspect none
[
    {
        "Name": "none",
        "Id": "0a69639b327eae6c9975be167273b21d71ba1664a894d3e050f1ffd5f35376c7",
        "Created": "2018-09-07T12:45:44.700945934Z",
        "Scope": "local",
        "Driver": "null",
        "EnableIPv6": false,
        "IPAM": {
            "Driver": "default",
            "Options": null,
            "Config": []
        },
        "Internal": false,
        "Attachable": false,
        "Ingress": false,
        "ConfigFrom": {
            "Network": ""
        },
        "ConfigOnly": false,
        "Containers": {
            "2573a35e027eea9e8b0d046d1b7c71e114eab37a6a6f476f8030d5f7165fc1b0": {
                "Name": "test1",
                "EndpointID": "c6c32c29e379f4926ab3203c231ef2f850699de503a17b5869b191ff11b0d2dd",
                "MacAddress": "",
                "IPv4Address": "",
                "IPv6Address": ""
            }
        },
        "Options": {},
        "Labels": {}
    }
]
[vagrant@docker-node1 ~]$ docker network inspect host
[
    {
        "Name": "host",
        "Id": "bfc01559a8b46f6a74226fd22a8dd17b13e1e3e61d71860fa971246ece82cffa",
        "Created": "2018-09-07T12:45:44.714559875Z",
        "Scope": "local",
        "Driver": "host",
        "EnableIPv6": false,
        "IPAM": {
            "Driver": "default",
            "Options": null,
            "Config": []
        },
        "Internal": false,
        "Attachable": false,
        "Ingress": false,
        "ConfigFrom": {
            "Network": ""
        },
        "ConfigOnly": false,
        "Containers": {
            "83d6d6a54c0ef475de080a65949ca664b7c8e40ac2770d4618114c683cd06bf0": {
                "Name": "test2",
                "EndpointID": "e77e462575c4655ee9af6c6b880a515225e591887c58fae5ac44f60954f56f13",
                "MacAddress": "",
                "IPv4Address": "",
                "IPv6Address": ""
            }
        },
        "Options": {},
        "Labels": {}
    }
]
[vagrant@docker-node1 ~]$ docker ps
CONTAINER ID        IMAGE               COMMAND                  CREATED              STATUS              PORTS               NAMES
83d6d6a54c0e        busybox             "/bin/sh -c 'while t…"   About a minute ago   Up About a minute                       test2
2573a35e027e        busybox             "/bin/sh -c 'while t…"   2 minutes ago        Up 2 minutes                            test1
[vagrant@docker-node1 ~]$ docker exec -it test1 /bin/sh
/ # ip a
1: lo: <LOOPBACK,UP,LOWER_UP> mtu 65536 qdisc noqueue qlen 1000
    link/loopback 00:00:00:00:00:00 brd 00:00:00:00:00:00
    inet 127.0.0.1/8 scope host lo
       valid_lft forever preferred_lft forever
/ # exit
[vagrant@docker-node1 ~]$ docker exec -it test2 /bin/sh
/ # ip a
1: lo: <LOOPBACK,UP,LOWER_UP> mtu 65536 qdisc noqueue qlen 1000
    link/loopback 00:00:00:00:00:00 brd 00:00:00:00:00:00
    inet 127.0.0.1/8 scope host lo
       valid_lft forever preferred_lft forever
    inet6 ::1/128 scope host
       valid_lft forever preferred_lft forever
2: eth0: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc pfifo_fast qlen 1000
    link/ether 52:54:00:c9:c7:04 brd ff:ff:ff:ff:ff:ff
    inet 10.0.2.15/24 brd 10.0.2.255 scope global dynamic eth0
       valid_lft 79792sec preferred_lft 79792sec
    inet6 fe80::5054:ff:fec9:c704/64 scope link
       valid_lft forever preferred_lft forever
3: eth1: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc pfifo_fast qlen 1000
    link/ether 08:00:27:8f:d9:3b brd ff:ff:ff:ff:ff:ff
    inet 192.168.205.10/24 brd 192.168.205.255 scope global eth1
       valid_lft forever preferred_lft forever
    inet6 fe80::a00:27ff:fe8f:d93b/64 scope link
       valid_lft forever preferred_lft forever
4: docker0: <NO-CARRIER,BROADCAST,MULTICAST,UP> mtu 1500 qdisc noqueue
    link/ether 02:42:8f:84:de:1d brd ff:ff:ff:ff:ff:ff
    inet 172.17.0.1/16 brd 172.17.255.255 scope global docker0
       valid_lft forever preferred_lft forever
    inet6 fe80::42:8fff:fe84:de1d/64 scope link
       valid_lft forever preferred_lft forever
11: br-33094d82a512: <NO-CARRIER,BROADCAST,MULTICAST,UP> mtu 1500 qdisc noqueue
    link/ether 02:42:da:52:7b:95 brd ff:ff:ff:ff:ff:ff
    inet 172.18.0.1/16 brd 172.18.255.255 scope global br-33094d82a512
       valid_lft forever preferred_lft forever
    inet6 fe80::42:daff:fe52:7b95/64 scope link
       valid_lft forever preferred_lft forever
/ # exit
[vagrant@docker-node1 ~]$ ip a
1: lo: <LOOPBACK,UP,LOWER_UP> mtu 65536 qdisc noqueue state UNKNOWN group default qlen 1000
    link/loopback 00:00:00:00:00:00 brd 00:00:00:00:00:00
    inet 127.0.0.1/8 scope host lo
       valid_lft forever preferred_lft forever
    inet6 ::1/128 scope host
       valid_lft forever preferred_lft forever
2: eth0: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc pfifo_fast state UP group default qlen 1000
    link/ether 52:54:00:c9:c7:04 brd ff:ff:ff:ff:ff:ff
    inet 10.0.2.15/24 brd 10.0.2.255 scope global noprefixroute dynamic eth0
       valid_lft 79788sec preferred_lft 79788sec
    inet6 fe80::5054:ff:fec9:c704/64 scope link
       valid_lft forever preferred_lft forever
3: eth1: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc pfifo_fast state UP group default qlen 1000
    link/ether 08:00:27:8f:d9:3b brd ff:ff:ff:ff:ff:ff
    inet 192.168.205.10/24 brd 192.168.205.255 scope global noprefixroute eth1
       valid_lft forever preferred_lft forever
    inet6 fe80::a00:27ff:fe8f:d93b/64 scope link
       valid_lft forever preferred_lft forever
4: docker0: <NO-CARRIER,BROADCAST,MULTICAST,UP> mtu 1500 qdisc noqueue state DOWN group default
    link/ether 02:42:8f:84:de:1d brd ff:ff:ff:ff:ff:ff
    inet 172.17.0.1/16 brd 172.17.255.255 scope global docker0
       valid_lft forever preferred_lft forever
    inet6 fe80::42:8fff:fe84:de1d/64 scope link
       valid_lft forever preferred_lft forever
11: br-33094d82a512: <NO-CARRIER,BROADCAST,MULTICAST,UP> mtu 1500 qdisc noqueue state DOWN group default
    link/ether 02:42:da:52:7b:95 brd ff:ff:ff:ff:ff:ff
    inet 172.18.0.1/16 brd 172.18.255.255 scope global br-33094d82a512
       valid_lft forever preferred_lft forever
    inet6 fe80::42:daff:fe52:7b95/64 scope link
       valid_lft forever preferred_lft forever
[vagrant@docker-node1 ~]$
```

##### flask-redis示例

```shell
docker run -d --name redis redis
docker build -t flask-redis .
docker run -d --link redis --name flask-redis -e REDIS_HOST=redis flask-redis
```

查看网络

```shell
[vagrant@docker-node1 ~]$ docker network ls
NETWORK ID          NAME                DRIVER              SCOPE
3102bfadb806        bridge              bridge              local
bfc01559a8b4        host                host                local
33094d82a512        mybridge            bridge              local
0a69639b327e        none                null                local
[vagrant@docker-node1 ~]$ docker ps
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS               NAMES
44cb416adf23        flask-redis         "python app.py"          30 minutes ago      Up 30 minutes       5000/tcp            flask-redis
4ba095fb90dc        redis               "docker-entrypoint.s…"   30 minutes ago      Up 30 minutes       6379/tcp            redis
83d6d6a54c0e        busybox             "/bin/sh -c 'while t…"   2 hours ago         Up 2 hours                              test2
2573a35e027e        busybox             "/bin/sh -c 'while t…"   2 hours ago         Up 2 hours                              test1
[vagrant@docker-node1 ~]$ docker network inspect bridge
[
    {
        "Name": "bridge",
        "Id": "3102bfadb80629aaeffafdfb7506a71cb3084cd8a32bc052b12b8504bac4a5d6",
        "Created": "2018-09-08T11:29:08.182233064Z",
        "Scope": "local",
        "Driver": "bridge",
        "EnableIPv6": false,
        "IPAM": {
            "Driver": "default",
            "Options": null,
            "Config": [
                {
                    "Subnet": "172.17.0.0/16",
                    "Gateway": "172.17.0.1"
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
            "44cb416adf233638e0328a22339227a4a332272f50fe5bdc63b50ef6d7722115": {
                "Name": "flask-redis",
                "EndpointID": "be41aad95ca1375fdf8ba894778ac2fe08285275c0bb6657e9d2a3cbb2fd097a",
                "MacAddress": "02:42:ac:11:00:03",
                "IPv4Address": "172.17.0.3/16",
                "IPv6Address": ""
            },
            "4ba095fb90dcc3f62fee44d9c9d87230f2da7ad3a3a9a398b1864afc0b186ac9": {
                "Name": "redis",
                "EndpointID": "8e45e4960234d138bf48d4a901e70201d1b25891a2723501720bb15eafb8a9ba",
                "MacAddress": "02:42:ac:11:00:02",
                "IPv4Address": "172.17.0.2/16",
                "IPv6Address": ""
            }
        },
        "Options": {
            "com.docker.network.bridge.default_bridge": "true",
            "com.docker.network.bridge.enable_icc": "true",
            "com.docker.network.bridge.enable_ip_masquerade": "true",
            "com.docker.network.bridge.host_binding_ipv4": "0.0.0.0",
            "com.docker.network.bridge.name": "docker0",
            "com.docker.network.driver.mtu": "1500"
        },
        "Labels": {}
    }
]
[vagrant@docker-node1 ~]$
```

得知flask-redis容器ip为172.17.0.3，用curl访问可得结果

```shell
[vagrant@docker-node1 ~]$ curl 172.17.0.3:5000
Hello Container World! I have been seen 1 times and my hostname is 44cb416adf23.
[vagrant@docker-node1 ~]$
```

##### docker容器传递参数到环境变量和查看环境变量

```shell
[vagrant@docker-node1 ~]$ docker run -d --name test1 -e KEY=VALUE busybox /bin/sh -c "while true; do sleep 3600; done"
703bcef751f8d3acd2aaa0a4cc3f7dfaf6ce3eb5836350b0d176a5aadd196808
[vagrant@docker-node1 ~]$ docker ps
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS               NAMES
703bcef751f8        busybox             "/bin/sh -c 'while t…"   5 seconds ago       Up 4 seconds                            test1
44cb416adf23        flask-redis         "python app.py"          About an hour ago   Up About an hour    5000/tcp            flask-redis
4ba095fb90dc        redis               "docker-entrypoint.s…"   About an hour ago   Up About an hour    6379/tcp            redis
[vagrant@docker-node1 ~]$ docker exec -it test1 /bin/sh
/ # env
HOSTNAME=703bcef751f8
SHLVL=1
HOME=/root
TERM=xterm
PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin
KEY=VALUE
PWD=/
/ #
```

