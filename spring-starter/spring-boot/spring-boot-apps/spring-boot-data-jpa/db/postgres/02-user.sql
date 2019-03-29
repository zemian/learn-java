create table appuser (
	username varchar(50) primary key not null,
	password varchar(50) not null,
	first_name varchar(50) not null,
	last_name varchar(50) not null,
	email varchar(50) not null,
	created_on timestamp default current_timestamp not null
	);

