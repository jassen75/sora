#!/bin/bash

THIS_FILE=$(readlink -f "${BASH_SOURCE[0]}")
THIS_DIR=$(dirname "${THIS_FILE}")

source "${THIS_DIR}/version.sh"

java -Dmysql-host=sora_mysql_db -Dsora-version=$SORA_VERSION -jar /opt/sora/lib/*.jar