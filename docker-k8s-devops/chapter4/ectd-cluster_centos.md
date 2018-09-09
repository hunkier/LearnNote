# [Centos7下Etcd集群搭建](https://www.cnblogs.com/zhenyuyaodidiao/p/6237019.html)



**一、简介**

　　“A highly-available key value store for shared configuration and service discovery.”
　　Etcd是coreos开发的分布式服务系统，内部采用raft协议作为一致性算法。作为一个高可用的配置共享、服务发现的键值存储系统，Etcd有以下的特点：
　　　　1）简单：安装配置简单，而且提供了 HTTP API 进行交互，使用也很简单
　　　　2）安全：支持 SSL 证书验证
　　　　3）快速：根据官方提供的数据，单实例支持每秒2k+读操作、1k写操作
　　　　4）可靠：采用raft算法，实现分布式系统数据的可用性和一致性
　　Etcd构建自身高可用集群主要有三种形式:
　　　　1）静态发现: 预先已知 Etcd 集群中有哪些节点，在启动时直接指定好Etcd的各个node节点地址
　　　　2）Etcd动态发现: 通过已有的Etcd集群作为数据交互点，然后在扩展新的集群时实现通过已有集群进行服务发现的机制
　　　　3）DNS动态发现: 通过DNS查询方式获取其他节点地址信息
　　本文主要介绍第一种方式，后续会陆续介绍剩下的两种方式。(直接docker安装请移步：[quay.io/coreos/etcd 基于Docker镜像的集群搭建](http://www.cnblogs.com/zhenyuyaodidiao/p/5311945.html))

**二、环境介绍**

　　三台虚拟机，系统环境均为Centos7，对应节点名称及IP地址如下：
　　　　node1:192.168.7.163
　　　　node2:192.168.7.57
　　　　etcd2:192.168.7.58

　　首先将这个信息添加到三台主机的hosts文件中，编辑/etc/hosts，填入以下信息：

```
192.168.7.163  node1
192.168.7.57  node2
192.168.7.58  etcd2
```

**三、安装、配置Etcd**

```
# yum install etcd -y
```

yum安装的etcd默认配置文件在/etc/etcd/etcd.conf，以下将三个节点上的配置贴出来，请注意不同点（未贴出的，则表明不需要更改）

node1

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
# [member]
# 节点名称
ETCD_NAME=node1
# 数据存放位置
ETCD_DATA_DIR="/var/lib/etcd/default.etcd"
#ETCD_WAL_DIR=""
#ETCD_SNAPSHOT_COUNT="10000"
#ETCD_HEARTBEAT_INTERVAL="100"
#ETCD_ELECTION_TIMEOUT="1000"
# 监听其他 Etcd 实例的地址
ETCD_LISTEN_PEER_URLS="http://0.0.0.0:2380"
# 监听客户端地址
ETCD_LISTEN_CLIENT_URLS="http://0.0.0.0:2379,http://0.0.0.0:4001"
#ETCD_MAX_SNAPSHOTS="5"
#ETCD_MAX_WALS="5"
#ETCD_CORS=""
#
#[cluster]
# 通知其他 Etcd 实例地址
ETCD_INITIAL_ADVERTISE_PEER_URLS="http://node1:2380"
# if you use different ETCD_NAME (e.g. test), set ETCD_INITIAL_CLUSTER value for this name, i.e. "test=http://..."
# 初始化集群内节点地址
ETCD_INITIAL_CLUSTER="node1=http://node1:2380,node2=http://node2:2380,etcd2=http://etcd2:2380"
# 初始化集群状态，new 表示新建
ETCD_INITIAL_CLUSTER_STATE="new"
# 初始化集群 token
ETCD_INITIAL_CLUSTER_TOKEN="mritd-etcd-cluster"
# 通知 客户端地址
ETCD_ADVERTISE_CLIENT_URLS="http://node1:2379,http://node1:4001"
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

node2

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
# [member]
ETCD_NAME=node2
ETCD_DATA_DIR="/var/lib/etcd/default.etcd"
#ETCD_WAL_DIR=""
#ETCD_SNAPSHOT_COUNT="10000"
#ETCD_HEARTBEAT_INTERVAL="100"
#ETCD_ELECTION_TIMEOUT="1000"
ETCD_LISTEN_PEER_URLS="http://0.0.0.0:2380"
ETCD_LISTEN_CLIENT_URLS="http://0.0.0.0:2379,http://0.0.0.0:4001"
#ETCD_MAX_SNAPSHOTS="5"
#ETCD_MAX_WALS="5"
#ETCD_CORS=""
#
#[cluster]

ETCD_INITIAL_ADVERTISE_PEER_URLS="http://node2:2380"
# if you use different ETCD_NAME (e.g. test), set ETCD_INITIAL_CLUSTER value for this name, i.e. "test=http://..."
ETCD_INITIAL_CLUSTER="node1=http://node1:2380,node2=http://node2:2380,etcd2=http://etcd2:2380"
ETCD_INITIAL_CLUSTER_STATE="new"
ETCD_INITIAL_CLUSTER_TOKEN="mritd-etcd-cluster"
ETCD_ADVERTISE_CLIENT_URLS="http://node2:2379,http://node2:4001"
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

etcd2

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
# [member]
ETCD_NAME=etcd2
ETCD_DATA_DIR="/var/lib/etcd/default.etcd"
#ETCD_WAL_DIR=""
#ETCD_SNAPSHOT_COUNT="10000"
#ETCD_HEARTBEAT_INTERVAL="100"
#ETCD_ELECTION_TIMEOUT="1000"
ETCD_LISTEN_PEER_URLS="http://0.0.0.0:2380"
ETCD_LISTEN_CLIENT_URLS="http://0.0.0.0:2379,http://0.0.0.0:4001"
#ETCD_MAX_SNAPSHOTS="5"
#ETCD_MAX_WALS="5"
#ETCD_CORS=""
#
#[cluster]
ETCD_INITIAL_ADVERTISE_PEER_URLS="http://etcd2:2380"
# if you use different ETCD_NAME (e.g. test), set ETCD_INITIAL_CLUSTER value for this name, i.e. "test=http://..."
ETCD_INITIAL_CLUSTER="node1=http://node1:2380,node2=http://node2:2380,etcd2=http://etcd2:2380"
ETCD_INITIAL_CLUSTER_STATE="new"
ETCD_INITIAL_CLUSTER_TOKEN="mritd-etcd-cluster"
ETCD_ADVERTISE_CLIENT_URLS="http://etcd2:2379,http://etcd2:4001"
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

 改好配置之后，在各个节点上开启etcd服务：

```
# systemctl restart etcd
```

**四、测试验证**

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
[root@localhost ~]# etcdctl set testdir/testkey0 0
0
[root@localhost ~]# etcdctl set testdir/testkey1 1
1
[root@localhost ~]# etcdctl set testdir/testkey2 2
2
[root@localhost ~]# etcdctl ls
/test
/testdir
[root@localhost ~]# etcdctl ls testdir
/testdir/testkey0
/testdir/testkey1
/testdir/testkey2
[root@localhost ~]# etcdctl get testdir/testkey2
2
[root@localhost ~]# etcdctl member list
377aa10974e8238d: name=node1 peerURLs=http://node1:2380 clientURLs=http://node1:2379,http://node1:4001 isLeader=true
9de2d4fdbbd835b6: name=etcd2 peerURLs=http://etcd2:2380 clientURLs=http://etcd2:2379,http://etcd2:4001 isLeader=false
f75ed833c7cbbe65: name=node2 peerURLs=http://node2:2380 clientURLs=http://node2:2379,http://node2:4001 isLeader=false
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

 