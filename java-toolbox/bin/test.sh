#!/usr/bin/env bash

PROJ_HOME=$(cd $(dirname $0)/.. && pwd)

for SCRIPT in $(ls $PROJ_HOME/bin/test-*.sh); do
    echo "Testing $SCRIPT"
    eval $SCRIPT | grep ERROR
done
echo "SUCCESS."
