# 导出数据到 sql 文件
#https://docs.pingcap.com/zh/tidb/stable/dumpling-overview
tiup  dumpling \
        -u root \
        -p 123456 \
        -P 3308 \
        --host 192.168.33.1 \
        --filetype sql \
        -t 8 \
        -o /tmp/test \
        -r 200000 \
        -F 256MiB