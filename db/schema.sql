create table rule
(
    id   serial primary key,
    name varchar(200)
);
create table type
(
    id   serial primary key,
    name varchar(200)
);
create table accident
(
    id      serial primary key,
    name    varchar(200),
    text    varchar(200),
    address varchar(200),
    type_id int references type (id)
);
create table accident_rule
(
    id          serial primary key,
    accident_id int references accident (id) on delete cascade,
    rule_id     int references rule (id)
);

insert into rule values (1, 'Статья 1');
insert into rule values (2, 'Статья 2');
insert into rule values (3, 'Статья 3');
insert into type values (1, 'Две машины');
insert into type values (2, 'Машина и человек');
insert into type values (3, 'Машина и велосипед');
