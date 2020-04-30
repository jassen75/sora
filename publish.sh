#!/bin/bash

set -e -x

TAG=$1

PUBLISHTAG=${TAG:-1.0}

docker tag sora:latest jassen75/sora:$PUBLISHTAG
docker login -u jassen75 --password=$PASSWORD 
docker push jassen75/sora:$PUBLISHTAG