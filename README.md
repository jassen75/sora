# sora

# in local

git push

gradle clean build

bash build.sh

bash publish.sh 1.0

# in cloud


git pull

bash stop.sh

bash start.sh 1.0



# cloud config

apt-get update

1  install zip
apt-get install -y zip

2 install sdkman

curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"

3 install gradle

sdk install gradle 5.3.1

4 set gradle 

in /home/jassen/.gradle/   create init.gradle
allprojects {
    repositories {
        def ALIYUN_REPOSITORY_URL = 'http://maven.aliyun.com/nexus/content/groups/public'
        def ALIYUN_JCENTER_URL = 'http://maven.aliyun.com/nexus/content/repositories/jcenter'
        all {
            ArtifactRepository repo ->
                if (repo instanceof MavenArtifactRepository) {
                    def url = repo.url.toString()
                    if (url.startsWith('https://repo1.maven.org/maven2')) {
                        project.logger.lifecycle "Repository ${repo.url} replaced by $ALIYUN_REPOSITORY_URL."
                        remove repo
                    }
                    if (url.startsWith('https://jcenter.bintray.com/')) {
                        project.logger.lifecycle "Repository ${repo.url} replaced by $ALIYUN_JCENTER_URL."
                        remove repo
                    }
                }
        }
        maven {
            url ALIYUN_REPOSITORY_URL
            url ALIYUN_JCENTER_URL
        }
    }
}

4 install docker

5 mkdir /home/jassen


6  git clone https://github.com/jassen75/sora.git


7  import db data

