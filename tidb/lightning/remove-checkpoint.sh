# 清除上次导入未完成的检查点
# 下载链接中的 {version} 为 TiDB Lightning 的版本号。
# 例如，v5.1.0 版本的下载链接为 https://download.pingcap.org/tidb-toolkit-v5.1.0-linux-amd64.tar.gz
tidb-lightning-ctl --checkpoint-remove=all --config=tidb-lightning.toml