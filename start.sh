#!/bin/bash

set -e -x

TAG=$1

RUNTAG=${TAG:-latest}

docker run -p 80:8013 --name sora --link mysql:mysql -d jassen75/sora:$RUNTAG