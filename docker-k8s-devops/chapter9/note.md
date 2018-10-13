minikube创建k8s单节点

```shell
minikube start
kubectl config view
kubectl config get-contexts
kubectl cluster-info
minikube ssh
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
[vagrant@node1 ~]$ cat dashboard-admin.yaaml
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
[vagrant@node1 ~]$ kubectl port-forward nginx 8088:80
```

