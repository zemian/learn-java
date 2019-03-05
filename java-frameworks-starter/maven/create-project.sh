#!/usr/bin/bash
#
# Use Maven Archetype to generate a project
# 
# Ref:
# https://maven.apache.org/archetype/maven-archetype-plugin/examples/generate-batch.html
#
# Author: Zemian Deng 2018-03-24
#

# Default project name is 'project', or first argument if given
PROJECT_NAME=project
GROUP_NAME=com.zemian
ARCHETYPE=maven-archetype-quickstart
if (( $# >= 1 )); then
	PROJECT_NAME=$1
fi
if (( $# >= 2 )); then
	GROUP_NAME=$2
fi
if (( $# >= 3 )); then
	ARCHETYPE=$3
fi
PACKAGE_NAME=${GROUP_NAME/-/}.${PROJECT_NAME/-/}

echo "Generating Maven Project $GROUP_NAME:$PROJECT_NAME using $ARCHETYPE"

mvn archetype:generate -B \
-DarchetypeGroupId=org.apache.maven.archetypes \
-DarchetypeArtifactId=$ARCHETYPE \
-DarchetypeVersion=1.1 \
-Dversion=1.0-SNAPSHOT \
-DgroupId=$GROUP_NAME \
-DartifactId=$PROJECT_NAME \
-Dpackage=$PACKAGE_NAME
