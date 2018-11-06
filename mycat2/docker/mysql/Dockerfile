FROM base

ADD mysql-5.6.42-linux-glibc2.12-x86_64.tar.gz /tmp/
RUN   cd /tmp \
    && mv mysq* mysql \
    && cp -r mysql /usr/local/  \