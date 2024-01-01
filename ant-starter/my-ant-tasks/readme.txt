# A Java Project 

A simple java project using Ant build tool.

We will explore how to create Ant custom tasks.


## How to run

Type "ant" command to run default "all" target.

## How to setup extra Ant libraries

After installing binary ant (eg: `sdk install ant`), you can
also install optional tasks libraries:

  cd $(dirname `which ant`)
  ant -f fetch.xml -Ddest=system

## And and JDK versions requirement

- Ant 1.10 works with JDK 1.8 and higher
- Ant 1.9.* works with JDK 1.5 and higher
- Ant 1.8.* works with JDK 1.4 and higher
- Ant 1.7.* works with JDK 1.3 and higher
- Ant 1.6.* works with JDK 1.2 and higher
- Ant 1.2 to Ant 1.5.* work with JDK 1.1 and higher

## How to debug Ant

  ant -version
  ant -diagnostics

On a project with `build.xml`, you may see a list of targets by:

  ant -p
