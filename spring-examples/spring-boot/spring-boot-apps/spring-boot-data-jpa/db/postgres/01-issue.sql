create sequence hibernate_sequence;

create table issue (
	id serial primary key not null,
	summary varchar(200) not null,
	description text not null,
	created_on timestamp default current_timestamp not null
	);

