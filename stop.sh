#!/bin/bash

set -e -x

docker-compose -f docker-compose.yml stop


docker-compose -f docker-compose.yml down -v
yes | docker system prune