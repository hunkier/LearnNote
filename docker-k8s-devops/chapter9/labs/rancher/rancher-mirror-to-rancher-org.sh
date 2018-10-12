#!/bin/sh
set -e -x

docker pull busybox
docker pull minio/minio:RELEASE.2018-05-25T19-49-13Z
docker pull rancher/alertmanager-helper:v0.0.2
docker pull quay.io/calico/cni:v3.1.1
docker tag quay.io/calico/cni:v3.1.1 rancher/calico-cni:v3.1.1
docker push rancher/calico-cni:v3.1.1
docker pull quay.io/calico/cni:v3.1.3
docker tag quay.io/calico/cni:v3.1.3 rancher/calico-cni:v3.1.3
docker push rancher/calico-cni:v3.1.3
docker pull quay.io/calico/ctl:v2.0.0
docker tag quay.io/calico/ctl:v2.0.0 rancher/calico-ctl:v2.0.0
docker push rancher/calico-ctl:v2.0.0
docker pull quay.io/calico/node:v3.1.1
docker tag quay.io/calico/node:v3.1.1 rancher/calico-node:v3.1.1
docker push rancher/calico-node:v3.1.1
docker pull quay.io/calico/node:v3.1.3
docker tag quay.io/calico/node:v3.1.3 rancher/calico-node:v3.1.3
docker push rancher/calico-node:v3.1.3
docker pull gcr.io/google_containers/cluster-proportional-autoscaler-amd64:1.0.0
docker tag gcr.io/google_containers/cluster-proportional-autoscaler-amd64:1.0.0 rancher/cluster-proportional-autoscaler-amd64:1.0.0
docker push rancher/cluster-proportional-autoscaler-amd64:1.0.0
docker pull quay.io/coreos/etcd:v3.1.12
docker tag quay.io/coreos/etcd:v3.1.12 rancher/coreos-etcd:v3.1.12
docker push rancher/coreos-etcd:v3.1.12
docker pull quay.io/coreos/etcd:v3.2.18
docker tag quay.io/coreos/etcd:v3.2.18 rancher/coreos-etcd:v3.2.18
docker push rancher/coreos-etcd:v3.2.18
docker pull quay.io/coreos/etcd:v3.2.24
docker tag quay.io/coreos/etcd:v3.2.24 rancher/coreos-etcd:v3.2.24
docker push rancher/coreos-etcd:v3.2.24
docker pull quay.io/coreos/flannel-cni:v0.2.0
docker tag quay.io/coreos/flannel-cni:v0.2.0 rancher/coreos-flannel-cni:v0.2.0
docker push rancher/coreos-flannel-cni:v0.2.0
docker pull quay.io/coreos/flannel-cni:v0.3.0
docker tag quay.io/coreos/flannel-cni:v0.3.0 rancher/coreos-flannel-cni:v0.3.0
docker push rancher/coreos-flannel-cni:v0.3.0
docker pull quay.io/coreos/flannel:v0.10.0
docker tag quay.io/coreos/flannel:v0.10.0 rancher/coreos-flannel:v0.10.0
docker push rancher/coreos-flannel:v0.10.0
docker pull quay.io/coreos/flannel:v0.9.1
docker tag quay.io/coreos/flannel:v0.9.1 rancher/coreos-flannel:v0.9.1
docker push rancher/coreos-flannel:v0.9.1
docker pull quay.io/pires/docker-elasticsearch-kubernetes:5.6.2
docker tag quay.io/pires/docker-elasticsearch-kubernetes:5.6.2 rancher/docker-elasticsearch-kubernetes:5.6.2
docker push rancher/docker-elasticsearch-kubernetes:5.6.2
docker pull rancher/fluentd-helper:v0.1.2
docker pull rancher/fluentd:v0.1.10
docker pull rancher/hyperkube:v1.10.5-rancher1
docker pull rancher/hyperkube:v1.11.3-rancher1
docker pull rancher/hyperkube:v1.12.0-rancher1
docker pull rancher/hyperkube:v1.9.7-rancher2
docker pull jenkins/jnlp-slave:3.10-1-alpine
docker tag jenkins/jnlp-slave:3.10-1-alpine rancher/jenkins-jnlp-slave:3.10-1-alpine
docker push rancher/jenkins-jnlp-slave:3.10-1-alpine
docker pull plugins/docker:17.12
docker tag plugins/docker:17.12 rancher/jenkins-plugins-docker:17.12
docker push rancher/jenkins-plugins-docker:17.12
docker pull gcr.io/google_containers/k8s-dns-dnsmasq-nanny-amd64:1.14.10
docker tag gcr.io/google_containers/k8s-dns-dnsmasq-nanny-amd64:1.14.10 rancher/k8s-dns-dnsmasq-nanny-amd64:1.14.10
docker push rancher/k8s-dns-dnsmasq-nanny-amd64:1.14.10
docker pull gcr.io/google_containers/k8s-dns-dnsmasq-nanny-amd64:1.14.13
docker tag gcr.io/google_containers/k8s-dns-dnsmasq-nanny-amd64:1.14.13 rancher/k8s-dns-dnsmasq-nanny-amd64:1.14.13
docker push rancher/k8s-dns-dnsmasq-nanny-amd64:1.14.13
docker pull gcr.io/google_containers/k8s-dns-dnsmasq-nanny-amd64:1.14.7
docker tag gcr.io/google_containers/k8s-dns-dnsmasq-nanny-amd64:1.14.7 rancher/k8s-dns-dnsmasq-nanny-amd64:1.14.7
docker push rancher/k8s-dns-dnsmasq-nanny-amd64:1.14.7
docker pull gcr.io/google_containers/k8s-dns-dnsmasq-nanny-amd64:1.14.8
docker tag gcr.io/google_containers/k8s-dns-dnsmasq-nanny-amd64:1.14.8 rancher/k8s-dns-dnsmasq-nanny-amd64:1.14.8
docker push rancher/k8s-dns-dnsmasq-nanny-amd64:1.14.8
docker pull gcr.io/google_containers/k8s-dns-kube-dns-amd64:1.14.10
docker tag gcr.io/google_containers/k8s-dns-kube-dns-amd64:1.14.10 rancher/k8s-dns-kube-dns-amd64:1.14.10
docker push rancher/k8s-dns-kube-dns-amd64:1.14.10
docker pull gcr.io/google_containers/k8s-dns-kube-dns-amd64:1.14.13
docker tag gcr.io/google_containers/k8s-dns-kube-dns-amd64:1.14.13 rancher/k8s-dns-kube-dns-amd64:1.14.13
docker push rancher/k8s-dns-kube-dns-amd64:1.14.13
docker pull gcr.io/google_containers/k8s-dns-kube-dns-amd64:1.14.7
docker tag gcr.io/google_containers/k8s-dns-kube-dns-amd64:1.14.7 rancher/k8s-dns-kube-dns-amd64:1.14.7
docker push rancher/k8s-dns-kube-dns-amd64:1.14.7
docker pull gcr.io/google_containers/k8s-dns-kube-dns-amd64:1.14.8
docker tag gcr.io/google_containers/k8s-dns-kube-dns-amd64:1.14.8 rancher/k8s-dns-kube-dns-amd64:1.14.8
docker push rancher/k8s-dns-kube-dns-amd64:1.14.8
docker pull gcr.io/google_containers/k8s-dns-sidecar-amd64:1.14.10
docker tag gcr.io/google_containers/k8s-dns-sidecar-amd64:1.14.10 rancher/k8s-dns-sidecar-amd64:1.14.10
docker push rancher/k8s-dns-sidecar-amd64:1.14.10
docker pull gcr.io/google_containers/k8s-dns-sidecar-amd64:1.14.13
docker tag gcr.io/google_containers/k8s-dns-sidecar-amd64:1.14.13 rancher/k8s-dns-sidecar-amd64:1.14.13
docker push rancher/k8s-dns-sidecar-amd64:1.14.13
docker pull gcr.io/google_containers/k8s-dns-sidecar-amd64:1.14.7
docker tag gcr.io/google_containers/k8s-dns-sidecar-amd64:1.14.7 rancher/k8s-dns-sidecar-amd64:1.14.7
docker push rancher/k8s-dns-sidecar-amd64:1.14.7
docker pull gcr.io/google_containers/k8s-dns-sidecar-amd64:1.14.8
docker tag gcr.io/google_containers/k8s-dns-sidecar-amd64:1.14.8 rancher/k8s-dns-sidecar-amd64:1.14.8
docker push rancher/k8s-dns-sidecar-amd64:1.14.8
docker pull kibana:5.6.4
docker tag kibana:5.6.4 rancher/kibana:5.6.4
docker push rancher/kibana:5.6.4
docker pull rancher/log-aggregator:v0.1.3
docker pull gcr.io/google_containers/metrics-server-amd64:v0.2.1
docker tag gcr.io/google_containers/metrics-server-amd64:v0.2.1 rancher/metrics-server-amd64:v0.2.1
docker push rancher/metrics-server-amd64:v0.2.1
docker pull gcr.io/google_containers/metrics-server-amd64:v0.3.1
docker tag gcr.io/google_containers/metrics-server-amd64:v0.3.1 rancher/metrics-server-amd64:v0.3.1
docker push rancher/metrics-server-amd64:v0.3.1
docker pull k8s.gcr.io/defaultbackend:1.4
docker tag k8s.gcr.io/defaultbackend:1.4 rancher/nginx-ingress-controller-defaultbackend:1.4
docker push rancher/nginx-ingress-controller-defaultbackend:1.4
docker pull rancher/nginx-ingress-controller:0.16.2-rancher1
docker pull gcr.io/google_containers/pause-amd64:3.0
docker tag gcr.io/google_containers/pause-amd64:3.0 rancher/pause-amd64:3.0
docker push rancher/pause-amd64:3.0
docker pull gcr.io/google_containers/pause-amd64:3.1
docker tag gcr.io/google_containers/pause-amd64:3.1 rancher/pause-amd64:3.1
docker push rancher/pause-amd64:3.1
docker pull rancher/pipeline-jenkins-server:v0.1.0
docker pull rancher/pipeline-tools:v0.1.0
docker pull prom/alertmanager:v0.15.2
docker tag prom/alertmanager:v0.15.2 rancher/prom-alertmanager:v0.15.2
docker push rancher/prom-alertmanager:v0.15.2
docker pull rancher/rke-tools:v0.1.13
docker pull rancher/rke-tools:v0.1.15
docker pull registry:2
docker pull rancher/rancher:v2.1.0
docker pull rancher/rancher-agent:v2.1.0
