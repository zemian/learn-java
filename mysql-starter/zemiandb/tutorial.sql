-- = MySQL Tutorial
-- https://dev.mysql.com/doc/refman/8.0/en/tutorial.html
-- 
-- == Connecting to db
  shell> mysql -h localhost -u zemiandb -p zemiandb

-- == First Query
  mysql> SELECT VERSION(), CURRENT_DATE;

-- == Mysql Client Commands 
/*
\c           Clearn SQL buffer
\h           Getting help
\. input.sql Execute a input sql file
\!           Execute system command
\u dbname    Switch to another database
\q           Quit and exit mysql client

show databases;
show tables;
*/

-- == Tutorial with DB "menagerie"

-- Create DB
CREATE DATABASE menagerie;
USE menagerie;

-- Create Table
CREATE TABLE pet (name VARCHAR(20), owner VARCHAR(20),
       species VARCHAR(20), sex CHAR(1), birth DATE, death DATE);
DESCRIBE pet;

-- Loading file  using LOCAL client
LOAD DATA LOCAL INFILE 'pet.txt' INTO TABLE pet;

/*
Loading file "Local" vs "Server" side. There is security issue with "Local". The problem
is actuall weak the client host, not that server!

NOTE: IF you see this error, then it means the server is not setup for it.
ERROR 1148 (42000): The used command is not allowed with this MySQL version
*/

-- Loading file using server
load data infile '/Users/zemian/src/zemian/mysql-starter/zemiandb/pet.txt' into table pet;

-- Reload from file
DELETE FROM pet;
load data infile '/Users/zemian/src/zemian/mysql-starter/zemiandb/pet.txt' into table pet;

-- Insert Data with SQL 
INSERT INTO pet
       VALUES ('Puffball','Diane','hamster','f','1999-03-30',NULL);

-- Retrieving data
SELECT * FROM pet;
SELECT * FROM pet WHERE name = 'Bowser';
SELECT * FROM pet WHERE birth >= '1998-1-1'; /* NOTE: You can use string as date value! */
SELECT * FROM pet WHERE species = 'dog' AND sex = 'f';
SELECT * FROM pet WHERE species = 'snake' OR species = 'bird';
SELECT * FROM pet WHERE (species = 'cat' AND sex = 'm')
       OR (species = 'dog' AND sex = 'f');
SELECT name, birth FROM pet;
SELECT owner FROM pet;
SELECT DISTINCT owner FROM pet;
SELECT name, species, birth FROM pet
       WHERE species = 'dog' OR species = 'cat';
SELECT name, birth FROM pet ORDER BY birth;
SELECT name, birth FROM pet ORDER BY birth DESC;
SELECT name, species, birth FROM pet
       ORDER BY species, birth DESC;
SELECT name, birth, CURDATE(),
       TIMESTAMPDIFF(YEAR,birth,CURDATE()) AS age
       FROM pet;
SELECT name, birth, CURDATE(),
       TIMESTAMPDIFF(YEAR,birth,CURDATE()) AS age
       FROM pet ORDER BY nameSELECT name, birth, CURDATE(),
       TIMESTAMPDIFF(YEAR,birth,CURDATE()) AS age
       FROM pet ORDER BY age;
SELECT name, birth, death,
       TIMESTAMPDIFF(YEAR,birth,death) AS age
       FROM pet WHERE death IS NOT NULL ORDER BY age;
SELECT name, birth, MONTH(birth) FROM pet;
SELECT name, birth FROM pet WHERE MONTH(birth) = 5;
SELECT name, birth FROM pet
       WHERE MONTH(birth) = MONTH(DATE_ADD(CURDATE(),INTERVAL 1 MONTH)); /* BD next MONTH */
SELECT name, birth FROM pet
       WHERE MONTH(birth) = MOD(MONTH(CURDATE()), 12) + 1;
SELECT '2018-10-31' + INTERVAL 1 DAY;
SELECT '2018-10-32' + INTERVAL 1 DAY;
SHOW WARNINGS;

SELECT * FROM pet WHERE name LIKE 'b%';
SELECT * FROM pet WHERE name LIKE '%fy';
SELECT * FROM pet WHERE name LIKE '%w%';

