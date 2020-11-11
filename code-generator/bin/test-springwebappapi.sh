#!/usr/bin/env bash

PROJ_HOME=$(cd $(dirname $0)/.. && pwd)
rm -rf $PROJ_HOME/target/springwebappapi
mvn exec:java -Dexec.mainClass=com.zemian.toolbox.app.Toolbox -Dexec.args="CodeGen --optionsFile=bin/test-springwebappapi-options.json $@"
pushd $PROJ_HOME/target/springwebappapi
mvn package
if [[ "$1" == "run" ]]; then
    java -jar target/dependency/webapp-runner.jar target/springwebappapi.war
fi
popd
