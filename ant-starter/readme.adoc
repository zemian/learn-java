Apache Ant is a Java-based build tool. In theory, it is kind of like make, without make's wrinkles.

http://ant.apache.org/manual/index.html

== Setup

After installing binary ant (eg: `sdk install ant`), you can
also install optional tasks libraries:

  cd $(dirname `which ant`)
  ant -f fetch.xml -Ddest=system

=== JDK Requirements

- Ant 1.10 works with JDK 1.8 and higher
- Ant 1.9.* works with JDK 1.5 and higher
- Ant 1.8.* works with JDK 1.4 and higher
- Ant 1.7.* works with JDK 1.3 and higher
- Ant 1.6.* works with JDK 1.2 and higher
- Ant 1.2 to Ant 1.5.* work with JDK 1.1 and higher

== How to run

  ant -version
  ant -diagnostics

On a project with `build.xml`

  ant -p
 