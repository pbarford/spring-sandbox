drop table if exists users;

create table users (
  id integer identity primary key,
  version integer not null,
  name varchar(200) not null,
  age integer not null
);
