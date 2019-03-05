#!/usr/bin/env bash
#
# Invoke toolbox.
#
# Zemian Deng 2018-01-09
#
PROJ_HOME=$(cd $(dirname $0)/.. && pwd)
$PROJ_HOME/bin/toolbox.sh CodeGen "$@"
