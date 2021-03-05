use bmicalculator;

delimiter $$
drop function if exists countPeople $$

create function countPeople() returns int deterministic
begin

declare co int default 0;

select count(*) into co from person;
return co;

end $$
delimiter ;