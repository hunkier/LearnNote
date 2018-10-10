#!/usr/bin/env bash
docker pull container-registry.oracle.com/kubernetes_developer/etcd-amd64:3.1.12
docker pull container-registry.oracle.com/kubernetes_developer/kube-proxy-amd64:v1.10.5
docker pull container-registry.oracle.com/kubernetes_developer/flannel:v0.9.0-amd64
docker pull container-registry.oracle.com/kubernetes_developer/k8s-dns-dnsmasq-nanny-amd64:1.14.8
docker pull container-registry.oracle.com/kubernetes_developer/k8s-dns-kube-dns-amd64:1.14.8
docker pull container-registry.oracle.com/kubernetes_developer/k8s-dns-sidecar-amd64:1.14.8
docker pull container-registry.oracle.com/kubernetes_developer/kube-apiserver-amd64:v1.10.5
docker pull container-registry.oracle.com/kubernetes_developer/kube-controller-manager-amd64:v1.10.5
docker pull container-registry.oracle.com/kubernetes_developer/kube-scheduler-amd64:v1.10.5
docker pull container-registry.oracle.com/kubernetes_developer/kubernetes-dashboard-amd64:v1.7.0
docker pull container-registry.oracle.com/kubernetes_developer/kubernetes-dashboard-init-amd64:v1.0.0
docker pull container-registry.oracle.com/kubernetes_developer/pause-amd64:3.1
