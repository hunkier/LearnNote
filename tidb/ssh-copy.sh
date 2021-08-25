# 免密登录
ssh-keygen -t rsa
ssh-copy-id -i ~/.ssh/id_rsa.pub tidb@192.168.33.10
# 导入 tiup 到环境变量
source /root/.bash_profile