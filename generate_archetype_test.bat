call mvn clean
mkdir target
cd target
mkdir gen_test
cd gen_test

rem #DON't RUN IN KNAPPSACK PROJECT FOLDER!
rem #
rem #
rem #batch file used to generate all archetypes 
rem #
rem #
rem #
rem # Set repo below
rem #
rem #set repo = -Dmaven.repo.remote=https://oss.sonatype.org/content/repositories/orgfluttercode-545/

rem #
rem #
rem #
set version=1.0.10-SNAPSHOT
set repo=file://C:/temp/maven2repo/

call mvn archetype:generate -DarchetypeGroupId=org.fluttercode.knappsack -DarchetypeArtifactId=jee6-basic-archetype  -DinteractiveMode=false -DarchetypeVersion=%version% -DgroupId=org.application -DartifactId=jee6basic -Dpackage=org.application -DarchetypeRepository=%repo%

call mvn archetype:generate -DarchetypeGroupId=org.fluttercode.knappsack -DarchetypeArtifactId=jee6-minimal-archetype -DinteractiveMode=false -DarchetypeVersion=%version% -DgroupId=org.application -DartifactId=jee6minimal -Dpackage=org.application -DarchetypeRepository=%repo%

call mvn archetype:generate -DarchetypeGroupId=org.fluttercode.knappsack -DarchetypeArtifactId=jee6-sandbox-archetype -DinteractiveMode=false -DarchetypeVersion=%version% -DgroupId=org.application -DartifactId=jee6sandbox -Dpackage=org.application -DarchetypeRepository=%repo%

call mvn archetype:generate -DarchetypeGroupId=org.fluttercode.knappsack -DarchetypeArtifactId=jee6-sandbox-demo-archetype -DinteractiveMode=false -DarchetypeVersion=%version% -DgroupId=org.application -DartifactId=jee6sboxdemo -Dpackage=org.application -DarchetypeRepository=%repo%

call mvn archetype:generate -DarchetypeGroupId=org.fluttercode.knappsack -DarchetypeArtifactId=jee6-servlet-basic-archetype -DinteractiveMode=false -DarchetypeVersion=%version% -DgroupId=org.application -DartifactId=servletbasic -Dpackage=org.application -DarchetypeRepository=%repo%

call mvn archetype:generate -DarchetypeGroupId=org.fluttercode.knappsack -DarchetypeArtifactId=jee6-servlet-minimal-archetype -DinteractiveMode=false -DarchetypeVersion=%version% -DgroupId=org.application -DartifactId=servletminimal -Dpackage=org.application -DarchetypeRepository=%repo%

call mvn archetype:generate -DarchetypeGroupId=org.fluttercode.knappsack -DarchetypeArtifactId=jee6-servlet-sandbox-archetype -DinteractiveMode=false -DarchetypeVersion=%version% -DgroupId=org.application -DartifactId=servletsandbox -Dpackage=org.application -DarchetypeRepository=%repo%

call mvn archetype:generate -DarchetypeGroupId=org.fluttercode.knappsack -DarchetypeArtifactId=jee6-servlet-demo-archetype -DinteractiveMode=false -DarchetypeVersion=%version% -DgroupId=org.application -DartifactId=servletdemol -Dpackage=org.application -DarchetypeRepository=%repo%

call mvn archetype:generate -DarchetypeGroupId=org.fluttercode.knappsack -DarchetypeArtifactId=spring-mvc-jpa-archetype -DinteractiveMode=false -DarchetypeVersion=%version% -DgroupId=org.application -DartifactId=spring-mvc-jpa  -Dpackage=org.application -DarchetypeRepository=%repo%

