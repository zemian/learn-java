## About The Project

A project to explore JDK 11 libraries

## How To Build Project

Required Software:

* JDK11

Build project command:

```
export JAVA_HOME=/path/to/jdk11
./mvnw install
```  

## How To Setup Maven Wrapper

We only need to run and setup this once:

  mvn -N io.takari:maven:wrapper -Dmaven=3.6.0


## Running Tests

Run all basic unit tests
  mvn test

Run all unit tests + db tests
  mvn test -P alltests

## DB Tests Setup

You need to start a PostgreSQL server with a "test" database running.

The default db user login is "test" and password is "test"

## Maven + JDK 11 Support

Continue to use compiler plugin with and set to 11

        <maven.compiler.source>11</maven.compiler.source>
        <maven.compiler.target>11</maven.compiler.target>

There is a new option for javac since JDK 9, and it works for Maven
on command line, but will not work with IntelliJ (2018.2.4) yet.

        <maven.compiler.release>11</maven.compiler.release>

See https://maven.apache.org/plugins/maven-compiler-plugin/compile-mojo.html#release

## IntelliJ and Maven + JDK 11 Support

With IntelliJ (2018.2.4), imported Maven project, but still need to fix two things:

1. In Project Structure, Module > Dependencies, it's default to jdk 1.8, so it needs
   to change to jdk 11.

2. Build > Rebuild Project failed with error. To fix, edit .idea/misc.xml, update 1.8 to 11.

  <component name="ProjectRootManager" version="2" languageLevel="JDK_11" project-jdk-name="11" project-jdk-type="JavaSDK">
    <output url="file://$PROJECT_DIR$/classes" />
  </component>