SELECT * FROM pet WHERE name LIKE '_____'; /* matching any name with 5 chars */
SELECT * FROM pet WHERE REGEXP_LIKE(name, '^.....$'); /* Same as above */
SELECT * FROM pet WHERE REGEXP_LIKE(name, '^.{5}$');

SELECT * FROM pet WHERE REGEXP_LIKE(name, '^b');
SELECT * FROM pet WHERE REGEXP_LIKE(name, 'fy$');
SELECT * FROM pet WHERE REGEXP_LIKE(name, 'w'); /* matching any name contains 'w' */

SELECT COUNT(*) FROM pet;
SELECT owner, COUNT(*) FROM pet GROUP BY owner;
SELECT species, COUNT(*) FROM pet GROUP BY species;
SELECT sex, COUNT(*) FROM pet GROUP BY sex;
SELECT species, sex, COUNT(*) FROM pet GROUP BY species, sex;
SELECT species, sex, COUNT(*) FROM pet
       WHERE species = 'dog' OR species = 'cat'
       GROUP BY species, sex;
SELECT species, sex, COUNT(*) FROM pet
       WHERE sex IS NOT NULL
       GROUP BY species, sex;



-- Update data
UPDATE pet SET birth = '1989-08-31' WHERE name = 'Bowser';

-- == Working with NULL values
-- Conceptually, NULL means “a missing unknown value” and it is treated somewhat 
-- differently from other values.

-- Test for NULL
SELECT 1 IS NULL, 1 IS NOT NULL;

-- DO not use comparison operators 
SELECT 1 = NULL, 1 <> NULL, 1 < NULL, 1 > NULL;

-- Zero or empty string is not the same as NULL!
SELECT 0 IS NULL, 0 IS NOT NULL, '' IS NULL, '' IS NOT NULL;


-- == More table (related data)
CREATE TABLE event (name VARCHAR(20), date DATE,
       type VARCHAR(15), remark VARCHAR(255));
load data infile '/Users/zemian/src/zemian/mysql-starter/zemiandb/event.txt' into table event;

-- Joins
SELECT pet.name,
       TIMESTAMPDIFF(YEAR,birth,date) AS age,
       remark
       FROM pet INNER JOIN event
         ON pet.name = event.name
       WHERE event.type = 'litter';
SELECT p1.name, p1.sex, p2.name, p2.sex, p1.species
       FROM pet AS p1 INNER JOIN pet AS p2
         ON p1.species = p2.species
         AND p1.sex = 'f' AND p1.death IS NULL
         AND p2.sex = 'm' AND p2.death IS NULL;

-- == DB Info
SELECT version();
SELECT DATABASE();
SHOW TABLES;
DESC event;
SHOW CREATE TABLE event;

-- == More examples

-- Create and load data into table
CREATE TABLE shop (
    article INT(4) UNSIGNED ZEROFILL DEFAULT '0000' NOT NULL,
    dealer  CHAR(20)                 DEFAULT ''     NOT NULL,
    price   DOUBLE(16,2)             DEFAULT '0.00' NOT NULL,
    PRIMARY KEY(article, dealer));
INSERT INTO shop VALUES
    (1,'A',3.45),(1,'B',3.99),(2,'A',10.99),(3,'B',1.45),
    (3,'C',1.69),(3,'D',1.25),(4,'D',19.95);
SELECT * FROM shop ORDER BY article;

-- “What is the highest item number?”
SELECT MAX(article) AS article FROM shop;

-- Find the number, dealer, and price of the most expensive article.
SELECT article, dealer, price
FROM   shop
WHERE  price=(SELECT MAX(price) FROM shop);

SELECT s1.article, s1.dealer, s1.price
FROM shop s1
LEFT JOIN shop s2 ON s1.price < s2.price
WHERE s2.article IS NULL;

SELECT article, dealer, price
FROM shop
ORDER BY price DESC
LIMIT 1;

-- Find the highest price per article.
SELECT article, MAX(price) AS price
FROM   shop
GROUP BY article
ORDER BY article;

SELECT s1.article, s1.dealer, s1.price
FROM shop s1
LEFT JOIN shop s2 ON s1.price < s2.price
WHERE s2.article IS NULL;

