#!/usr/bin/env bash
#
# Invoke toolbox.
#
# Zemian Deng 2018-01-09
#
PROJ_HOME=$(cd $(dirname $0)/.. && pwd)
if [[ ! -d $PROJ_HOME/target/dependency ]]; then
    mvn compile dependency:copy-dependencies
fi
$PROJ_HOME/bin/runjava.sh com.zemian.toolbox.app.Toolbox "$@"
