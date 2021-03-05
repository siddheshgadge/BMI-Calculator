use bmicalculator;

delimiter $$

drop procedure if exists calculateBmi $$

create procedure calculateBmi(in weight double, in height double, out bmi double, out bmidecision varchar(25))
begin

set bmi = weight / (height * height);

if bmi <= 18.5 then 		set bmidecision = "Thinness";
elseif bmi <= 25 then 		set bmidecision = "Healthy";
elseif bmi <= 30 then 		set bmidecision = "Overweight";
else 						set bmidecision = "Obese";
end if;

end $$

delimiter ;