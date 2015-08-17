

-- **************** day16 ************* --
create database day16 default character set utf8;
show databases;
use day16;
show tables;
show create database day16;
drop database day16;

-- ****************** 一、数据约束 ********************* --
-- 1.1 默认值
create table student(
  id int,
  name varchar(20),
  address varchar(50) default '广州天河'  -- 默认值
);

-- 当字段没有插入值的时候，mysql自动给该字段分配默认值
insert into student (id,name) values (1,'张三');
-- 注意： 默认值的字段允许为null
insert into student(id,name,address) values(2,'李四',null),(3,'王五','广州番禺');

-- 1.2 非空
-- 需求： gender字段必须有值（不为null）
create table student(
  id int,
  name varchar(20),
  gender varchar(2) not null -- 非空
);

-- 非空字段必须赋值
insert into student (id,name) value(1,'李四');
-- 非空字段不能插入null
insert into student (id,name,gender) value (1,'李四',null);
insert into student (id,name,gender) value (1,'李四','男');
drop table student;
select * from student;
desc student;

-- 1.3 唯一
create table student(
  id int UNIQUE , -- 唯一
  name varchar(20)
);

insert into student (id,name) values(1,'zs');
insert into student (id,name) values(1,'lisi');-- (1,'lisi');[23000][1062] Duplicate entry '1' for key 'id'

insert into student (id,name) value(2,'lisi');

select * from student;

-- 1.4 主键 （非空+唯一）
drop table student;

create table student(
  id int primary key,
  name varchar(20)
);

insert into student(id,name)values(1,'张三');
insert into student(id,name)values(2,'张三');
# insert into student(id,name)values(1,'李四'); --  [23000][1062] Duplicate entry '1' for key 'PRIMARY'
# insert into student(name)values('李四'); -- [HY000][1364] Field 'id' doesn't have a default value

-- 1.5 自增长
drop table student;
create table student(
  id int(4) zerofill primary key AUTO_INCREMENT,
  name varchar(20)
);

-- 自增字段可以不赋值，自动递增
insert into student(name) values('张三');
insert into student(name) values('李四');
insert into student(name) values('王五');

select * from student;
-- 不能影响自增长约束
delete from student;
-- 可以影响自增长
truncate table student;

-- 1.6 外键约束
-- 员工表
create table employee(
  id int primary key,
  empName varchar(20),
  deptName varchar(20)
);

insert into employee(id,empName,deptName) values(1,'张三','软件开发部');
insert into employee(id,empName,deptName) values(2,'李四','软件开发部');
insert into employee(id,empName,deptName) values(3,'王五','应用维护部');

select * from employee;

-- 添加员工，部门的数据冗余高
insert into employee values(4,'陈六','软件开发部');

-- 解决数据冗余高的问题：给冗余的字段放到一张独立的表中
-- 独立设计一张部门表
create table dept(
  id int primary key,
  deptName varchar(20)
);

drop table employee;

-- 修改员工表
create table employee(
  id int primary key,
  empName varchar(20),
  deptId int, -- 把部门名称改为部门ID
  -- 声明一个外键约束
  constraint employee_dept_fk foreign key(deptId) references dept(id) on update cascade on delete cascade
  --              外键名称                    外键                参考表( 参考字段)     on cascade update :级联修改
);

insert into dept(id,deptName) values(1,'软件开发部');
insert into dept(id,deptName) values(2,'应用维护部');
insert into dept(id,deptName) values(3,'秘书部');


insert into employee value(1,'张三',1);
insert into employee value(2,'李四',1);
insert into employee value(3,'王五',2);
insert into employee value(4,'陈六',3);

-- 问题： 该记录业务上不合法，员工插入了一个不存在的部门数据
insert into employee values(5,'陈六',4); -- 违反外键约束 [23000][1452] Cannot add or update a child row: a foreign key constraint fails (`day16`.`employee`, CONSTRAINT `employee_dept_fk` FOREIGN KEY (`deptId`) REFERENCES `dept` (`id`) ON DELETE CASCADE ON UPDATE CASCADE)

-- 1） 当有了外键约束，添加数据的顺序：先添加主表，再添加副表数据
-- 2） 当有了外键约束，修改数据的顺序：先修改副表，在修改主表数据
-- 3） 当有了外键约束，删除数据的顺序：先删除附表，再删除主表数据
-- 修改部门（不能直接修改主表）
update dept set id=4 where id=3;
-- 先修改员工表
update employee set deptId=2 where id=4;

-- 删除部门
delete from dept where id=2;

