## About Nashorn

JDK 6 - JDK 8 introduced `java.scripting` API package to load ScriptEngine.
A new command `jrunscript` has been added to run script.
The JDK package a built-in JavaScript engine using the Rhino project.
Rhino 1.6R2 - bundled with Java 6 - supports JavaScript 1.5 (See https://stackoverflow.com/questions/6701489/which-javascript-ecmascript-version-does-javas-rhino-implementation-implement)

* https://docs.oracle.com/javase/7/docs/technotes/tools/index.html#scripting
* https://docs.oracle.com/javase/7/docs/technotes/tools/share/jrunscript.html
* https://docs.oracle.com/javase/7/docs/technotes/tools/share/jsdocs/allclasses-noframe.html
* https://developer.mozilla.org/en-US/docs/Mozilla/Projects/Rhino
* http://mozilla.github.io/rhino/javadoc/index.html
* https://developer.mozilla.org/en-US/docs/Mozilla/Projects/Rhino/Shell

JDK 8 to 14 changed built-in JavaScript engine to Nashorn project. 
A new command tool `jjs` has been added for Nashorn specific.
Nashorn provides a 100% support of ECMAScript 5.1
Note that JDK11 is the LTS release. You should able to replace "11" up to "14" in these docs URLs.
* https://docs.oracle.com/en/java/javase/11/scripting/
* https://docs.oracle.com/en/java/javase/11/tools/jrunscript.html
* https://docs.oracle.com/en/java/javase/11/nashorn/
* https://docs.oracle.com/en/java/javase/11/nashorn/nashorn-and-shell-scripting.html
* https://docs.oracle.com/en/java/javase/11/docs/specs/man/jjs.html

Starting JDK 15, Nashorn has been removed from JDK as separate project now.
Note that the `jrunscript` command is still avaiable in JDK 15, but it's experimental.
* https://docs.oracle.com/en/java/javase/15/docs/specs/man/jrunscript.html
* https://docs.oracle.com/en/java/javase/15/docs/api/java.scripting/module-summary.html
* https://github.com/openjdk/nashorn
* https://search.maven.org/artifact/org.openjdk.nashorn/nashorn-core/15.0/jar
* https://mail.openjdk.java.net/pipermail/nashorn-dev/2020-October/007557.html

## Other ScriptingEngines

* https://groovy-lang.org/
* https://www.jruby.org/
* https://www.jython.org/

## Java and JavaScript Shell

With JDK6, there is a JavaScript shell provided by Rhino with `org.mozilla.javascript.tools.shell.Main` class. JDK also started shipping an experimental `jrunscript` command that support the Java scripting API.

With JDK8, the binary program `jjs` is used to launch the internal Nashorn shell, which simply instantiates the `jdk.nashorn.tools.Shell` class in Java 8 or `jdk.scripting.nashorn.shell` in Java 9. The Nashorn has ES6 support, but you must enable it with `--language=es6` option.

With JDK 10, it introduces `jshell` command that you can evaluate Java (Not JS) code in
a shell terminal. See https://docs.oracle.com/javase/10/jshell for more.

## More about Nashorn

* https://winterbe.com/posts/2014/04/05/java8-nashorn-tutorial/
* https://docs.oracle.com/javase/8/docs/jdk/api/nashorn/jdk/nashorn/api/scripting/ScriptUtils.html
* https://developer.oracle.com/databases/nashorn-javascript-part1.html
* https://developer.oracle.com/databases/nashorn-javascript-part2.html (How to enable ES6 features)
* https://developer.oracle.com/databases/nashorn-javascript-part3.html
* https://wiki.openjdk.java.net/display/Nashorn/Nashorn+extensions
* https://github.com/EclairJS/eclairjs-nashorn/wiki/Nashorn-Java-to-JavaScript-interoperability-issues
