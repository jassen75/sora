# sora

# in local

service mysql stop

mysql -h 127.0.0.1 -uroot -psora -e 'source /home/jassen/workspace/sora/src/main/resources/database/create_tables.sql'

git push

gradle clean build

bash build.sh

bash publish.sh 1.05



# in cloud

cd /home/sora

git pull

docker rmi jassen75/sora:1.05 (if needed)

bash stop.sh

bash start.sh 1.05

docker logs -f sora

# cloud config

apt-get update

1  install zip
apt-get install -y zip

2 install sdkman

curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"

3 install gradle

sdk install gradle 5.3.1

4 install docker

5 mkdir /home/jassen


6  git clone https://github.com/jassen75/sora.git


7  import db data

bash start-db.sh