-- 先删除员工表
delete from employee where deptId=2;
delete * from dept;

select * from dept;
select * from employee;

-- 级联修改（修改）
-- 直接修改部门
update dept set id=5 where id=4;

-- 级联删除
-- 直接删除部门
delete from dept where id=1;



-- ************* 二、关联查询（多表查询） *************** --
-- 需求：查询员工及其所在部门。（显示员工姓名，部门名称）
-- 2.1 交叉连接查询（不推荐。产生笛卡尔积现象：4*4=16，有些是重复记录）
select empName,deptName from employee,dept;

-- 需求：查询员工及其所在的部门（显示员工姓名，部门名称）
-- 多表查询规则：1）确定查哪些表  2）确定查哪些字段 3）表与表之间的连接条件（规律：连接条件数量是表数量-1）
-- 2.2 内连接：只有满足条件的结果才会显示
select empName,deptName        -- 2） 确定查哪些字段
  from employee,dept              -- 1) 确定查哪些表
  where employee.deptId=dept.id; -- 3）表与表之间连接关系

-- 内连接的另一种写法
select empName,deptName
from employee
  inner join dept
    on employee.deptId=dept.id;

-- 使用别名
select e.empName,d.deptName
from employee e
  inner join dept d
  on e.deptId=d.id;

insert into dept(id,deptName) values(4,'总经办');

-- 需求：查询每个部门的员工
-- 预期结果
 -- 软件开发部  张三
 -- 软件开发部  李四
 -- 应用维护部  王五
 -- 秘书部      陈六
 -- 总经办      null
-- 2.2 左[外]连接查询：使用左边表的数据去匹配右边表的数据，如果符合连接条件的结果显示，如果不符合连接则显示null
  -- （注意：左外连接：左表的数据一定会显示）
select d.deptName,e.empName
from dept d
  left outer join employee e
    on d.id = e.deptId;

-- 2.3 右[外]连接查询：使用右边的表的数据去匹配左边表的数据，如果如果符合连接条件的结果则显示，不符合连接条件的结果则显示null
  -- （注意：右外连接：右边的数据一定会完成显示！）
select d.deptName,e.empName
from employee e
  right outer join dept d
  on d.id=e.deptId;

-- 2.4 自连接查询
-- 需求：查询员工及其上司
-- 预期结果
  --  张三    null
  --  李四    张三
  --  王五    李四
  --  陈六    王五
select e.empName, b.empName
from employee e
  left outer join employee b
    on e.bossId=b.id;

select * from dept;
select * from employee;
-- 添加上司
alter table employee add  bossId int;
update employee set bossId=1 where id=2;
update employee set bossId=2 where id=3;
update employee set bossId=3 where id=4;

-- ********** 三、存储过程 *********** --
-- 声明结束符
-- 创建存储过程
delimiter $
create procedure pro_test()
  begin
    -- 可以写多个sql语句
    select * from employee;
  end $

-- 执行存储过程
call pro_test();

-- 3.1 带有参数的存储过程
-- 需求传入一个员工的id，查询员工信息
delimiter $
create procedure pro_findById(in eid int) -- in: 输入参数
begin
  select * from employee where id=eid;
end $

-- 调用
call pro_findById(1);

-- 3.2 带有输出参数的存储过程
delimiter $
create procedure pro_testOut(out str varchar(20)) -- out: 输出参数
begin
      -- 给参数赋值
  set str='hello sql procedure';
end $

call pro_testOut(@name);
select @name ;

-- 删除存储过程
drop procedure pro_testOut;
-- 调用
-- 如何接受返回参数的值？？
-- *** mysql的变量 ******
-- 全局变量（内置变量）：mysql数据库内置的变量（所有的连接都起作用）
       -- 查看所有全局变量： show ariables
       -- 查看某个全局变量： select @@变量名
       -- 修改全局变量：     set 变量名=新值
       -- character_set_client： mysql服务器的接收数据的编码
       -- character_set_result： mysql服务器的输出数据的编码

-- 会话变量：  只存在于当前客户端与数据库服务器端的一次连接中。如果断开连接，那么会话变量全部丢失！
      --  定义会变变量： set @变量名=值
      --  查看会话变量： select @变量

-- 局部变量： 在存储过程中使用的变量叫局部变量。只要存储过程执行完毕，局部变量就丢失！

-- 1）定义一个会话变量name， 2）使用name会话变量接收存储过程的返回值
call pro_testOut(@name);
-- 查看变量
select @name;

