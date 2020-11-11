-- == Test Tables
CREATE TABLE settings (
  setting_id SERIAL NOT NULL PRIMARY KEY,
  category VARCHAR(50) NOT NULL,
  name VARCHAR(50) NOT NULL,
  value VARCHAR(5000) NOT NULL,
  type VARCHAR(50) NOT NULL DEFAULT 'STRING',
  description VARCHAR(1000) NULL,
  UNIQUE (category, name)
);
insert into settings(category, name, value) values('APP_WEB_DEV', 'app.web.name', 'HelloApp');
insert into settings(category, name, value) values('APP_WEB_DEV', 'app.web.htmlTitle', 'Spring Demo');

CREATE TABLE users (
  username VARCHAR(50) NOT NULL PRIMARY KEY,
  password VARCHAR(500) NOT NULL,
  full_name VARCHAR(500) NOT NULL,
  admin BOOLEAN NOT NULL DEFAULT FALSE ,
  deleted BOOLEAN NOT NULL DEFAULT FALSE,
  created_dt TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE audit_logs (
  log_id SERIAL NOT NULL PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  value VARCHAR(5000) NOT NULL,
  created_dt TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE contacts (
  contact_id SERIAL NOT NULL PRIMARY KEY,
  first_name VARCHAR(500) NOT NULL,
  middle_name VARCHAR(500) NULL,
  last_name VARCHAR(500) NOT NULL,
  email VARCHAR(1000) NULL,
  phone VARCHAR(50) NULL,
  addresss1 VARCHAR(500) NULL,
  addresss2 VARCHAR(500) NULL,
  city VARCHAR(500) NULL,
  state VARCHAR(50) NULL,
  birthdate DATE NULL,
  created_dt TIMESTAMPTZ NOT NULL DEFAULT NOW()
);


CREATE TABLE my_types (
  test_types_id SERIAL NOT NULL PRIMARY KEY,
  test_types_id2 SMALLSERIAL NOT NULL,
  test_types_id3 BIGSERIAL NOT NULL,

  mysmallint SMALLINT NULL,
  myint INT NULL,
  mybigint BIGINT NULL,

  mydecimal DECIMAL NULL,
  mynumberic NUMERIC NULL,
  myreal REAL NULL,
  mydoubleprecision DOUBLE PRECISION NULL,

  mymoney MONEY NULL,

  mychar CHAR(10) NULL,
  mystring VARCHAR(500) NULL,
  mytext TEXT NULL,

  mybytea BYTEA NULL,

  mydate DATE NULL,
  mytime TIME NULL,
  mydatetime TIMESTAMP NULL,
  mydatetimetz TIMESTAMPTZ NULL,
  mytimetz TIMETZ NULL,
  myinterval INTERVAL NULL,

  myboolean BOOLEAN NULL
);

CREATE TYPE mood AS ENUM ('sad', 'ok', 'happy');
CREATE TABLE my_custom_types (
  test_custom_types_id SERIAL NOT NULL PRIMARY KEY,
  mymood mood null
);


CREATE TABLE test_timestamps (
  test_datatime_id SERIAL NOT NULL PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  created_dt TIMESTAMPTZ NOT NULL  DEFAULT NOW(),
  created_dt_tz TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE test_foos (
  name VARCHAR(50) NOT NULL,
  value VARCHAR(1000) NOT NULL
);