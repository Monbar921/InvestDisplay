truncate table provider restart identity cascade;
truncate table vehicle_type restart identity cascade;

insert into provider(id,name_ru) values (1,'Новая компания');

insert into vehicle_type(id,name_ru,name_en,is_active) values (1,'Автобус','Bus',true);