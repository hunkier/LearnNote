# 将导出的 SQL 文件导入到 MySQL
for SQL in *.sql; do mysql -hlocalhost -P3306 -uroot -ppassword dbname < $SQL; done