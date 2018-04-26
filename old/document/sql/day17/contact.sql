-- **************** day17 ************* --
create database contactsys_web default character set utf8;
show create database contactsys_web;
-- drop database contactsys_web;
use contactsys_web;
show tables;
-- 创建联系人表
create table contact(
  id varchar(50) primary key,
  name varchar(20),
  gender varchar(2),
  age int,
  phone varchar(20),
  email varchar(20),
  qq varchar(20)
);


desc contact;

drop table contact;

-- 插入记录
insert into contact (name,gender) values ('张三','男');

-- 查询
select * from contact;

-- 更新
update contact set name='王五' where id=1;

-- 删除
delete from contact where id=1;

-- 模拟用户登录
-- 用户表
create table users(
  id int primary key auto_increment,
  name varchar(20),
  password varchar(20)
);

-- alter table user rename users;
show tables;

insert into users(name,password) values('eric','123456');
insert into users(name,password) values('hunk','654321');

select *from users;

-- 登录陈功：用户输入的用户名和密码和users表的某条记录匹配
--  登录失败：找不到匹配的数据
select * from users where name='eric' and password='123456';

select * from users;

select * from users where 1=1; -- 1=1 表示永远成立（永远成立，永远真）

-- sql注入
select * from users where name='hunk' or 1=1 and password='35dwq';
-- select * from users where name='hunk';
select * from users where 1=1;


-- 创建存储过程
delimiter $
create procedure pro_findById(in sid int)
  begin
    select * from contact where id=sid;
    end $

delimiter $
create procedure pro_findById2(in sid int,out vname varchar(20))
  begin
    select name into vname from contact where id=sid;
    end $