SELECT article, dealer, price
FROM shop
ORDER BY price DESC
LIMIT 1;

-- For each article, find the dealer or dealers with the most expensive price.
SELECT article, dealer, price
FROM   shop s1
WHERE  price=(SELECT MAX(s2.price)
              FROM shop s2
              WHERE s1.article = s2.article)
ORDER BY article;

-- Uncorrelated subquery: 
SELECT s1.article, dealer, s1.price
FROM shop s1
JOIN (
  SELECT article, MAX(price) AS price
  FROM shop
  GROUP BY article) AS s2
  ON s1.article = s2.article AND s1.price = s2.price
ORDER BY article;

SELECT s1.article, s1.dealer, s1.price
FROM shop s1
LEFT JOIN shop s2 ON s1.article = s2.article AND s1.price < s2.price
WHERE s2.article IS NULL
ORDER BY s1.article;

-- Windowing function
WITH s1 AS (
   SELECT article, dealer, price,
          RANK() OVER (PARTITION BY article
                           ORDER BY price DESC
                      ) AS `Rank`
     FROM shop
)
SELECT article, dealer, price
  FROM s1
  WHERE `Rank` = 1
ORDER BY article;

-- Using user vars
SELECT @min_price:=MIN(price),@max_price:=MAX(price) FROM shop;
SELECT * FROM shop WHERE price=@min_price OR price=@max_price;
select @min_price;

-- == Foreign Keys
CREATE TABLE person (
    id SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
    name CHAR(60) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE shirt (
    id SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
    style ENUM('t-shirt', 'polo', 'dress') NOT NULL,
    color ENUM('red', 'blue', 'orange', 'white', 'black') NOT NULL,
    owner SMALLINT UNSIGNED NOT NULL REFERENCES person(id),
    PRIMARY KEY (id)
);

INSERT INTO person VALUES (NULL, 'Antonio Paz');

SELECT @last := LAST_INSERT_ID();

INSERT INTO shirt VALUES
(NULL, 'polo', 'blue', @last),
(NULL, 'dress', 'white', @last),
(NULL, 't-shirt', 'blue', @last);

INSERT INTO person VALUES (NULL, 'Lilliana Angelovska');

SELECT @last := LAST_INSERT_ID();

INSERT INTO shirt VALUES
(NULL, 'dress', 'orange', @last),
(NULL, 'polo', 'red', @last),
(NULL, 'dress', 'blue', @last),
(NULL, 't-shirt', 'white', @last);

SELECT * FROM person;

SELECT * FROM shirt;

SELECT s.* FROM person p INNER JOIN shirt s
   ON s.owner = p.id
 WHERE p.name LIKE 'Lilliana%'
   AND s.color <> 'white';

-- NOTE That foreign references do not show up with "SHOW TABLE"!
SHOW CREATE TABLE shirt\G


-- ==  Calculating Visits Per Day
CREATE TABLE t1 (year YEAR(4), month INT(2) UNSIGNED ZEROFILL,
             day INT(2) UNSIGNED ZEROFILL);
INSERT INTO t1 VALUES(2000,1,1),(2000,1,20),(2000,1,30),(2000,2,2),
            (2000,2,23),(2000,2,23);
SELECT year,month,BIT_COUNT(BIT_OR(1<<day)) AS days FROM t1
       GROUP BY year,month;

-- == Auto Increment Column
CREATE TABLE animals (
     id MEDIUMINT NOT NULL AUTO_INCREMENT,
     name CHAR(30) NOT NULL,
     PRIMARY KEY (id)
);

INSERT INTO animals (name) VALUES
    ('dog'),('cat'),('penguin'),
    ('lax'),('whale'),('ostrich');

SELECT * FROM animals;

-- Default to generate next ID
INSERT INTO animals (id,name) VALUES(0,'groundhog');
INSERT INTO animals (id,name) VALUES(NULL,'squirrel');

-- Skip next ID value
INSERT INTO animals (id,name) VALUES(100,'rabbit');
INSERT INTO animals (id,name) VALUES(NULL,'mouse');
SELECT * FROM animals;

-- Set start value
ALTER TABLE animals AUTO_INCREMENT = 200;
INSERT INTO animals (id,name) VALUES(0,'chippie');
select * from animals;
