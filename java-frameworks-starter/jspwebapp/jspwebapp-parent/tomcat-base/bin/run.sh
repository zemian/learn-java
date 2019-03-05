#!/usr/bin/env bash
SCRIPT_HOME=$(cd $(dirname $0)/.. && pwd)
export CATALINA_HOME=$HOME/apps/apache-tomcat-8.5.14
export CATALINA_BASE=$(cygpath -ma $SCRIPT_HOME)
$CATALINA_HOME/bin/catalina.sh run
