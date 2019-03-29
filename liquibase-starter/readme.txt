== About
See https://www.liquibase.org/documentation/index.html

NOTE: It spelled "liqui" without the "d".

== To run liquibase:

	mvn compile

	# Or to just run update
	mvn liquibase:update

== Stuck on "Waiting for changelog lock...."

	[INFO] SELECT LOCKED FROM databasechangeloglock WHERE ID=1
	[INFO] Waiting for changelog lock....


	Something bad happens during update. Try to remove the lock:
	UPDATE DATABASECHANGELOGLOCK SET LOCKED=FALSE, LOCKGRANTED=null, LOCKEDBY=null where ID=1;

	Or simply delete it:
	DELETE FROM DATABASECHANGELOGLOCK;

== To remove all tables

	mvn liquibase:dropAll
	
== Command Line Usage

java -jar liquibase.jar \
		--changeLogFile=my-changelog.xml \
        --driver=com.mysql.jdbc.Driver \
        --url=jdbc:mysql://localhost:3306/zemiandb \
        --username=zemiandb \
        --password=zemiandb123 \
        update
