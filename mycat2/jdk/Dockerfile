FROM alpine:3.8
MAINTAINER hunkier <huangkuier@gmail.com>
LABEL Author="hunkier <huangkuier@gmail.com>"

COPY jdk-8u191-linux-x64.tar.gz /tmp/jdk-8u191.tar.gz

RUN  sed -i 's/dl-cdn.alpinelinux.org/mirrors.aliyun.com/g' /etc/apk/repositories && \
     cd /tmp     && tar -zxf jdk-8u191.tar.gz -C .      && rm *.tar.gz -rf       && \
#    apk update && apk add --no-cache --virtual=build-dependencies procps wget curl && \
    export GLIBC_VERSION="2.26-r0"                                  && \
    export JAVA_PACKAGE="jdk"                                    && \
    export JAVA_UPDATE="191"                                      && \
    export JAVA_BUILD="12"                                        && \
    export JAVA_HOME="/usr/lib/jvm/default-jvm"                          && \
    mkdir -p /usr/lib/jvm                                                && \
    if [ ${JAVA_PACKAGE} = "server-jre" ]; then mv jdk*/jre /usr/lib/jvm/java-8-oracle; else mv j* /usr/lib/jvm/java-8-oracle; fi && \
    ln -s java-8-oracle $JAVA_HOME                                       && \
    rm -rf $JAVA_HOME/*src.zip                                           && \
    rm -rf \
            ${JAVA_HOME}/*/javaws \
            ${JAVA_HOME}/*/jjs \
            ${JAVA_HOME}/*/keytool \
            ${JAVA_HOME}/*/orbd \
            ${JAVA_HOME}/*/pack200 \
            ${JAVA_HOME}/*/policytool \
            ${JAVA_HOME}/*/rmid \
            ${JAVA_HOME}/*/rmiregistry \
            ${JAVA_HOME}/*/servertool \
            ${JAVA_HOME}/*/tnameserv \
            ${JAVA_HOME}/*/unpack200 \
#            ${JAVA_HOME}/*/*javafx* \
#            ${JAVA_HOME}/*/*jfx* \
            ${JAVA_HOME}/*/amd64/libdecora_sse.so \
            ${JAVA_HOME}/*/amd64/libfxplugins.so \
            ${JAVA_HOME}/*/amd64/libglass.so \
            ${JAVA_HOME}/*/amd64/libgstreamer-lite.so \
#            ${JAVA_HOME}/*/amd64/libjavafx*.so \
#            ${JAVA_HOME}/*/amd64/libjfx*.so \
            ${JAVA_HOME}/*/amd64/libprism_*.so \
            ${JAVA_HOME}/*/deploy* \
            ${JAVA_HOME}/*/desktop \
#            ${JAVA_HOME}/*/ext/jfxrt.jar \
            ${JAVA_HOME}/*/ext/nashorn.jar \
            ${JAVA_HOME}/*/javaws.jar \
            ${JAVA_HOME}/*/jfr \
            ${JAVA_HOME}/*/jfr \
            ${JAVA_HOME}/*/jfr.jar \
            ${JAVA_HOME}/*/missioncontrol \
            ${JAVA_HOME}/*/oblique-fonts \
            ${JAVA_HOME}/*/plugin.jar \
            ${JAVA_HOME}/*/visualvm \
            ${JAVA_HOME}/man \
            ${JAVA_HOME}/plugin \
            ${JAVA_HOME}/*.txt \
            ${JAVA_HOME}/*/*/javaws \
            ${JAVA_HOME}/*/*/jjs \
            ${JAVA_HOME}/*/*/keytool \
            ${JAVA_HOME}/*/*/orbd \
            ${JAVA_HOME}/*/*/pack200 \
            ${JAVA_HOME}/*/*/policytool \
            ${JAVA_HOME}/*/*/rmid \
            ${JAVA_HOME}/*/*/rmiregistry \
            ${JAVA_HOME}/*/*/servertool \
            ${JAVA_HOME}/*/*/tnameserv \
            ${JAVA_HOME}/*/*/unpack200 \
#            ${JAVA_HOME}/*/*/*javafx* \
#            ${JAVA_HOME}/*/*/*jfx* \
            ${JAVA_HOME}/*/*/amd64/libdecora_sse.so \
            ${JAVA_HOME}/*/*/amd64/libfxplugins.so \
            ${JAVA_HOME}/*/*/amd64/libglass.so \
            ${JAVA_HOME}/*/*/amd64/libgstreamer-lite.so \
#            ${JAVA_HOME}/*/*/amd64/libjavafx*.so \
#            ${JAVA_HOME}/*/*/amd64/libjfx*.so \
            ${JAVA_HOME}/*/*/amd64/libprism_*.so \
            ${JAVA_HOME}/*/*/deploy* \
            ${JAVA_HOME}/*/*/desktop \
#            ${JAVA_HOME}/*/*/ext/jfxrt.jar \
            ${JAVA_HOME}/*/*/ext/nashorn.jar \
            ${JAVA_HOME}/*/*/javaws.jar \
            ${JAVA_HOME}/*/*/jfr \
            ${JAVA_HOME}/*/*/jfr \
            ${JAVA_HOME}/*/*/jfr.jar \
            ${JAVA_HOME}/*/*/missioncontrol \
            ${JAVA_HOME}/*/*/oblique-fonts \
            ${JAVA_HOME}/*/*/plugin.jar \
            ${JAVA_HOME}/*/*/visualvm \
            ${JAVA_HOME}/*/man \
            ${JAVA_HOME}/*/plugin \
            ${JAVA_HOME}/*.txt \
                                                                         && \
    apk add -U tzdata   procps                                           && \
    cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime                  && \
    apk del build-dependencies                                           && \
    ln -s $JAVA_HOME/bin/* /usr/bin/                                     && \
    echo "#!/bin/sh" >> /usr/local/bin/docker-java-home                  && \
    echo "set -e" >>   /usr/local/bin/docker-java-home                   && \
    echo " " >>   /usr/local/bin/docker-java-home                        && \
    echo "dirname "$(dirname "$(readlink -f "$(which javac || which java)")")"" >>   /usr/local/bin/docker-java-home     && \
    chmod 755 /usr/local/bin/docker-java-home                            && \
    rm -rf /tmp/*


ENV JAVA_HOME=$JAVA_HOME \
    PATH=${PATH}:$JAVA_HOME/bin

