create table my_money(
  id serial,
  name text,
  amount numeric(15, 2)
);

create table my_money_float(
  id serial,
  name text,
  amount float
);

select * from my_money where name = 'test1532876147895';

select * from test_money_numeric where name = 'test31532874767006' order by id desc;
select * from test_money_float where name = 'num_1532835635436' order by id desc;


-- drop table test_money;

create table test_money (
  id serial,
  name text not null,
  seq integer,
  factor1 double precision,
  amount1 double precision,
  factor2 numeric(15, 2),
  amount2 numeric(15, 2),
  create_ts timestamptz not null default current_timestamp,
  notes text
);

-- alter table test_money rename test_name to name;

select now(), clock_timestamp(), localtime, timeofday(), extract(epoch from now()) as epoch;

insert into test_money(name, factor1, amount1, factor2, amount2)
select 'test4', i * (1.0 / i), 100 - (i * (1.0 / i)), 1.0 / i, 100 - (i * (1.0 / i))
from generate_series(1,100) as i;

create or replace function insert_test_money(name text, size int) returns text as $$
declare
  fac1 float := 0.10;
  amt1 float := 100.00;
  fac2 numeric(15, 2) := 0.10;
  amt2 numeric(15, 2) := 100.00;
begin
  -- first row
  insert into test_money(name, factor1, amount1, factor2, amount2)
  select name, fac1, amt1, fac2, amt2;

  -- rest of rows
  insert into test_money(name, factor1, amount1, factor2, amount2)
  select name, fac1, amount1 - fac1, fac2, amount2 - fac2
  from generate_series(1,size) as i;
  return name;
end;
$$ language plpgsql;
select insert_test_money('test' || nextval('test_money_id_seq'), 100);


select * from test_money;
select name, max(create_ts) latest_ts from test_money group by name order by latest_ts desc;
select name, max(create_ts) latest_ts from test_money group by name order by latest_ts desc limit 1;
select * from test_money where name = 'test1532745613991';
select id, amount1, amount2 from test_money where name = 'test2';
select sum(amount1) sum1, sum(amount2) sum2, abs(sum(amount1) - sum(amount2)) diff from test_money where name = 'test2';
select sum(amount1) sum1, sum(amount2) sum2, abs(sum(amount1) - sum(amount2)) diff from test_money
where id in (101, 102);


-- test money table #2
create table money2 (
  id serial,
  name text,
  amount float
);

create or replace function insert_money2(p_name text) returns text as $$
declare
  sum float := 0.00;
  inc float := 0.01;
begin
  for i in 1..101 loop
    insert into money2(name, amount) values(p_name, sum);
    sum := sum + inc;
  end loop;
  return p_name;
end;
$$ language plpgsql;

select insert_money2('test' || nextval('money2_id_seq'));

select * from money2 where name = 'test103';
select sum(amount) from money2 where name = 'test103';


-- test money table #3
create table money3 (
  id serial,
  name text,
  amount numeric(15, 2)
);

create or replace function insert_money3(p_name text) returns text as $$
declare
  sum numeric(15, 2) := 0.00;
  inc numeric(15, 2) := 0.01;
begin
  for i in 1..101 loop
    insert into money3(name, amount) values(p_name, sum);
    sum := sum + inc;
  end loop;
  return p_name;
end;
$$ language plpgsql;

select insert_money3('test' || nextval('money3_id_seq'));

select * from money3 where name = 'test1';
select sum(amount) from money3 where name = 'test1';
