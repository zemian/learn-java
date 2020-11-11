/*
== How to run this file

You usually want to use 'root' user to setup a new database. Run it like this:

  mysql -uroot -v < zemiandb/init.sql
  mysql -uroot -v zemiandb < zemiandb/test.sql

== Default MySQL character set and collation
	MySQL 8.0 (utf8mb4, utf8mb4_0900_ai_ci)
	MySQL 5.7 (latin1, latin1_swedish_ci)

	SHOW CHARACTER SET;
	SHOW COLLATION WHERE Charset = 'utf8mb4';

See https://dev.mysql.com/doc/refman/8.0/en/charset-applications.html

NOTE: Use 'utf8mb4' (4 bytes) instead of 'utf8' (3 bytes) for MySQL character set.

== Create DB using 'mysqladmin'

  mysqladmin -uroot create zemiandb

See https://dev.mysql.com/doc/refman/8.0/en/mysqladmin.html

NOTE: You can not use 'mysqladmin' to create new user. For that you must
login and use SQL instead.

== Create new db user

See https://dev.mysql.com/doc/refman/8.0/en/adding-users.html

After user creation you need to setup user permission with GRANT

To view user grant result:

  show grants for zemiandb;

What is a ROLE? (For MySQL 8 + only)
  A MySQL role is a named collection of privileges. 

See 
- https://dev.mysql.com/doc/refman/8.0/en/grant.html
- https://dev.mysql.com/doc/refman/8.0/en/show-grants.html
- https://dev.mysql.com/doc/refman/8.0/en/roles.html
*/

CREATE DATABASE IF NOT EXISTS zemiandb
  DEFAULT CHARACTER SET 'utf8mb4'
  DEFAULT COLLATE 'utf8mb4_0900_ai_ci';

CREATE USER IF NOT EXISTS zemiandb IDENTIFIED BY 'zemiandb123';

-- Give 'zemiandb123' access to all other databases for easy development purpose!
GRANT ALL ON *.* TO zemiandb;

USE zemiandb;
