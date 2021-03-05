use bmicalculator;

delimiter $$

drop procedure if exists heightConverter $$

create procedure heightConverter(in feet double, in inches double, out meters double)
begin

declare total_inches double default 0.0;

set total_inches = inches + feet * 12;

set meters = total_inches * 2.54 / 100;

end $$
delimiter ;