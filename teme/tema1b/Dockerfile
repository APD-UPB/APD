FROM maven:3.9.9-eclipse-temurin-21-alpine

RUN mkdir -p /apd
WORKDIR /apd
COPY checker /apd/checker
RUN cd checker/skel && mvn dependency:resolve
RUN cd checker/skel && mvn test-compile
RUN chmod a+x ./checker/checker.sh

COPY src /apd/src
RUN cp -R checker/skel/* /apd