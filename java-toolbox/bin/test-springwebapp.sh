#!/usr/bin/env bash

PROJ_HOME=$(cd $(dirname $0)/.. && pwd)
rm -rf $PROJ_HOME/target/springwebapp
mvn exec:java -Dexec.mainClass=com.zemian.toolbox.app.Toolbox -Dexec.args="CodeGen --optionsFile=bin/test-springwebapp-options.json $@"
pushd $PROJ_HOME/target/springwebapp
mvn package
if [[ "$1" == "run" ]]; then
    java -jar target/dependency/webapp-runner.jar target/springwebapp.war
fi
popd
