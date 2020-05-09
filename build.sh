#!/bin/bash

set -e -x

docker build --force-rm -t jassen75/sora:latest --file docker/Dockerfile .



