-- == App configuration settings table
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
  middle_name VARCHAR(500) NOT NULL,
  last_name VARCHAR(500) NOT NULL,
  email VARCHAR(1000) NOT NULL,
  phone VARCHAR(50) NOT NULL,
  addresss1 VARCHAR(500) NOT NULL,
  addresss2 VARCHAR(500) NOT NULL,
  city VARCHAR(500) NOT NULL,
  state VARCHAR(50) NOT NULL,
  birthdate DATE NOT NULL,
  created_dt TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- == Test Tables

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


-- == STORE PROCEDURE

-- Sample of simple store procedure for testing
CREATE OR REPLACE FUNCTION test_sp_simple() RETURNS INT AS $$
  SELECT 1 + 1
  $$ LANGUAGE SQL;

CREATE OR REPLACE FUNCTION test_sp_inc(i INT) RETURNS INT AS $$
  SELECT i + 1
  $$ LANGUAGE SQL;

CREATE OR REPLACE FUNCTION test_sp_ins_settings(aVal VARCHAR(50), bVal VARCHAR(50), cVal VARCHAR(5000))
  RETURNS INT AS $$
    INSERT INTO settings(category, name, value) VALUES (aVal, bVal, cVal) RETURNING setting_id;
  $$ LANGUAGE SQL;

CREATE OR REPLACE FUNCTION test_sp_count_by_out_param(aVal VARCHAR(50), bResult OUT BIGINT) RETURNS BIGINT AS $$
    SELECT COUNT(*) bResult FROM settings WHERE category = aVal
  $$ LANGUAGE SQL;

CREATE OR REPLACE FUNCTION test_sp_count_by_out_param2(aVal VARCHAR(50), bResult OUT BIGINT, cMaxId OUT INT)
  RETURNS RECORD AS $$
  SELECT COUNT(*) bResult, MAX(setting_id) cMaxId FROM settings WHERE category = aVal
  $$ LANGUAGE SQL;

CREATE OR REPLACE FUNCTION test_sp_sel_settings() RETURNS REFCURSOR AS $$
  DECLARE
    ref REFCURSOR;
  BEGIN
    OPEN ref FOR SELECT * FROM settings;
    RETURN ref;
  END;
  $$ LANGUAGE PLPGSQL;

CREATE OR REPLACE FUNCTION test_sp_sel_settings2()
  RETURNS SETOF settings AS $$
    SELECT * FROM settings;
  $$ LANGUAGE SQL;


CREATE OR REPLACE FUNCTION test_sp_ins_settings2(aVal VARCHAR(50), bVal VARCHAR(50), cVal VARCHAR(5000))
  RETURNS VOID AS $$
  BEGIN
    INSERT INTO audit_logs(name, value) VALUES ('test_sp_ins_settings2', 'SP INSERT BEGIN');

    INSERT INTO settings(category, name, value) VALUES (aVal, bVal, cVal);
    INSERT INTO audit_logs(name, value) VALUES ('test_sp_ins_settings2', 'First row inserted into settings');

    INSERT INTO settings(category, name, value) VALUES ('DUP_' || aVal, bVal, cVal);
    INSERT INTO audit_logs(name, value) VALUES ('test_sp_ins_settings2', 'Second row inserted into settings');

    INSERT INTO audit_logs(name, value) VALUES ('test_sp_ins_settings2', 'SP INSERT END');
  END;
  $$ LANGUAGE PLPGSQL;