# 数据导入 tidb
# https://docs.pingcap.com/zh/tidb/stable/migrate-from-mysql-dumpling-files
nohup tiup tidb-lightning -config tidb-lightning.toml --check-requirements=false > nohup.out &