-- 3.3 带有输入输出参数的存储过程
delimiter $
create procedure pro_testInOut(inout n int) -- inout: 输入输出参数
begin
  -- 查看变量
  select n;
  set n=500;
end $

-- 调用
set @n=10;
call pro_testInOut(@n);
select @n;

-- 3.4 带有条件判断的存储过程
-- 需求：  输入一个整数，如果1，则返回“星期一”，如果2，返回“星期二”，如果3，返回“星期三”。其他数字返回“输入错误”。
delimiter $
create procedure pro_testIf(in num int, out str varchar(20))
begin
  if num=1 THEN
    set str='星期一';
  elseif num=2 then
    set str='星期二';
  elseif num=3 then
    set str='星期三';
  else
    set str='输入错误';
  end if;
end $

call pro_testIf(4,@str);
select @str;

-- 3.5 带有循环功能的存储过程
-- 需求：  输入一个整数，求和。例如，输入100，统计1-100的和
delimiter $
create procedure pro_testWhile(in num int,out result int)
begin
  -- 定义一个局部变量
  declare i int default 1;
  declare vsum int default 0;
  while i<=num do
      set vsum=vsum+i;
      set i=i+1;
    end while;
    set result=vsum;
  end $

call pro_testWhile(100,@result);
select @result;

-- 3.6 使用查询的结果赋值给变量（into）
delimiter $
create procedure pro_findById2(in eid int,out vname varchar(20))
  begin
    select empName into vname from employee where id=eid;
  end $

call pro_findById2(1,@name);
select @name;

use day15;
select * from student2;

-- 练习： 编写一个存储过程
  -- 如果学生的英语成绩平均分小于等于70分，则输出’一般‘
  -- 如果学生的应用成绩平均分大于70，且小于等于90分，则输出’良好‘
  -- 如果学生的英语成绩平均分大于90分，则输出优秀

delimiter $
create procedure pro_testAvg(out str varchar(20))
  begin
    -- 定义局部变量，接收平均分
    declare savg double;
    -- 计算平均分
    select avg(english) into savg from student2;
    if savg<=70 then
      set str='一般';
    elseif savg>70 and savg<=90 then
      set str='良好';
    else
      set str='优秀';
    end if;
  end $

call pro_testAvg(@str);
select @str;

use day16;
-- ********************* 四、触发器 ***************** --
select * from employee;

-- 日志表
create table test_log(
  id int primary key auto_increment,
  content varchar(100)
);

-- 需求：  当向员工表插入一条记入时，希望mysql自动同时往日志表插入数据
-- 创建触发器
create trigger tri_empAdd after insert on employee for each row -- 当员工表插入一条数据时
  insert into test_log(content) values('员工表插入了一条数据');

-- 插入数据
insert into employee(id,empName,deptId) values(7,'扎古斯',1);
insert into employee(id,empName,deptId) values(8,'扎古斯2',1);

-- 创建触发器（修改）
create trigger tri_empUpdate after update on employee for each row -- 当员工表更新了一条数据
  insert into test_log(content) values ('员工表修改了一条数据');

-- 修改
update employee set empName='eric' where id=7;

-- 创建触发器（删除）
create trigger tri_empDel after delete on employee for each row
  insert into test_log(content) values('员工表删除了一条记录');

-- 删除
delete from employee where id=7;

select * from employee;
select * from test_log;

-- ************* 五、mysql权限问题 *********** --
-- mysql数据库权限问题：root ：拥有所有权限（可以干任何事）
-- 权限账户，只拥有部分权限（CURD）例如，只能操作某个数据库的某张表
-- 如果修改mysql的用户密码？
-- password：md5加密函数（单向加密）
select password('root'); -- *81F5E21E35407D884A6CD4A731AEBFB6AF209E1B

-- mysql数据库，用户配置表：user表
use mysql;

delete from user where user='hunk';

select * from user;

-- 修改密码
update user set password=password('123456') where user='root';
update user set password=password('123456') where user='hunk';
update user set password='' where user='root';

-- 分配权限账户
grant select on day16.employee to 'eric'@'localhost' identified by '123456';
grant delete on day16.employee to 'eric'@'localhost' identified by '123456';
grant all privileges  on *.*  to 'hunkier'@'localhost' identified by'';
grant all privileges  on *.*  to 'hunk'@'localhost' identified by'123456';

-- ***** 六、mysql备份和还原 ****** --
-- 备份，在命令行运行  mysqldump -u root -p day17 > C:/back.sql
-- 恢复，在命令行运行  mysql -u root -p day17 < C:/back.sql



