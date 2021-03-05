use bmicalculator;

delimiter $$

drop trigger if exists t1 $$

create trigger t1 before insert on person for each row
begin

if length(new.name) < 2 or new.name is null then
	signal SQLSTATE '12345' set message_text = "Invalid name";
elseif new.age <= 0 or new.age is null then
	signal SQLSTATE '23456' set message_text = "Invalid age";
elseif length(new.phone) != 10 then
	signal SQLSTATE '45678' set message_text = "Invalid phone";
elseif new.gender is null then
	signal SQLSTATE '56789' set message_text = "Invalid gender";
elseif new.height <= 0 or new.height is null then	
	signal SQLSTATE '67890' set message_text = "Invalid height";
elseif new.weight <= 0 or new.weight is null then	
	signal SQLSTATE '78901' set message_text = "Invalid weight";
elseif new.caldate is null then	
	signal SQLSTATE '89012' set message_text = "Invalid date";
elseif new.bmi <= 0 or new.bmi is null then	
	signal SQLSTATE '90123' set message_text = "Invalid bmi";
elseif new.bmidecision is null then	
	signal SQLSTATE '13245' set message_text = "Invalid remark";
end if;

end $$

delimiter ;