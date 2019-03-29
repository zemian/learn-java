
# Maven shortcuts
alias m='mvn'
alias mw='./mvnw'
alias mdep='m dependency:tree'
alias mc='m compile'
alias mp='m package -DskipTests'
alias mjetty='m jetty:run'
mrun() {
	MC=$1
	shift
	mvn -q exec:java -Dexec.mainClass=$MC -Dexec.args="$@"
}
