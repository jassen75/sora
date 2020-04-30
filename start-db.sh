#!/bin/bash

set -x

THIS_DIR=$(dirname $(readlink -f ${BASH_SOURCE[0]}))

docker run -p 3306:3306 --name mysql -e MYSQL_ROOT_PASSWORD=sora -e MYSQL_DATABASE=sora -e MYSQL_USER=admin -e MYSQL_PASSWORD=admin -d mysql:8.0.17 --default-authentication-plugin=mysql_native_password

mysql -h 127.0.0.1 -uroot -psora -e "show databases;"
while [[ $? != 0 ]]
do
    echo "mysql connection is not OK now, waiting for mysql service stable"
    sleep 20
    mysql -h 127.0.0.1 -uroot -psora -e "show databases;"
done
echo "mysql connection is OK"

if [[ -f "${THIS_DIR}/src/main/resources/database/create_tables.sql" ]]; then
	echo "create tables"
	mysql -h 127.0.0.1 -uroot -psora -e "source ${THIS_DIR}/src/main/resources/database/create_tables.sql"
fi