FROM library/ubuntu
RUN cp /etc/apt/sources.list /etc/apt/sources.list.bak \
&& sed -i "s/http:\/\/security.ubuntu.com\/ubuntu/http:\/\/mirrors.aliyun.com\/ubuntu/g" /etc/apt/sources.list \
&& sed -i "s/http:\/\/archive.ubuntu.com\/ubuntu/http:\/\/mirrors.aliyun.com\/ubuntu/g" /etc/apt/sources.list  \
&& apt-get update && apt-get install -y stress
ENTRYPOINT ["/usr/bin/stress"]
CMD []
