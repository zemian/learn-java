#!/usr/bin/env bash

PROJ_HOME=$(cd $(dirname $0)/.. && pwd)
rm -rf $PROJ_HOME/target/jspwebapp
mvn exec:java -Dexec.mainClass=com.zemian.toolbox.app.Toolbox -Dexec.args="CodeGen --templateSetName=jspwebapp $@"
pushd $PROJ_HOME/target/jspwebapp
mvn package
if [[ "$1" == "run" ]]; then
    java -jar target/dependency/webapp-runner.jar target/jspwebapp.war
fi
popd
