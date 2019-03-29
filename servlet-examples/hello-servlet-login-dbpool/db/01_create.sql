CREATE TABLE settings (
  id SERIAL PRIMARY KEY,
  category VARCHAR(50) NULL,
  name VARCHAR(50) NOT NULL,
  value VARCHAR(5000) NOT NULL
);

create table users (
  user_name         varchar(100) not null primary key,
  user_pass         varchar(1000) not null
);

create table user_roles (
  user_name         varchar(100) not null,
  role_name         varchar(100) not null,
  primary key (user_name, role_name)
);

INSERT INTO users(user_name, user_pass) VALUES('admin', 'admin123');
INSERT INTO users(user_name, user_pass) VALUES('test', 'test123');
INSERT INTO user_roles(user_name, role_name) VALUES('admin', 'webuser');
INSERT INTO user_roles(user_name, role_name) VALUES('test', 'webuser');
