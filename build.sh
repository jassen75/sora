#!/bin/bash

set -e -x

docker build --force-rm -t sora:latest --file docker/Dockerfile .
docker tag sora:latest jassen75/sora:1.0

docker login -u jassen75 --password=$PASSWORD 
docker push jassen75/sora:1.0
