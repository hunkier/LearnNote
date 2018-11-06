#FROM lwieske/java-8:jdk-8u181-slim AS build
# FROM maven:3.6-jdk-8-alpine AS build
FROM alpine
LABEL Author="huangkui <huangkuier@gmail.com>"

ADD jdk-8u191-linux-x64.tar.gz /tmp/

RUN  echo "https://mirrors.aliyun.com/alpine/v3.6/main/" > /etc/apk/repositories && \
     echo "https://mirrors.aliyun.com/alpine/v3.6/community/" >> /etc/apk/repositories && \
     cd /tmp                                                              && \
    apk add --no-cache --virtual=build-dependencies ca-certificates wget && \
    export GLIBC_VERSION="2.26-r0"                                  && \
    export JAVA_PACKAGE="jdk"                                    && \
    export JAVA_UPDATE="191"                                      && \
    export JAVA_BUILD="12"                                        && \
    export JAVA_DOWNLOAD_PREFIX="/2787e4a523244c269598db4e85c51e0c"                     && \
    export GLIBC_URL="https://github.com/sgerrand/alpine-pkg-glibc/releases/download/${GLIBC_VERSION}" && \
    export GLIBC_APK="glibc-${GLIBC_VERSION}.apk"                        && \
    export GLIBC_BIN_APK="glibc-bin-${GLIBC_VERSION}.apk"                && \
    export JAVA_URL="http://download.oracle.com/otn-pub/java/jdk/8u${JAVA_UPDATE}-b${JAVA_BUILD}${JAVA_DOWNLOAD_PREFIX}/" && \
    export JAVA_TGZ="${JAVA_PACKAGE}-8u${JAVA_UPDATE}-linux-x64.tar.gz"  && \
    export JAVA_HOME="/usr/lib/jvm/default-jvm"                          && \
    wget -q ${GLIBC_URL}/${GLIBC_APK}                                    && \
    wget -q ${GLIBC_URL}/${GLIBC_BIN_APK}                                && \
    apk add --no-cache --allow-untrusted ${GLIBC_APK}                    && \
    apk add --no-cache --allow-untrusted ${GLIBC_BIN_APK}                && \
    echo 'hosts: files mdns4_minimal [NOTFOUND=return] dns mdns4' >> /etc/nsswitch.conf && \
    mkdir -p /usr/lib/jvm                                                && \
#    wget -qO-  --no-check-certificate --no-cookies  --header "Cookie: oraclelicense=accept-securebackup-cookie;" ${JAVA_URL}/${JAVA_TGZ} | tar -xzf -  && \
#     wget --no-check-certificate --no-cookies --header "Cookie: oraclelicense=accept-securebackup-cookie" http://download.oracle.com/otn-pub/java/jdk/8u191-b12/2787e4a523244c269598db4e85c51e0c/jdk-8u191-linux-x64.tar.gz | tar -xzf -  && \
#    if [ ${JAVA_PACKAGE} = "server-jre" ]; then mv jdk*/jre /usr/lib/jvm/java-8-oracle; else mv j* /usr/lib/jvm/java-8-oracle; fi && \
    mv jdk*/jre /usr/lib/jvm/java-8-oracle && \
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
    apk del build-dependencies                                           && \
    ln -s $JAVA_HOME/bin/* /usr/bin/                                     && \
    rm -rf /tmp/*

ENV JAVA_HOME=/usr/lib/jvm/default-jvm/ \
    PATH=${PATH}:/usr/lib/jvm/default-jvm/bin



# install maven
ARG MAVEN_VERSION=3.6.0
ARG USER_HOME_DIR="/root"
ARG SHA=fae9c12b570c3ba18116a4e26ea524b29f7279c17cbaadc3326ca72927368924d9131d11b9e851b8dc9162228b6fdea955446be41207a5cfc61283dd8a561d2f
ARG BASE_URL=https://apache.osuosl.org/maven/maven-3/${MAVEN_VERSION}/binaries

RUN apk add --no-cache \
      curl procps git \
  && rm -rf /var/lib/apt/lists/*

RUN mkdir -p /usr/share/maven /usr/share/maven/ref \
  && curl -fsSL -o /tmp/apache-maven.tar.gz ${BASE_URL}/apache-maven-${MAVEN_VERSION}-bin.tar.gz \
  && echo "${SHA}  /tmp/apache-maven.tar.gz" | sha512sum -c - \
  && tar -xzf /tmp/apache-maven.tar.gz -C /usr/share/maven --strip-components=1 \
  && rm -f /tmp/apache-maven.tar.gz \
  && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

ENV MAVEN_HOME /usr/share/maven
ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"

COPY mvn-entrypoint.sh /usr/local/bin/mvn-entrypoint.sh
COPY settings-docker.xml /usr/share/maven/ref/

RUN /usr/local/bin/mvn-entrypoint.sh

# compile mycat2
WORKDIR /src
COPY settings.xml /root/
RUN  git clone https://github.com/MyCATApache/Mycat2.git \
    && cd Mycat2/source \
    && mkdir -p /root/.m2 \
    && cp /root/settings.xml /root/.m2 \
    && mvn package -Dmaven.test.skip=true \
    && mv /src/Mycat2/source/target/*linux.tar.gz /src/mycat2.tar.gz

# timezone
RUN apk add -U tzdata \
    && cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

FROM mysql:5.6
# timezone
COPY --from=build /etc/localtime /etc/localtime
ARG version=2.0.0
ENV MYSQL_ALLOW_EMPTY_PASSWORD true

ENV LANG C.UTF-8



RUN  mv /src/mycat2.tar.gz /usr/local/ && cd /usr/local && tar -xzf mycat2.tar.gz
COPY run.sh /usr/local/mycat2/
RUN mkdir /usr/local/mycat2/logs
RUN chmod 777 /usr/local/mycat2/run.sh
EXPOSE 3306
EXPOSE 8066
ENTRYPOINT ["/usr/local/mycat2/run.sh"]