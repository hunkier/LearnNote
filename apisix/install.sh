# 关闭 selinux
sudo sed -i 's/SELINUX=enforcing/SELINUX=disabled/g' /etc/selinux/config

# 关闭 防火墙
sudo systemctl stop firewalld.service && sudo systemctl disable firewalld.service

# 时区
sudo ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
sudo timedatectl set-timezone Asia/Shanghai
sudo echo 'LANG="en_US.UTF-8"' >> /etc/profile;source /etc/profile

# 关闭 swap 交换区
sudo echo "vm.swappiness = 0">> /etc/sysctl.conf
sudo swapoff -a && sudo swapon -a
sudo sysctl -p

# 操作系统配置优化
sudo grubby --args="transparent_hugepage=never" --update-kernel `grubby --default-kernel`

#修改当前的内核配置立即关闭透明大页
sudo  echo never > /sys/kernel/mm/transparent_hugepage/enabled
sudo  echo never > /sys/kernel/mm/transparent_hugepage/defrag

# 创建 CPU 节能策略配置服务
sudo cat  >> /etc/systemd/system/cpupower.service << EOF
     [Unit]
     Description=CPU performance
     [Service]
     Type=oneshot
     ExecStart=/usr/bin/cpupower frequency-set --governor performance
     [Install]
     WantedBy=multi-user.target
EOF

sudo systemctl daemon-reload
sudo systemctl enable cpupower.service
sudo systemctl start cpupower.service


# 修改 sysctl 参数
sudo cat >> /etc/sysctl.conf  <<EOF

fs.file-max = 1000000
net.core.somaxconn = 32768
net.ipv4.tcp_tw_recycle = 0
net.ipv4.tcp_syncookies = 0
vm.overcommit_memory = 1

EOF
sudo sysctl -p

sudo cat >> /usr/lib/sysctl.d/00-system.conf  <<EOF

net.ipv4.ip_forward=1

EOF

#  修改 root账号密码
echo apisix | passwd --stdin root
# 添加 tidb 用户
sudo useradd apisix && echo apisix | passwd --stdin apisix
# tidb 授予超级管理员权限
sudo echo "apisix ALL=(ALL) NOPASSWD: ALL" >> /etc/sudoers
# 允许远程 ssh 登录
sudo sed -i 's/PasswordAuthentication no/PasswordAuthentication yes/g' /etc/ssh/sshd_config
sudo systemctl reload sshd.service



# NUMA 绑核是用来隔离 CPU 资源
sudo yum install -y yum-utils device-mapper-persistent-data lvm2
sudo yum install -y numactl telnet bridge-utils net-tools


# 安装 tiup
