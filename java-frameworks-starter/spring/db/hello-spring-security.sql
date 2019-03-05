-- == App configuration settings table
CREATE TABLE settings (
  setting_id SERIAL NOT NULL PRIMARY KEY,
  category VARCHAR(50) NOT NULL,
  name VARCHAR(50) NOT NULL,
  value VARCHAR(5000) NOT NULL,
  type VARCHAR(50) NOT NULL DEFAULT 'STRING',
  description VARCHAR(1000) NULL
);

-- == Spring Security
-- Demo: `hello-spring-security`
-- User authentication and authrization tables based on SpringSecurity
-- https://docs.spring.io/spring-security/site/docs/current/reference/html/appendix-schema.html#user-schema
CREATE TABLE users (
  username VARCHAR(50) NOT NULL PRIMARY KEY,
  password VARCHAR(500) NOT NULL,
  enabled BOOLEAN NOT NULL
);

CREATE TABLE authorities (
  username VARCHAR(50) NOT NULL,
  authority VARCHAR(50) NOT NULL,
  CONSTRAINT fk_authorities_users FOREIGN KEY(username) REFERENCES users(username)
);
CREATE UNIQUE INDEX ix_auth_username ON authorities(username, authority);

INSERT INTO users(username, password, enabled) VALUES
  ('admin', '$2a$05$Exj9iNyCq.aDUVxi7HPp6egkFJbQF0BtWC1zgCusVB5AI/aMsakQG', TRUE),
  ('test', '$2a$05$R9PsUlsdXRb3qUuIwdLR8u8X4ZVNn6I/o6SBTCYy98MGlKFXokCHW', TRUE),
  ('test2', '$2a$05$MtRC/pjZV/77BXW9WoCpCuNHuLPb5yDqaqQnX.0W3YbKN3kbToGZy', TRUE),
  ('test3', '$2a$05$i2JH5MK63jdVAmmd.oYfXu2dIrvzyIswZMln0sStUh7q.LZu/7tsq', FALSE);

INSERT INTO authorities(username, authority) VALUES
  ('admin', 'ADMIN'),
  ('test', 'USER'),
  ('test2', 'USER'),
  ('test3', 'USER');
