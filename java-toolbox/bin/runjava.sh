#!/usr/bin/env bash
#
# Bootstrap a java process using current maven project output directories for classpath.
#
# Zemian Deng 2018-01-04
#
PROJ_HOME=$(cd $(dirname $0)/.. && pwd)

PROJ_HOME_MIXED_PATH=$PROJ_HOME
CP_SEP=':'
case "`uname`" in
	CYGWIN*)
	    PROJ_HOME_MIXED_PATH=`cygpath -wma $PROJ_HOME`
        CP_SEP=';'
	;;
esac

if [[ -z $JAVA_CP ]]; then
    JAVA_CP="$PROJ_HOME_MIXED_PATH/target/classes"
    JAVA_CP="$JAVA_CP${CP_SEP}$PROJ_HOME_MIXED_PATH/target/dependency/*"
fi

JAVA_OPTS=${JAVA_OPTS:=}

RUN_CMD="java $JAVA_OPTS -cp '$JAVA_CP' $@"
if [[ -n $DRY_RUN ]]; then
    echo $RUN_CMD
else
    eval $RUN_CMD
fi
