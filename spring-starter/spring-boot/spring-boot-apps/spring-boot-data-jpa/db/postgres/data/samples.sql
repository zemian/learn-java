insert into issue(id, summary, description) values(
1,
'Create a spring-boot-data-jpa sample project',
'Provide a getting started example on how to use Spring Data.' ||
  '- Maven based project.' ||
  '- Using spring-boot to bring in dependencies.' ||
  '- Provide sample service on how to manage Issue entity.'
);

insert into appuser(username, password, first_name, last_name, email, created_on) values
('test', 'test', 'First', 'Tester', 'first.tester@test.com', '2016-10-30 08:00:00'),
('test2', 'test', 'Second', 'Tester', 'second.tester@test.com', '2000-01-01 08:00:00'),
('test3', 'test', 'Third', 'Tester', 'third.tester@test.com', '2015-12-31 20:00:00');

