#!/bin/bash

set -e -x

docker build --force-rm -t sora:latest --file docker/Dockerfile .



