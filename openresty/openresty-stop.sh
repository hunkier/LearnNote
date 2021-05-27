#openresty  -s quit  -p `pwd` -c conf/nginx.conf
openresty  -p `pwd` -c conf/nginx.conf -s quit