create table settings(
	category varchar(50) not null default 'DEFAULT',
	name varchar(100) not null,
	value varchar(1000) null
);
create unique index ix_settings on settings(category, name);
