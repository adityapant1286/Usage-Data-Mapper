FROM openjdk:12
LABEL auther="Aditya Pant <apant@zuora.com>" \
      version="1.0" \
      nickname="udm"

EXPOSE 8080
#RUN apt-get -y install openjdk-11-jdk \
##RUN apt-get update \
##    && apt-get -y upgrade \
#    && rm -rf /var/lib/apt/lists/*
#RUN apk update \
##    && apt-get -y upgrade \
#    && apk add openjdk10-jdk \
#    && rm -rf /var/lib/apt/lists/*
#ENV JAVA_HOME /usr/lib/jvm/java-11-openjdk-amd64
#ENV PATH $JAVA_HOME/bin:$PATH

RUN mkdir -p /app/
RUN mkdir -p /app/logs
COPY target/usagedatamapper-1.0.0.jar /app/udm-1.0.0.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=dev", "-jar", "/app/udm-1.0.0.jar"]
VOLUME /app


