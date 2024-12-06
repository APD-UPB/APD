FROM mfisherman/openmpi

USER root
RUN apk update && apk add bash

COPY checker /apd/checker
COPY src /apd/src