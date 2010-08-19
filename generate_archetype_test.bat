#batch file used to generate all archetypes 
set repo = -Dmaven.repo.remote=https://oss.sonatype.org/content/repositories/orgfluttercode-545/
set version=1.0.5

call mvn archetype:generate -DarchetypeGroupId=org.fluttercode.knappsack -DarchetypeArtifactId=jee6-basic-archetype  -DinteractiveMode=false -DarchetypeVersion=%version% -DgroupId=org.application -DartifactId=jee6basic -Dpackage=org.application  %repo%

call mvn archetype:generate -DarchetypeGroupId=org.fluttercode.knappsack -DarchetypeArtifactId=jee6-minimal-archetype -DinteractiveMode=false -DarchetypeVersion=%version% -DgroupId=org.application -DartifactId=jee6minimal -Dpackage=org.application %repo%

call mvn archetype:generate -DarchetypeGroupId=org.fluttercode.knappsack -DarchetypeArtifactId=jee6-sandbox-archetype -DinteractiveMode=false -DarchetypeVersion=%version% -DgroupId=org.application -DartifactId=jee6sandbox -Dpackage=org.application %repo%

call mvn archetype:generate -DarchetypeGroupId=org.fluttercode.knappsack -DarchetypeArtifactId=jee6-sandbox-demo-archetype -DinteractiveMode=false -DarchetypeVersion=%version% -DgroupId=org.application -DartifactId=jee6sboxdemo -Dpackage=org.application %repo%

call mvn archetype:generate -DarchetypeGroupId=org.fluttercode.knappsack -DarchetypeArtifactId=jee6-servlet-basic-archetype -DinteractiveMode=false -DarchetypeVersion=%version% -DgroupId=org.application -DartifactId=servletbasic -Dpackage=org.application %repo%

call mvn archetype:generate -DarchetypeGroupId=org.fluttercode.knappsack -DarchetypeArtifactId=jee6-servlet-minimal-archetype -DinteractiveMode=false -DarchetypeVersion=%version% -DgroupId=org.application -DartifactId=servletminimal -Dpackage=org.application %repo%

call mvn archetype:generate -DarchetypeGroupId=org.fluttercode.knappsack -DarchetypeArtifactId=jee6-servlet-sandbox-archetype -DinteractiveMode=false -DarchetypeVersion=%version% -DgroupId=org.application -DartifactId=servletsandbox -Dpackage=org.application %repo%


call mvn archetype:generate -DarchetypeGroupId=org.fluttercode.knappsack -DarchetypeArtifactId=jee6-servlet-demo-archetype -DinteractiveMode=false -DarchetypeVersion=%version% -DgroupId=org.application -DartifactId=servletdemol -Dpackage=org.application %repo%
