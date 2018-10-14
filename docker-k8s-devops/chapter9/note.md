minikube创建k8s单节点

```shell
minikube start
kubectl config view
kubectl config get-contexts
kubectl cluster-info
minikube ssh
```

kubectl自动补全

```shell
[vagrant@worker1 deployment]$ source <(kubectl completion zsh)

[vagrant@worker1 deployment]$ source <(kubectl completion bash)
[vagrant@worker1 deployment]$ kubectl
alpha          auth           convert        drain          label          proxy          taint
annotate       autoscale      cordon         edit           logs           replace        top
api-resources  certificate    cp             exec           options        rollout        uncordon
api-versions   cluster-info   create         explain        patch          run            version
apply          completion     delete         expose         plugin         scale          wait
attach         config         describe       get            port-forward   set
[vagrant@worker1 deployment]$ kubectl p
patch         plugin        port-forward  proxy
[vagrant@worker1 deployment]$ kubectl d
delete    describe  drain
```

oracle的vagrant镜像

https://github.com/oracle/vagrant-boxes

https://yq.aliyun.com/articles/221687



[rancher安装kubernetes集群](https://www.cnrancher.com/docs/rancher/v2.x/cn/installation/server-installation/air-gap-installation/)



```shell
docker run -d -p 80:80 -p 443:443
-e CATTLE_SYSTEM_DEFAULT_REGISTRY=registry.yourdomain.com:port 
registry.yourdomain.com:port/rancher/rancher:v2.1.0
```

[dashboard配置](https://studygolang.com/articles/11730?fr=sidebar)

## dashboard访问

### service account原理

k8s里面有两种用户，一种是User，一种就是service account，User给人用的，service account给进程用的，让进程有相关的权限。

如dasboard就是一个进程，我们就可以创建一个service account给它，让它去访问k8s。

我们看一下是如何把admin权限赋给dashboard的

```shell
[vagrant@node1 ~]$ cat dashboard-admin.yaml
apiVersion: rbac.authorization.k8s.io/v1beta1
kind: ClusterRoleBinding
metadata:
  name: kubernetes-dashboard
  labels:
    k8s-app: kubernetes-dashboard
roleRef:
  apiGroup: rbac.authorization.k8s.io
  kind: ClusterRole
  name: cluster-admin
subjects:
- kind: ServiceAccount
  name: kubernetes-dashboard
  namespace: kube-system
[vagrant@node1 ~]$
[vagrant@node1 ~]$ kubectl create -f dashboard-admin.yaml
clusterrolebinding.rbac.authorization.k8s.io/kubernetes-dashboard created
```

把 kubernetes-dashboard 这个ServiceAccount绑定到cluster-admin这个ClusterRole上，这个cluster role非常牛逼，啥权限都有

```shell
[vagrant@node1 ~]$ kubectl describe clusterrole cluster-admin -n kube-system
Name:         cluster-admin
Labels:       kubernetes.io/bootstrapping=rbac-defaults
Annotations:  rbac.authorization.kubernetes.io/autoupdate: true
PolicyRule:
  Resources  Non-Resource URLs  Resource Names  Verbs
  ---------  -----------------  --------------  -----
  *.*        []                 []              [*]
             [*]                []              [*]
[vagrant@node1 ~]$
```

更安全的做法

```shell
[vagrant@node1 ~]$ cat admin-token.yaml
kind: ClusterRoleBinding
apiVersion: rbac.authorization.k8s.io/v1beta1
metadata:
  name: admin
  annotations:
    rbac.authorization.kubernetes.io/autoupdate: "true"
roleRef:
  kind: ClusterRole
  name: cluster-admin
  apiGroup: rbac.authorization.k8s.io
subjects:
- kind: ServiceAccount
  name: admin
  namespace: kube-system
---
apiVersion: v1
kind: ServiceAccount
metadata:
  name: admin
  namespace: kube-system
  labels:
    kubernetes.io/cluster-service: "true"
    addonmanager.kubernetes.io/mode: Reconcile
[vagrant@node1 ~]$
[vagrant@node1 ~]$ kubectl create -f admin-token.yaml
clusterrolebinding.rbac.authorization.k8s.io/admin created
serviceaccount/admin created
[vagrant@node1 ~]$
```

```shell
[vagrant@node1 ~]$ kubectl get secret -n kube-system|grep admin
admin-token-66sg6                                kubernetes.io/service-account-token   3      33s
kube-admin                                       Opaque                                9      1h
[vagrant@node1 ~]$
[vagrant@node1 ~]$ kubectl describe secret admin-token-66sg6 -n kube-system
Name:         admin-token-66sg6
Namespace:    kube-system
Labels:       <none>
Annotations:  kubernetes.io/service-account.name: admin
              kubernetes.io/service-account.uid: 085ce17b-cedb-11e8-8bc8-525400c9c704

Type:  kubernetes.io/service-account-token

Data
====
ca.crt:     1017 bytes
namespace:  11 bytes
token:      eyJhbGciOiJSUzI1NiIsImtpZCI6IiJ9.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJrdWJlLXN5c3RlbSIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VjcmV0Lm5hbWUiOiJhZG1pbi10b2tlbi02NnNnNiIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VydmljZS1hY2NvdW50Lm5hbWUiOiJhZG1pbiIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VydmljZS1hY2NvdW50LnVpZCI6IjA4NWNlMTdiLWNlZGItMTFlOC04YmM4LTUyNTQwMGM5YzcwNCIsInN1YiI6InN5c3RlbTpzZXJ2aWNlYWNjb3VudDprdWJlLXN5c3RlbTphZG1pbiJ9.qrD2S7ueG3U007yDFF-Hhu4YUXWqiCI7qH63Z7ix6h-BwrXghJdFNZPUPAwopbgyJnoaUzpkoEkMkr_oeVOdp-itYHKSvw_zBmGzg_0k4YsHJG3TW-lOjfXrWiwWrjDDbBehlrcc6JSmvDSOP1icjIZbicDNKPpyDpTLVEUaTd7Gkn0mT8VK8EByxoL8WUP8HoFVsPaNQJg6GfuS-KNCxqaEOXK8ML9GntMBIuIsDUQm6B1xp2yNtupt4rGCvuTfuBUTn2pJiWjB7HWFyBR7tA7AmWjFQaOcrjeLdjN-NDhSuZdvCH65TD1Ej7irHOC2U8ZKu6ChgW5LAnzy8x6VUw
[vagrant@node1 ~]$
```

用此token在kubernetes-dashboard界面上登录即可

### Windows

Install with [Chocolatey](https://chocolatey.org/):

```cmd
@"%SystemRoot%\System32\WindowsPowerShell\v1.0\powershell.exe" -NoProfile -InputFormat None -ExecutionPolicy Bypass -Command "iex ((New-Object System.Net.WebClient).DownloadString('https://chocolatey.org/install.ps1'))" && SET "PATH=%PATH%;%ALLUSERSPROFILE%\chocolatey\bin"

choco install minikube


minikube start  --registry-mirror=https://registry.docker-cn.com  --bootstrapper localkube
```



```ssh
minikube start --vm-driver=none --registry-mirror=https://registry.docker-cn.com
```



```cmd
G:\Users\hunk>minikube version
minikube version: v0.28.2

G:\Users\hunk>kubectl version
Client Version: version.Info{Major:"1", Minor:"11", GitVersion:"v1.11.3", GitCommit:"a4529464e4629c21224b3d52edfe0ea91b072862", GitTreeState:"clean", BuildDate:"2018-09-09T18:02:47Z", GoVersion:"go1.10.3", Compiler:"gc", Platform:"windows/amd64"}
Server Version: version.Info{Major:"1", Minor:"10", GitVersion:"v1.10.0", GitCommit:"fc32d2f3698e36b93322a3465f63a14e9f0eaead", GitTreeState:"clean", BuildDate:"2018-04-10T12:46:31Z", GoVersion:"go1.9.4", Compiler:"gc", Platform:"linux/amd64"}

G:\Users\hunk>which minikube
/g/ProgramData/chocolatey/bin/minikube

G:\Users\hunk>which kubectl
/g/ProgramData/chocolatey/bin/kubectl

G:\Users\hunk>kubectl config view
apiVersion: v1
clusters:
- cluster:
    certificate-authority: G:\Users\hunk\.minikube\ca.crt
    server: https://192.168.99.100:8443
  name: minikube
contexts:
- context:
    cluster: minikube
    user: minikube
  name: minikube
current-context: minikube
kind: Config
preferences: {}
users:
- name: minikube
  user:
    client-certificate: G:\Users\hunk\.minikube\client.crt
    client-key: G:\Users\hunk\.minikube\client.key

G:\Users\hunk>kubectl config get-contexts
CURRENT   NAME       CLUSTER    AUTHINFO   NAMESPACE
*         minikube   minikube   minikube

G:\Users\hunk>kubectl cluster-info
Kubernetes master is running at https://192.168.99.100:8443

To further debug and diagnose cluster problems, use 'kubectl cluster-info dump'.

G:\Users\hunk>

```





```shell
[vagrant@rke pod-basic]$ ls
pod_busybox.yml  pod_nginx.yml  README.md
[vagrant@rke pod-basic]$ cat pod_nginx.yml 
apiVersion: v1
kind: Pod
metadata:
  name: nginx
  labels:
    app: nginx
spec:
  containers:
  - name: nginx
    image: nginx
    ports:
    - containerPort: 80
[vagrant@rke pod-basic]$ kubectl get pods
NAME    READY   STATUS    RESTARTS   AGE
nginx   1/1     Running   1          2h
[vagrant@rke pod-basic]$ kubectl delete -f pod_nginx.yml 
pod "nginx" deleted
[vagrant@rke pod-basic]$ kubectl create -f pod_nginx.yml 
pod/nginx created
[vagrant@rke pod-basic]$ kubectl get pods
NAME    READY   STATUS    RESTARTS   AGE
nginx   1/1     Running   0          6s
[vagrant@rke pod-basic]$ 
[vagrant@rke pod-basic]$ kubectl get pods -o wide
NAME    READY   STATUS    RESTARTS   AGE   IP          NODE    NOMINATED NODE
nginx   1/1     Running   0          1m    10.42.3.4   work3   <none>

```



```shell
[vagrant@node3 ~]$ docker ps | grep nginx
1b75a4e45952        nginx                                             "nginx -g 'daemon of…"   6 minutes ago       Up 6 minutes                            k8s_nginx_nginx_default_84ef3b3d-ceba-11e8-8cfb-525400c9c704_0
7ffb2a7215e9        192.168.33.2/rancher/pause-amd64:3.1              "/pause"                 6 minutes ago       Up 6 minutes                            k8s_POD_nginx_default_84ef3b3d-ceba-11e8-8cfb-525400c9c704_0
f1c634387f22        df4469c42185                                      "/usr/bin/dumb-init …"   13 minutes ago      Up 13 minutes                           k8s_nginx-ingress-controller_nginx-ingress-controller-qxrgd_ingress-nginx_277098e9-ce98-11e8-ab8c-525400c9c704_1
5e8a91243ca0        192.168.33.2/rancher/pause-amd64:3.1              "/pause"                 13 minutes ago      Up 13 minutes                           k8s_POD_nginx-ingress-controller-qxrgd_ingress-nginx_277098e9-ce98-11e8-ab8c-525400c9c704_1
2648a446ca7d        192.168.33.2/rancher/rke-tools:v0.1.15            "nginx-proxy CP_HOST…"   4 hours ago         Up 13 minutes                           nginx-proxy
[vagrant@node3 ~]$ docker exec -it 1b75a4e45952 sh
# 
# ip a
sh: 2: ip: not found
# whoami
root
# uname -a
Linux nginx 3.10.0-862.2.3.el7.x86_64 #1 SMP Wed May 9 18:05:47 UTC 2018 x86_64 GNU/Linux
# 
```

```shell
[vagrant@node1 pod-basic]$ kubectl create -f pod_nginx.yml 
pod/nginx created
[vagrant@node1 pod-basic]$ kubectl get pods -o wide
NAME    READY   STATUS              RESTARTS   AGE   IP       NODE     NOMINATED NODE
nginx   0/1     ContainerCreating   0          23s   <none>   worker   <none>
[vagrant@node1 pod-basic]$ kubectl get pods -o wide
NAME    READY   STATUS              RESTARTS   AGE   IP       NODE     NOMINATED NODE
nginx   0/1     ContainerCreating   0          27s   <none>   worker   <none>
[vagrant@node1 pod-basic]$ kubectl get pods -o wide
NAME    READY   STATUS    RESTARTS   AGE   IP          NODE     NOMINATED NODE
nginx   1/1     Running   0          31s   10.42.1.7   worker   <none>
[vagrant@node1 pod-basic]$ curl localhost:80
^C
[vagrant@node1 pod-basic]$ curl 10.42.1.7
<!DOCTYPE html>
<html>
<head>
<title>Welcome to nginx!</title>
<style>
    body {
        width: 35em;
        margin: 0 auto;
        font-family: Tahoma, Verdana, Arial, sans-serif;
    }
</style>
</head>
<body>
<h1>Welcome to nginx!</h1>
<p>If you see this page, the nginx web server is successfully installed and
working. Further configuration is required.</p>

<p>For online documentation and support please refer to
<a href="http://nginx.org/">nginx.org</a>.<br/>
Commercial support is available at
<a href="http://nginx.com/">nginx.com</a>.</p>

<p><em>Thank you for using nginx.</em></p>
</body>
</html>
[vagrant@node1 pod-basic]$ kubectl describe pods nginx
Name:               nginx
Namespace:          default
Priority:           0
PriorityClassName:  <none>
Node:               worker/10.0.2.15
Start Time:         Sun, 14 Oct 2018 00:06:18 +0800
Labels:             app=nginx
Annotations:        cni.projectcalico.org/podIP: 10.42.1.7/32
Status:             Running
IP:                 10.42.1.7
Containers:
  nginx:
    Container ID:   docker://38885d69b8709f084d1ade13b43824b9b0b2564253751e126a2249bd607f7d11
    Image:          nginx
    Image ID:       docker-pullable://nginx@sha256:9ad0746d8f2ea6df3a17ba89eca40b48c47066dfab55a75e08e2b70fc80d929e
    Port:           80/TCP
    Host Port:      0/TCP
    State:          Running
      Started:      Sun, 14 Oct 2018 00:06:45 +0800
    Ready:          True
    Restart Count:  0
    Environment:    <none>
    Mounts:
      /var/run/secrets/kubernetes.io/serviceaccount from default-token-vh2r4 (ro)
Conditions:
  Type              Status
  Initialized       True 
  Ready             True 
  ContainersReady   True 
  PodScheduled      True 
Volumes:
  default-token-vh2r4:
    Type:        Secret (a volume populated by a Secret)
    SecretName:  default-token-vh2r4
    Optional:    false
QoS Class:       BestEffort
Node-Selectors:  <none>
Tolerations:     node.kubernetes.io/not-ready:NoExecute for 300s
                 node.kubernetes.io/unreachable:NoExecute for 300s
Events:
  Type    Reason     Age   From               Message
  ----    ------     ----  ----               -------
  Normal  Scheduled  110s  default-scheduler  Successfully assigned default/nginx to worker
  Normal  Pulling    109s  kubelet, worker    pulling image "nginx"
  Normal  Pulled     83s   kubelet, worker    Successfully pulled image "nginx"
  Normal  Created    83s   kubelet, worker    Created container
  Normal  Started    83s   kubelet, worker    Started container
[vagrant@node1 pod-basic]$ 
```

端口转发

```shell
[vagrant@worker2 ~]$ kubectl port-forward pod/nginx 8088:80
Forwarding from 127.0.0.1:8088 -> 80
Forwarding from [::1]:8088 -> 80
Handling connection for 8088
Handling connection for 8088
```

删除pod

```shell
[vagrant@worker2 pod-basic]$ kubectl delete -f pod_nginx.yml
pod "nginx" deleted
[vagrant@worker2 pod-basic]$ kubectl get pods
No resources found.
[vagrant@worker2 pod-basic]$
```

创建ReplicationController集群

```shell
[vagrant@worker2 replicas-set]$ cat rc_nginx.yml
apiVersion: v1
kind: ReplicationController
metadata:
  name: nginx
spec:
  replicas: 3
  selector:
    app: nginx
  template:
    metadata:
      name: nginx
      labels:
        app: nginx
    spec:
      containers:
      - name: nginx
        image: nginx
        ports:
        - containerPort: 80
[vagrant@worker2 replicas-set]$ kubectl create -f rc_nginx.yml
replicationcontroller/nginx created
[vagrant@worker2 replicas-set]$ kubectl get rc -o wide
NAME    DESIRED   CURRENT   READY   AGE   CONTAINERS   IMAGES   SELECTOR
nginx   3         3         3       20s   nginx        nginx    app=nginx
[vagrant@worker2 replicas-set]$ kubectl get pods
NAME          READY   STATUS    RESTARTS   AGE
nginx-k68lr   1/1     Running   0          59s
nginx-m6mbc   1/1     Running   0          59s
nginx-s7rcc   1/1     Running   0          59s
[vagrant@worker2 replicas-set]$
```

删除pods会自动重新创建

```shell
[vagrant@worker2 replicas-set]$ kubectl get pods
NAME          READY   STATUS    RESTARTS   AGE
nginx-k68lr   1/1     Running   0          59s
nginx-m6mbc   1/1     Running   0          59s
nginx-s7rcc   1/1     Running   0          59s
[vagrant@worker2 replicas-set]$ 
[vagrant@worker2 replicas-set]$ kubectl delete pods nginx-k68lr
pod "nginx-k68lr" deleted
[vagrant@worker2 replicas-set]$ kubectl get pods
NAME          READY   STATUS    RESTARTS   AGE
nginx-hgmbr   1/1     Running   0          10s
nginx-m6mbc   1/1     Running   0          2m
nginx-s7rcc   1/1     Running   0          2m
[vagrant@worker2 replicas-set]$ kubectl delete pods nginx-hgmbr
pod "nginx-hgmbr" deleted
^[[A[vagrant@worker2 replicas-set]$ kubectl get pods
NAME          READY   STATUS    RESTARTS   AGE
nginx-2hwvg   1/1     Running   0          4s
nginx-m6mbc   1/1     Running   0          3m
nginx-s7rcc   1/1     Running   0          3m
[vagrant@worker2 replicas-set]$
```

集群扩展伸缩

```shell
[vagrant@worker2 replicas-set]$ kubectl scale --replicas=2 rc/nginx
replicationcontroller/nginx scaled
[vagrant@worker2 replicas-set]$ kubectl get pods -o wide
NAME          READY   STATUS    RESTARTS   AGE   IP           NODE      NOMINATED NODE
nginx-m6mbc   1/1     Running   0          9m    10.42.2.8    worker2   <none>
nginx-s7rcc   1/1     Running   0          9m    10.42.1.12   worker1   <none>
[vagrant@worker2 replicas-set]$ kubectl get rc
NAME    DESIRED   CURRENT   READY   AGE
nginx   2         2         2       9m
[vagrant@worker2 replicas-set]$
[vagrant@worker2 replicas-set]$ kubectl scale --replicas=1 rc/nginx
replicationcontroller/nginx scaled
[vagrant@worker2 replicas-set]$ kubectl get rc
NAME    DESIRED   CURRENT   READY   AGE
nginx   1         1         1       10m
[vagrant@worker2 replicas-set]$ kubectl get pods -o wide
NAME          READY   STATUS    RESTARTS   AGE   IP          NODE      NOMINATED NODE
nginx-m6mbc   1/1     Running   0          10m   10.42.2.8   worker2   <none>
[vagrant@worker2 replicas-set]$ kubectl scale --replicas=5 rc/nginx
replicationcontroller/nginx scaled
[vagrant@worker2 replicas-set]$ kubectl get pods -o wide
NAME          READY   STATUS              RESTARTS   AGE   IP          NODE      NOMINATED NODE
nginx-hnh2q   0/1     ContainerCreating   0          2s    <none>      worker1   <none>
nginx-m255k   0/1     ContainerCreating   0          2s    <none>      worker2   <none>
nginx-m6mbc   1/1     Running             0          10m   10.42.2.8   worker2   <none>
nginx-p8lbq   0/1     ContainerCreating   0          2s    <none>      worker1   <none>
nginx-rfcf9   0/1     ContainerCreating   0          2s    <none>      worker2   <none>
[vagrant@worker2 replicas-set]$ kubectl get pods -o wide
NAME          READY   STATUS    RESTARTS   AGE   IP           NODE      NOMINATED NODE
nginx-hnh2q   1/1     Running   0          12s   10.42.1.14   worker1   <none>
nginx-m255k   1/1     Running   0          12s   10.42.2.12   worker2   <none>
nginx-m6mbc   1/1     Running   0          10m   10.42.2.8    worker2   <none>
nginx-p8lbq   1/1     Running   0          12s   10.42.1.13   worker1   <none>
nginx-rfcf9   1/1     Running   0          12s   10.42.2.13   worker2   <none>
[vagrant@worker2 replicas-set]$
```

删除集群

```shell
[vagrant@worker2 replicas-set]$ kubectl delete -f rc_nginx.yml
replicationcontroller "nginx" deleted
[vagrant@worker2 replicas-set]$ kubectl get pods -o wide
NAME          READY   STATUS        RESTARTS   AGE   IP           NODE      NOMINATED NODE
nginx-m255k   0/1     Terminating   0          3m    10.42.2.12   worker2   <none>
nginx-m6mbc   0/1     Terminating   0          13m   10.42.2.8    worker2   <none>
nginx-p8lbq   0/1     Terminating   0          3m    <none>       worker1   <none>
nginx-rfcf9   0/1     Terminating   0          3m    <none>       worker2   <none>
[vagrant@worker2 replicas-set]$ kubectl get pods -o wide
NAME          READY   STATUS        RESTARTS   AGE   IP           NODE      NOMINATED NODE
nginx-m255k   0/1     Terminating   0          3m    10.42.2.12   worker2   <none>
nginx-m6mbc   0/1     Terminating   0          13m   10.42.2.8    worker2   <none>
nginx-rfcf9   0/1     Terminating   0          3m    <none>       worker2   <none>
[vagrant@worker2 replicas-set]$ kubectl get pods -o wide
NAME          READY   STATUS        RESTARTS   AGE   IP           NODE      NOMINATED NODE
nginx-m255k   0/1     Terminating   0          3m    10.42.2.12   worker2   <none>
nginx-m6mbc   0/1     Terminating   0          13m   10.42.2.8    worker2   <none>
nginx-rfcf9   0/1     Terminating   0          3m    <none>       worker2   <none>
[vagrant@worker2 replicas-set]$ kubectl get pods -o wide
No resources found.
[vagrant@worker2 replicas-set]$ kubectl get rc
No resources found.
[vagrant@worker2 replicas-set]$
```

创建ReplicaSet集群

```shell
[vagrant@worker2 replicas-set]$ cat rs_nginx.yml
apiVersion: apps/v1
kind: ReplicaSet
metadata:
  name: nginx
  labels:
    tier: frontend
spec:
  replicas: 3
  selector:
    matchLabels:
      tier: frontend
  template:
    metadata:
      name: nginx
      labels:
        tier: frontend
    spec:
      containers:
      - name: nginx
        image: nginx
        ports:
        - containerPort: 80
[vagrant@worker2 replicas-set]$ kubectl create -f rs_nginx.yml
replicaset.apps/nginx created
[vagrant@worker2 replicas-set]$ kubectl get rs -o wide
NAME    DESIRED   CURRENT   READY   AGE   CONTAINERS   IMAGES   SELECTOR
nginx   3         3         2       27s   nginx        nginx    tier=frontend
[vagrant@worker2 replicas-set]$ kubectl get pods -o wide
NAME          READY   STATUS    RESTARTS   AGE   IP           NODE      NOMINATED NODE
nginx-4l92j   1/1     Running   0          44s   10.42.2.15   worker2   <none>
nginx-668jz   1/1     Running   0          44s   10.42.1.15   worker1   <none>
nginx-kgqh2   1/1     Running   0          44s   10.42.2.14   worker2   <none>
[vagrant@worker2 replicas-set]$
[vagrant@worker2 replicas-set]$ kubectl scale rs/nginx --replicas=2
replicaset.extensions/nginx scaled
[vagrant@worker2 replicas-set]$ kubectl get pods -o wide
NAME          READY   STATUS    RESTARTS   AGE   IP           NODE      NOMINATED NODE
nginx-4l92j   1/1     Running   0          3m    10.42.2.15   worker2   <none>
nginx-kgqh2   1/1     Running   0          3m    10.42.2.14   worker2   <none>
[vagrant@worker2 replicas-set]$


```

deployment

```shell
[vagrant@worker2 deployment]$ ls
deployment_nginx.yml
[vagrant@worker2 deployment]$ cat deployment_nginx.yml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: nginx-deployment
  labels:
    app: nginx
spec:
  replicas: 3
  selector:
    matchLabels:
      app: nginx
  template:
    metadata:
      labels:
        app: nginx
    spec:
      containers:
      - name: nginx
        image: nginx:1.12.2
        ports:
        - containerPort: 80[vagrant@worker2 deployment]$
[vagrant@worker2 deployment]$
[vagrant@worker2 deployment]$ kubectl create -f deployment_nginx.yml
deployment.apps/nginx-deployment created
[vagrant@worker2 deployment]$ kubectl get pods
NAME                                READY   STATUS              RESTARTS   AGE
nginx-4l92j                         1/1     Running             0          6m
nginx-deployment-6bfd5b66fc-279wp   0/1     ContainerCreating   0          4s
nginx-deployment-6bfd5b66fc-4cz5r   0/1     ContainerCreating   0          4s
nginx-deployment-6bfd5b66fc-xvz7v   0/1     ContainerCreating   0          4s
nginx-kgqh2                         1/1     Running             0          6m
[vagrant@worker2 deployment]$ kubectl get pods -o wide
NAME                                READY   STATUS              RESTARTS   AGE   IP           NODE      NOMINATED NODE
nginx-deployment-6bfd5b66fc-279wp   0/1     ContainerCreating   0          9s    <none>       worker2   <none>
nginx-deployment-6bfd5b66fc-4cz5r   0/1     ContainerCreating   0          9s    <none>       worker1   <none>
nginx-deployment-6bfd5b66fc-xvz7v   0/1     ContainerCreating   0          9s    <none>       worker2   <none>
[vagrant@worker2 deployment]$ kubectl get rs
NAME                          DESIRED   CURRENT   READY   AGE
nginx-deployment-6bfd5b66fc   3         3         3       33s
[vagrant@worker2 deployment]$
[vagrant@worker2 deployment]$ kubectl get deployment
NAME               DESIRED   CURRENT   UP-TO-DATE   AVAILABLE   AGE
nginx-deployment   3         3         3            3           3m
[vagrant@worker2 deployment]$
[vagrant@worker2 deployment]$ kubectl get deployment -o wide
NAME               DESIRED   CURRENT   UP-TO-DATE   AVAILABLE   AGE   CONTAINERS   IMAGES         SELECTOR
nginx-deployment   3         3         3            3           4m    nginx        nginx:1.12.2   app=nginx
[vagrant@worker2 deployment]$

```

image版本升级

```shell
[vagrant@worker2 deployment]$ kubectl set image deployment nginx-deployment nginx=nginx:1.13
deployment.extensions/nginx-deployment image updated
[vagrant@worker2 deployment]$ kubectl get deployment -o wide
NAME               DESIRED   CURRENT   UP-TO-DATE   AVAILABLE   AGE   CONTAINERS   IMAGES       SELECTOR
nginx-deployment   3         4         1            3           5m    nginx        nginx:1.13   app=nginx
[vagrant@worker2 deployment]$ kubectl get deployment -o wide
NAME               DESIRED   CURRENT   UP-TO-DATE   AVAILABLE   AGE   CONTAINERS   IMAGES       SELECTOR
nginx-deployment   3         4         2            3           6m    nginx        nginx:1.13   app=nginx
[vagrant@worker2 deployment]$ kubectl get deployment -o wide
NAME               DESIRED   CURRENT   UP-TO-DATE   AVAILABLE   AGE   CONTAINERS   IMAGES       SELECTOR
nginx-deployment   3         4         2            3           6m    nginx        nginx:1.13   app=nginx
[vagrant@worker2 deployment]$ kubectl get deployment -o wide
NAME               DESIRED   CURRENT   UP-TO-DATE   AVAILABLE   AGE   CONTAINERS   IMAGES       SELECTOR
nginx-deployment   3         3         3            3           6m    nginx        nginx:1.13   app=nginx
[vagrant@worker2 deployment]$
[vagrant@worker2 deployment]$ kubectl get pods -o wide
NAME                                READY   STATUS    RESTARTS   AGE   IP           NODE      NOMINATED NODE
nginx-deployment-57b696c78d-2wg7w   1/1     Running   0          2m    10.42.1.17   worker1   <none>
nginx-deployment-57b696c78d-ljtrz   1/1     Running   0          2m    10.42.2.19   worker2   <none>
nginx-deployment-57b696c78d-s9cnv   1/1     Running   0          2m    10.42.2.18   worker2   <none>
[vagrant@worker2 deployment]$ kubectl get rs -o wide
NAME                          DESIRED   CURRENT   READY   AGE   CONTAINERS   IMAGES         SELECTOR
nginx-deployment-57b696c78d   3         3         3       3m    nginx        nginx:1.13     app=nginx,pod-template-hash=1362527348
nginx-deployment-6bfd5b66fc   0         0         0       9m    nginx        nginx:1.12.2   app=nginx,pod-template-hash=2698162297
[vagrant@worker2 deployment]$ kubectl rollout history deployment nginx-deployment
deployment.extensions/nginx-deployment
REVISION  CHANGE-CAUSE
1         <none>
2         <none>

[vagrant@worker2 deployment]$

```

回滚到历史版本

```shell
[vagrant@worker2 deployment]$ kubectl rollout undo deployment nginx-deployment
deployment.extensions/nginx-deployment
[vagrant@worker2 deployment]$ 
[vagrant@worker2 deployment]$ kubectl get deployment -o wide
NAME               DESIRED   CURRENT   UP-TO-DATE   AVAILABLE   AGE   CONTAINERS   IMAGES         SELECTOR
nginx-deployment   3         3         3            3           12m   nginx        nginx:1.12.2   app=nginx
[vagrant@worker2 deployment]$
[vagrant@worker2 deployment]$ kubectl rollout history deployment nginx-deployment
deployment.extensions/nginx-deployment
REVISION  CHANGE-CAUSE
2         <none>
3         <none>

[vagrant@worker2 deployment]$
```

删除deployment

```
[vagrant@worker1 services]$ kubectl get deploy
NAME               DESIRED   CURRENT   UP-TO-DATE   AVAILABLE   AGE
nginx-deployment   3         3         3            0           5h
[vagrant@worker1 services]$ kubectl delete deployment nginx-deployment
deployment.extensions "nginx-deployment" deleted
[vagrant@worker1 services]$
```



删除services

```shell
[vagrant@worker1 deployment]$ kubectl delete services nginx-deployment
service "nginx-deployment" deleted
[vagrant@worker1 deployment]$ kubectl expose deployment nginx-deployment --port=80 --target-port=8000  --type=NodePort
service/nginx-deployment exposed
[vagrant@worker1 deployment]$ kubectl get svc
NAME               TYPE        CLUSTER-IP    EXTERNAL-IP   PORT(S)        AGE
kubernetes         ClusterIP   10.43.0.1     <none>        443/TCP        1h
nginx-deployment   NodePort    10.43.91.42   <none>        80:32759/TCP   1m
[vagrant@worker1 deployment]$
[vagrant@worker1 deployment]$ kubectl delete services nginx-deployment
service "nginx-deployment" deleted
```



kubectl部署services

```shell
[vagrant@worker1 labs]$ cd services/
[vagrant@worker1 services]$ ls
pod_busybox.yml  pod_nginx.yml  service_nginx.yml
[vagrant@worker1 services]$ cat pod_busybox.yml
apiVersion: v1
kind: Pod
metadata:
  name: busybox-pod
  labels:
    app: busybox
spec:
  containers:
  - name: busybox-container
    image: busybox
    command:
      - sleep
      - "360000"[vagrant@worker1 services]$
[vagrant@worker1 services]$
[vagrant@worker1 services]$ kubectl create -f pod_busybox.yml
pod/busybox-pod created
[vagrant@worker1 services]$ cat pod_nginx.yml
apiVersion: v1
kind: Pod
metadata:
  name: nginx-pod
  labels:
    app: nginx
spec:
  containers:
  - name: nginx-container
    image: nginx
    ports:
    - name: nginx-port
      containerPort: 80
[vagrant@worker1 services]$ kubectl get pods -o wide
NAME                                READY   STATUS             RESTARTS   AGE   IP           NODE      NOMINATED NODE
busybox-pod                         1/1     Running            0          28s   10.42.2.2    worker2   <none>
[vagrant@worker1 services]$ kubectl create -f pod_nginx.yml
pod/nginx-pod created
[vagrant@worker1 services]$ kubectl get pods -o wide
NAME                                READY   STATUS              RESTARTS   AGE   IP           NODE      NOMINATED NODE
busybox-pod                         1/1     Running             0          1m    10.42.2.2    worker2   <none>
nginx-pod                           0/1     ContainerCreating   0          17s   <none>       worker1   <none>
[vagrant@worker1 services]$
```





```shell
[vagrant@worker1 labs]$ cd services/
[vagrant@worker1 services]$ ls
deployment_python_http.yml  pod_busybox.yml  pod_nginx.yml  service_nginx.yml
[vagrant@worker1 services]$ cat deployment_python_http.yml
apiVersion: extensions/v1beta1
kind: Deployment
metadata:
  name: service-test
spec:
  replicas: 2
  selector:
    matchLabels:
      app: service_test_pod
  template:
    metadata:
      labels:
        app: service_test_pod
    spec:
      containers:
      - name: simple-http
        image: python:2.7
        imagePullPolicy: IfNotPresent
        command: ["/bin/bash"]
        args: ["-c", "echo \" <p>Hello from $(hostname)</p>\" > index.html; python -m SimpleHTTPServer 8080"]
        ports:
        - name: http
          containerPort: 8080
          
[vagrant@worker1 services]$
[vagrant@worker1 services]$ kubectl create -f deployment_python_http.yml
deployment.extensions/service-test created
[vagrant@worker1 services]$ kubectl get pods -o wide
NAME                            READY   STATUS    RESTARTS   AGE   IP          NODE      NOMINATED NODE
service-test-697bb576b7-btxvh   1/1     Running   0          8s    10.42.1.6   worker1   <none>
service-test-697bb576b7-hn8q2   1/1     Running   0          8s    10.42.2.2   worker2   <none>
[vagrant@worker1 services]$

[vagrant@worker1 ~]$ curl 10.42.1.6:8080
 <p>Hello from service-test-697bb576b7-btxvh</p>
[vagrant@worker1 services]$

[vagrant@worker2 ~]$ curl 10.42.2.2:8080
 <p>Hello from service-test-697bb576b7-hn8q2</p>
[vagrant@worker1 services]$
 

 
```



导出服务，k8s集群任意worker节点均可访问

```shell
[vagrant@worker1 services]$ kubectl get deployment
NAME           DESIRED   CURRENT   UP-TO-DATE   AVAILABLE   AGE
service-test   2         2         2            2           5m
[vagrant@worker1 services]$ kubectl expose deployment service-test
service/service-test exposed
[vagrant@worker1 services]$ kubectl get svc
NAME           TYPE        CLUSTER-IP      EXTERNAL-IP   PORT(S)    AGE
kubernetes     ClusterIP   10.43.0.1       <none>        443/TCP    15m
service-test   ClusterIP   10.43.187.221   <none>        8080/TCP   12s
[vagrant@worker1 services]$


[vagrant@worker1 ~]$  <p>Hello from service-test-697bb576b7-btxvh</p>^C
[vagrant@worker1 ~]$ curl 10.43.187.221:8080
 <p>Hello from service-test-697bb576b7-btxvh</p>
[vagrant@worker1 ~]$ 

[vagrant@worker2 ~]$ curl 10.43.187.221:8080
 <p>Hello from service-test-697bb576b7-hn8q2</p>
[vagrant@worker2 ~]$ 

```



node主机暴露端口

```shell
[vagrant@worker1 services]$ kubectl expose pods nginx-pod --type=NodePort
```

