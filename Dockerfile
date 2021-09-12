FROM openjdk:15-jdk-alpine3.12
ENV AXON_AXONSERVER_SERVERS=axonserver
COPY target/*.jar text-search-service.jar
EXPOSE 8080
USER 1000
CMD java ${JAVA_OPTS} -jar text-search-service.jar
