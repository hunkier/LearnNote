安装weaveworks监控

```shell
[vagrant@master ~]$ sudo curl -L git.io/scope -o /usr/local/bin/scope
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
  0     0    0     0    0     0      0      0 --:--:-- --:--:-- --:--:--     0
  0     0    0     0    0     0      0      0 --:--:--  0:00:02 --:--:--     0
  0     0    0     0    0     0      0      0 --:--:--  0:00:05 --:--:--     0
  0     0    0   595    0     0     94      0 --:--:--  0:00:06 --:--:--   929
100 11663  100 11663    0     0   1540      0  0:00:07  0:00:07 --:--:-- 15245
[vagrant@master ~]$ sudo chmod +x /usr/local/bin/scope
[vagrant@master ~]$ scope launch 192.168.33.200
Unable to find image 'weaveworks/scope:1.9.1' locally
1.9.1: Pulling from weaveworks/scope
f4900964ff56: Pull complete
492f0586676a: Pull complete
ef01d0528468: Pull complete
3da02b9928c5: Pull complete
a48986778278: Pull complete
7b1065123316: Pull complete
ac6cb2ba185f: Pull complete
9198945ee9e3: Pull complete
48cc76c52705: Pull complete
6af8463b2ab9: Pull complete
Digest: sha256:651cc9ac380803a83e19af40943ebece696448b053d3f80f3fd86b430e558606
Status: Downloaded newer image for weaveworks/scope:1.9.1
926aefc03210a49689e5c73eec144fe7ad3b7caf3321a40008293a7b8abeb06b
Scope probe started
Weave Scope is listening at the following URL(s):
  * http://10.42.0.0:4040/
  * http://192.168.33.200:4040/
  * http://10.0.2.15:4040/
[vagrant@master ~]$

# 添加其他节点
scope launch 192.168.33.200 192.168.33.2 192.168.33.201 192.168.33.202
```

heapster资源监控

```shell
git https://github.com/kubernetes/heapster

# 在heapster/deploy/kube-config/influxdb/目录下，采用influxdb方式安装
[vagrant@master ~]$ ls
grafana.yaml  heapster.yaml  influxdb.yaml  labs
[vagrant@master ~]$ kubectl c
certificate   cluster-info  completion    config        convert       cordon        cp            create
[vagrant@master ~]$ kubectl create -f influxdb.yaml
deployment.extensions/monitoring-influxdb created
service/monitoring-influxdb created
[vagrant@master ~]$ kubectl create -f heapster.yaml
serviceaccount/heapster created
deployment.extensions/heapster created
service/heapster created
[vagrant@master ~]$ kubectl create -f grafana.yaml
deployment.extensions/monitoring-grafana created
service/monitoring-grafana created
[vagrant@master ~]$
```

安装wrk压力测试工具

```shell
git clone https://github.com/wg/wrk.git
cd wrk
make
ln -s /usr/local/src/wrk/wrk /usr/local/bin
# 一般用法
wrk -t1 -c400 —timeout 5s -d 100s —latency ‘http://localhost:8080/index.html‘

#添加Header和Lua脚本（Lua脚本后面再详细说）
wrk -t1 -c400 —timeout 5s -d 100s -s ‘/usr/local/src/test.lua’ -H “Authorization: ABCDEFGH123OPQ” —latency ‘http://localhost:8080/index.html‘
--------------------- 

```

