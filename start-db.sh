#!/bin/bash

set -e -x

docker run -p 3306:3306 --name mysql -e MYSQL_ROOT_PASSWORD=sora -e MYSQL_DATABASE=sora -e MYSQL_USER=admin -e MYSQL_PASSWORD=admin -d mysql:8.0.17