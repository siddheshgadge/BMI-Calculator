drop database if exists bmicalculator;

create database if not exists bmicalculator;

use bmicalculator;

create table if not exists person (

personid int unsigned primary key auto_increment,
name varchar(20) not null,
age int not null,
phone bigint,
gender enum('male','female'),
height decimal(10,2) unsigned not null,
weight decimal(10,2) unsigned not null,
caldate date,
bmi decimal(10,2) unsigned not null, 
bmidecision enum('Thinness','Healthy','Overweight','Obese')

);

desc person;

select * from person;