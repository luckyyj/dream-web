FROM java:8

WORKDIR /data/dream-web

COPY ./dream-web.jar /data/dream-web/dream-web.jar
COPY ./application.yml /data/dream-web/application.yml

ENV PARAMS=""
ENV JAVA_OPTS="-Xms256m -Xmx512m"
ENV TZ=PRC

RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

ENTRYPOINT ["sh","-c","java -jar $JAVA_OPTS dream-web.jar $PARAMS"]