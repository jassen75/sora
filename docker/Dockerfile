FROM nathonfowlie/centos-jre
LABEL maintainer jassen.wu@oracle.com
USER root
COPY build/libs/*.jar /opt/sora/lib/
COPY docker/entrypoint.sh /opt/sora/bin/ 
COPY version.sh /opt/sora/bin
RUN chmod a+x /opt/sora/bin/*

EXPOSE 8013

WORKDIR /opt/sora/
    
CMD /opt/sora/bin/entrypoint.sh
