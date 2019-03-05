#!/usr/bin/env bash

PROJ_HOME=$(cd $(dirname $0)/.. && pwd)
rm -rf $PROJ_HOME/target/javaapp
mvn exec:java -Dexec.mainClass=com.zemian.toolbox.app.Toolbox -Dexec.args="CodeGen --optionsFile=bin/test-javaapp-options.json $@"
pushd $PROJ_HOME/target/javaapp
mvn package
mvn exec:java -Dexec.mainClass=javaapp.app.Hello
popd

