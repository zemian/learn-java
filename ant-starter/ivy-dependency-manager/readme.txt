# A Java Project 

A simple java project using Ant build tool.

This project will explore the Ivy library, which provide 
dependency management for Ant.

See https://ant.apache.org/ivy/history/2.5.2/tutorial.html

## How to Setup Ivy

Download the version you want here, unpack the downloaded zip file 
wherever you want, and copy the Ivy jar file into your Ant lib directory (ANT_HOME/lib).

Then create ivy.xml file to define your dependencies.

TODO: The Ivy store dependency files under "lib" folder. You have to explicitly configure
different configuration to separate them into compile and test and destination.
For this project, we just stored them all under lib for now.

## How to run

  ant download-lib
  ant resolve
  ant test
