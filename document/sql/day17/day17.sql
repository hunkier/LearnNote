-- **************** day17 ************* --
create database day17 default character set utf8;
show create database day17;
drop database day17;
use day17;
show tables;
-- 创建学生表
create table student(
  id int primary key auto_increment,
  name varchar(20),
  gender varchar(2)
);


desc student;

drop table student;

-- 插入记录
insert into student (name,gender) values ('张三','男');

-- 查询
select * from student;

-- 更新
update student set name='王五' where id=1;

-- 删除
delete from student where id=1;

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




