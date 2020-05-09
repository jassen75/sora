#!/bin/bash

set -e -x


TAG=$1

THIS_FILE=$(readlink -f "${BASH_SOURCE[0]}")
THIS_DIR=$(dirname "${THIS_FILE}")
source "${THIS_DIR}/env.sh"

PUBLISHTAG=${TAG:-$SORA_VERSION}

docker tag jassen75/sora:latest jassen75/sora:$PUBLISHTAG
docker login -u jassen75 --password=$DOCKHUB_PASSWORD 
docker push jassen75/sora:$PUBLISHTAG