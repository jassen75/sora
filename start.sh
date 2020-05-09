#!/bin/bash

set -e -x


THIS_FILE=$(readlink -f "${BASH_SOURCE[0]}")
THIS_DIR=$(dirname "${THIS_FILE}")
source "${THIS_DIR}/env.sh"

TAG=$1

RUNTAG=${TAG:-$SORA_VERSION}

docker run -p 80:8013 --name sora --link mysql:mysql -d jassen75/sora:$RUNTAG