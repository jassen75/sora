#!/bin/bash

set -e -x

docker stop sora_mysql_db

docker rm sora_mysql_db