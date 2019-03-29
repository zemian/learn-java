#!/usr/bin/env bash

SCRIPT_DIR=$(cd `dirname $0` && pwd)
export CATALINA_HOME=$HOME/apps/apache-tomcat
export CATALINA_BASE=$SCRIPT_DIR/..
$CATALINA_HOME/bin/catalina.sh run
