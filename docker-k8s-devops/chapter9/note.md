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
cat pod_busybox.yml
apiVersion: v1
kind: Pod
metadata:
  name: busybox
  labels:
    app: busybox
spec:
  containers:
  - name: busybox
    image: busybox
    
kubectl create -f pod_nginx.yml

kubectl get pods
NAME      READY     STATUS              RESTARTS   AGE
nginx     0/1       ContainerCreating   0          2m


```



rancher安装kubernetes集群

```shell
docker run -d -p 80:80 -p 443:443 rancher/rancher:v2.1.0
```

