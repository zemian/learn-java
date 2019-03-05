#!/usr/bin/env bash

PROJ_HOME=$(cd $(dirname $0)/.. && pwd)
rm -rf $PROJ_HOME/target/springapp
mvn exec:java -Dexec.mainClass=com.zemian.toolbox.app.Toolbox -Dexec.args="CodeGen --optionsFile=bin/test-springapp-options.json $@"
pushd $PROJ_HOME/target/springapp
mvn package
mvn exec:java -Dexec.mainClass=springapp.app.Hello
popd

