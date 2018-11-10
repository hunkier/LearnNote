FROM alpine
MAINTAINER hunkier <huangkuier@gmail.com>
LABEL Author="hunkier <huangkuier@gmail.com>"

ENV JAVA_HOME=/usr/lib/jvm/java-8-oracle \
    PATH="/usr/lib/jvm/java-8-oracle/bin:$PATH"

#ADD jdk-8u191-linux-x64.tar.gz /tmp/

RUN GLIBC_VERSION=2.26-r0 && \
    GCC_LIBS_VERSION=8.2.0-2 && \
    ZLIB_VERSION=1.2.9-1 && \
    JAVA_VERSION=8 && \
    JAVA_UPDATE=191 && \
    JAVA_BUILD=12 && \
    JAVA_SIG=750e1c8617c5452694857ad95c3ee230  && \
    sed -i 's/dl-cdn.alpinelinux.org/mirrors.aliyun.com/g' /etc/apk/repositories && \
    apk add --no-cache --virtual=build-dependencies ca-certificates wget && \
    export GLIBC_VERSION="2.26-r0"     && \
    export GLIBC_URL="https://github.com/sgerrand/alpine-pkg-glibc/releases/download/${GLIBC_VERSION}" && \
    export GLIBC_APK="glibc-${GLIBC_VERSION}.apk"                        && \
    export GLIBC_BIN_APK="glibc-bin-${GLIBC_VERSION}.apk"                && \
    export JAVA_PACKAGE="jdk"                                    && \
    export JAVA_UPDATE="191"                                      && \
    export JAVA_BUILD="12"                                        && \
    export JAVA_DOWNLOAD_PREFIX="/2787e4a523244c269598db4e85c51e0c"                     && \
#    wget -q ${GLIBC_URL}/${GLIBC_APK}                                    && \
#    wget -q ${GLIBC_URL}/${GLIBC_BIN_APK}                                && \
#    apk add --no-cache --allow-untrusted ${GLIBC_APK}                    && \
#    apk add --no-cache --allow-untrusted ${GLIBC_BIN_APK}                && \
#    mkdir /tmp/gcc-libs && \
#    curl -Ls https://archive.archlinux.org/packages/g/gcc-libs/gcc-libs-"${GCC_LIBS_VERSION}"-x86_64.pkg.tar.xz \
#        | tar -xJ -C /tmp/gcc-libs && \
#    mv /tmp/gcc-libs/usr/lib/libgcc* /tmp/gcc-libs/usr/lib/libstdc++* /usr/glibc-compat/lib && \
#    strip /usr/glibc-compat/lib/libgcc_s.so.* /usr/glibc-compat/lib/libstdc++.so* && \
#    mkdir /tmp/zlib && \
#    curl -Ls https://archive.archlinux.org/packages/z/zlib/zlib-1%3A"${ZLIB_VERSION}"-x86_64.pkg.tar.xz \
#        | tar -xJ -C /tmp/zlib && \
#    mv /tmp/zlib/usr/lib/libz.so* /usr/glibc-compat/lib && \
    wget --no-check-certificate --no-cookies --header "Cookie: oraclelicense=accept-securebackup-cookie"   \
#        http://download.oracle.com/otn-pub/java/jdk/"${JAVA_VERSION}"u"${JAVA_UPDATE}"-b"${JAVA_BUILD}"/"${JAVA_SIG}"/server-jre-"${JAVA_VERSION}"u"${JAVA_UPDATE}"-linux-x64.tar.gz \
        http://download.oracle.com/otn-pub/java/jdk/8u191-b12/2787e4a523244c269598db4e85c51e0c/server-jre-8u191-linux-x64.tar.gz \
        | tar xz -C /tmp && \
    mkdir -p /usr/lib/jvm && \
    mv /tmp/jdk1.${JAVA_VERSION}.0_${JAVA_UPDATE} "${JAVA_HOME}" && \
    apk add -U tzdata   procps                                           && \
    cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime                  && \
    echo "#!/bin/sh" >> /usr/local/bin/docker-java-home                  && \
    echo "set -e" >>   /usr/local/bin/docker-java-home                   && \
    echo " " >>   /usr/local/bin/docker-java-home                        && \
    echo "dirname "$(dirname "$(readlink -f "$(which javac || which java)")")"" >>   /usr/local/bin/docker-java-home     && \
    chmod 755 /usr/local/bin/docker-java-home                            && \
#    apk del binutils && \
    apk del build-dependencies            && \
    rm -rf /tmp/* /var/cache/apk/*

