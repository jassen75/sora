#!/bin/bash

set -e -x

docker run -p 80:8013 --name sora --link mysql:mysql -d jassen75/sora:1.0