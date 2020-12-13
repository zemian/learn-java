// Nashorn support "scripting" mode - it comes with few builtin objects
// See https://docs.oracle.com/en/java/javase/11/nashorn/nashorn-and-shell-scripting.html

// Usage: jjs -scripting --language=es6 hello-scripting.js -- one two three

print("$ARG=" + $ARG);

print("$ENV.USER=" + $ENV.USER);
print("$ENV.PWD=" + $ENV.PWD);

print('$EXEC("ls -l")=' + $EXEC("ls -l"));
