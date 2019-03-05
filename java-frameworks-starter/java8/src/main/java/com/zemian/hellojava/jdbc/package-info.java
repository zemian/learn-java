package com.zemian.hellojava.jdbc;

/*

== Testing `settings` Table

  mvn compile exec:java -Dexec.mainClass=com.zemian.hellojava.SettingSample -Daction=query
  mvn compile exec:java -Dexec.mainClass=com.zemian.hellojava.SettingSample -Daction=insert
  mvn compile exec:java -Dexec.mainClass=com.zemian.hellojava.SettingSample -Daction=delete

== Setup Database

First database setup

----
# Create a user that can create db
createuser -d test

# Create the database
createdb -U test test

# Grant user with database
psql -c 'grant all privileges on database test to test'

# Create database schema
psql -U test -d test -f db/01_create.sql
----

If you need to drop db:

  dropdb -U test test

 */
