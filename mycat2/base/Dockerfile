#FROM lwieske/java-8:jdk-8u181-slim AS build
# FROM maven:3.6-jdk-8-alpine AS build
FROM mbase AS build
LABEL Author="huangkui <huangkuier@gmail.com>"


# compile mycat2
WORKDIR /src
#RUN  git clone https://github.com/MyCATApache/Mycat2.git
RUN  cd Mycat2/source \
#    && mkdir -p /root/.m2 \
#    && cp /root/settings.xml /root/.m2 \
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

# install jre
# add a simple script that can auto-detect the appropriate JAVA_HOME value
# based on whether the JDK or only the JRE is installed
RUN apt-get update && apt-get install -y --no-install-recommends \
		bzip2 \
		unzip \
		xz-utils \
		procps \
	&& rm -rf /var/lib/apt/lists/*

# Default to UTF-8 file.encoding
ENV LANG C.UTF-8

# add a simple script that can auto-detect the appropriate JAVA_HOME value
# based on whether the JDK or only the JRE is installed
RUN { \
		echo '#!/bin/sh'; \
		echo 'set -e'; \
		echo; \
		echo 'dirname "$(dirname "$(readlink -f "$(which javac || which java)")")"'; \
	} > /usr/local/bin/docker-java-home \
	&& chmod +x /usr/local/bin/docker-java-home

# do some fancy footwork to create a JAVA_HOME that's cross-architecture-safe
RUN ln -svT "/usr/lib/jvm/java-8-openjdk-$(dpkg --print-architecture)" /docker-java-home
ENV JAVA_HOME /docker-java-home/jre

ENV JAVA_VERSION 8u181
ENV JAVA_DEBIAN_VERSION 8u181-b13-2~deb9u1

# see https://bugs.debian.org/775775
# and https://github.com/docker-library/java/issues/19#issuecomment-70546872
ENV CA_CERTIFICATES_JAVA_VERSION 20170531+nmu1

RUN set -ex; \
	\
# deal with slim variants not having man page directories (which causes "update-alternatives" to fail)
	if [ ! -d /usr/share/man/man1 ]; then \
		mkdir -p /usr/share/man/man1; \
	fi; \
	\
	apt-get update; \
	apt-get install -y --no-install-recommends \
	    supervisor  \
		openjdk-8-jre-headless="$JAVA_DEBIAN_VERSION" \
		ca-certificates-java="$CA_CERTIFICATES_JAVA_VERSION" \
	; \
	apt-get install -y --no-install-recommends \
    	     vim ; \
	rm -rf /var/lib/apt/lists/*; \
	\
# verify that "docker-java-home" returns what we expect
	[ "$(readlink -f "$JAVA_HOME")" = "$(docker-java-home)" ]; \
	\
# update-alternatives so that future installs of other OpenJDK versions don't change /usr/bin/java
	update-alternatives --get-selections | awk -v home="$(readlink -f "$JAVA_HOME")" 'index($3, home) == 1 { $2 = "manual"; print | "update-alternatives --set-selections" }'; \
# ... and verify that it actually worked for one of the alternatives we care about
	update-alternatives --query java | grep -q 'Status: manual'

#EXPOSE 3306

# see CA_CERTIFICATES_JAVA_VERSION notes above
# install mycat
COPY  --from=build /src/mycat2.tar.gz /usr/local/
RUN cd /usr/local && tar -xzf mycat2.tar.gz
COPY run.sh /usr/local/mycat2/
ENV DMYCAT_HOME /usr/local/mycat2/
#ENV MYSQL_ROOT_PASSWORD 123456
ENV MYSQL_ALLOW_EMPTY_PASSWORD true
RUN mkdir /usr/local/mycat2/logs
#RUN chmod 777 /usr/local/mycat2/run.sh

RUN mkdir -p /var/log/supervisor
COPY supervisord.conf /etc/supervisord.conf
EXPOSE 3306
EXPOSE 8066
#ENTRYPOINT ["/usr/local/mycat2/run.sh"]

ENTRYPOINT ["/usr/bin/supervisord"]