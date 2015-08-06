--  show VARIABLES LIKE 'CHARACTER%';
SHOW VARIABLES LIKE 'character%';
set character_set_database=utf8;
set character_set_results=utf8;
set character_set_server=utf8;
create database day15;
show databases ;
use day15;
-- 创建表
create table teacher(
  id int,
  name varchar(20)
);

-- 查看所有的表
show tables;

-- 创建一个和teacher一样的学生表
create table student like teacher;
desc student;

-- 删除学生表
drop table student;

create table student(
  id int,
  name varchar(20),
  gender varchar(2),
  age int
);

-- ***********一、增删改数据*********** ---
-- 1.1增加数据
-- 插入所以字段。一定一次按顺序插入
insert into student values(1, '张三', '男',20 );
-- 注意不能少或多字段值
-- insert into student values(2, '李四', '女');
-- 插入部分字段
insert into student(id, name) values(2, "李四");

-- 1.2 修改数据
-- 修改所有的数据(建议少用)
update student set gender='女';
-- 带条件的修改（推荐使用）
update student set gender='男' where id=1; -- 修改id为1的学生，修改性别为男
-- 修改多个字段，注意：set 字段名=值,字段名=值,...
update student set gender='男', age=30 where id=2;

-- 1.3 删除数据
-- 删除所有数据(建议少用)
delete from student;

-- 带条件的删除(推荐使用)
delete from student where id=2;
-- 另一种方式
-- delete from: 可以全表删除    1）可以带条件删除   2）只能删除表的数据，不能删除表的约束   3）使用delete from删除的数据可以回滚（事务）
-- truncate table: 可以全部删除 1）不能带条件删除   2）既可以删除表的数据，也可以删除表约束  3）使用truncate table删除的数据不能回滚
truncate table student;

-- 查看表数据
select * from student;


create table test(
  id int primary key auto_increment, -- 自增长约束
  name varchar(20)
);
desc test;

-- 1.
delete from test;
-- 2.
truncate table test;

insert into test(name) values('张三');
insert into test(name) values('张三2');
insert into test(name) values('张三3');

select * from test;

-- truncate table student where id=2;  不能带条件

-- 查询数据
select * from student;

-- ********二、查询数据(select) ********--
-- 2.1 查询所有列
select * from student;

-- 2.2 查询指定列
select id,name,gender from student;

-- 2.3 查询时指定别名
-- 注意： 在多表查询是经常使用别名
select id as '编号', name as '姓名' from student;

-- 2.4 查询时添加常量列
-- 需求：在查询student表时添加一个班级列，内容为“java就业班”
select id,name,gender,age,'java就业班' as '年级'from student;

alter table student add column (servlet int);
alter table student add column jsp int;
alter table student add column address varchar(60);
update student set address='北京';
update student set servlet=20, jsp=30;

-- 2.5 查询合并列
select id,name,(servlet+jsp) as "总成绩" from student;
-- 注意：合并列只能是数值类型的字段
select id,(name+servlet) from student;

-- 2.6 查询时去除重复记录(DISTINCT)
-- 需求：查询学生的性别  男 女
select distinct gender from student;
-- 另一种语法
select distinct(gender) from student;
-- 需求：查询学生所在的地区
select distinct address from student;

-- 2.7 条件查询（where）
-- 2.7.1 逻辑条件： and（与）   or（或）
-- 需求：查询id为2，且姓名为李四的学生
select * from student where id=2 and name='李四'; -- 交集

-- 需求：查询id为2，或姓名为张三的学生
select * from student where id=2 or name='张三';-- 并集

-- 2.7.2 比较条件：  >   <   >=  <=  =   <>(不等于)     between and (等价于>= 且 <=)
-- 需求：查询servlet成绩大于70的学生
select * from student where jsp>=75 and jsp<=90;

select * from student where gender<>'男';

-- 2.7.3 判断天骄（null 空字符串）： is null / is not null / ='' / <>''
-- 需求：  查询地址为空的学生（包括null和空字符串）
-- null vs 空字符串
-- null： 表示没有
-- 空字符串：有值的
-- 判断null
select * from student where address is null;
-- 判断空字符串
select * from student where address='';

select * from student where address is null or address=''; -- (包括null和空字符串)

-- 2.7.4 模糊条件： like
-- 通常使用以下替换标记
-- % : 表示任意个字符
-- _ : 表示一个字符
-- 需求： 查询行'张'的学生
select * from student where name like '张%';

-- 需求：  查询姓'李'，且姓名只有两个字的学生
select * from student where name like '李_';

select * from student ;

-- 练习 --
create table student2(
  id int,
  name varchar(20),
  chinese float,
  english float,
  math float
);

insert into student2(id,name,chinese,english,math) values(1,'张小明',89,78,90);
insert into student2(id,name,chinese,english,math) values(2,'李进',67,53,95);
insert into student2(id,name,chinese,english,math) values(3,'王五',87,78,77);
insert into student2(id,name,chinese,english,math) values(4,'李一',88,98,92);
insert into student2(id,name,chinese,english,math) values(5,'李来财',82,84,67);
insert into student2(id,name,chinese,english,math) values(6,'张进宝',55,85,45),(7,'黄蓉',75,65,30);
insert into student2(id,name,chinese,english,math) values(7,'黄蓉',75,65,30);


-- 查询表中所有学生的信息
select * from student2;

-- 查询表中所有学生的姓名和对应的英语成绩
select name,english from student2;

-- 过滤表中英语成绩重复的数据
select distinct(english) from student2;

-- 使用别名表示学生分数
select name as '姓名', chinese as '语文', english as '英语', math as '数学' from student2;

-- 查询姓名为“李一”的学生成绩
select * from student2 where name='李一';

-- 查询英语成绩大于等于90分的同学
select * from student2 where english>=90;

-- 查询总分大于200分的所有同学
select * from student2 where (chinese+english+math)>200;
select *,(chinese+english+math) as '总分' from student2 where (chinese+english+math)>200;

-- 查询所有姓李的学生的英语成绩
select name,english from student2 where name like '李%';

-- 查询英语大于80或者总分大于200的同学
select * from student2 where english>80 or (chinese+english+math)>200;

-- 统计每个学生的总分
select id,name,(chinese+english+math) as '总分' from student2;

-- 在所有学生分数总分数上加10分特长分
select id,name,(chinese+english+math+10) as '总分' FROM student2;

select * from student;


-- 2.8 聚合查询（使用聚合函数的查询）
  -- 常用的聚合函数： sum()  avg()  max()  min()  count()
-- 需求：  查询学生servlet的总成绩：  (sum() :求和函数)
select sum(servlet) as 'servlet的总成绩' from student;

-- 需求： 查询学生servlet的平均分
select avg(servlet) as 'servlet的平均分' FROM  student;

-- 需求： 查询当前servlet最高分
select max(servlet) as 'servlet最高分' from student;

-- 需求： 查询servlet最低分
select min(servlet) as 'servlet最低分' from student;

-- 需求： 统计当前有多少学生
select count(*) from student;

select count(id) from student;

-- 注意： count() 函数统计的数量不包含null的数据
-- 使用count统计表的记录数，要使用不包含null的字段
select count(age) from student;
select count(address) from student;


select * from student;
-- 2.9 分页查询 (limit 起始行， 查询几行)
-- 起始从0开始
-- 分页： 当前页  每页显示多少条
-- 分页查询当前页的数据的sql：select from stduent limit (当前页-1）*每页显示多少条

-- 需求: 查询第1、2条记录（第一页的数据）
select * from student limit 0,2;
-- 查询第3、4条记录（第二页数据）
select * from student limit 2,2;
-- 查询第5、6条记录（第三页数据）
select * from student limit 3,2;
-- 查询第7、8条记录 （没有数据不显示）
select * from student limit 4,2;
select * from student limit 5,2;

-- 2.10 查询排序  (order by)
-- 语法 ： order by 字段 asc/desc
-- asc:   顺序，正序。数值：递增， 字母：自然顺序（a-z）
-- desc:  倒序，反序。数值：递减， 字母：自然反序（z-a）

-- 默认情况下，按照插入记录顺序排序
select * from student;

-- 需求： 按照id顺序排序
select * from student order by id asc;
select * from student order by id ; -- 默认正序
select * from student order by id desc; -- 反序

-- 注意： 多个条件排序
-- 需求：按照servlet正序，按照jsp的倒序
select * from student order by servlet asc, jsp desc;

-- 2.11 分组查询(group by)
-- 需求： 查询男女的人数
-- 预期结果：
  --  男 3
  --  女 2
  --  1） 把学生按照性别分组(group by gender);
  --  2)  统计每组的人数(count(*))
select gender,count(*) from student group by gender;

-- 2.12 分组查询后帅选
-- 需求：  查询总人数大于2的性别
-- 1）查询男女的人数
-- 2）帅选出人数大于2的记录（having）
-- 注意：  分组之前的条件是where，分组后的条件使用having关键字
select gender,count(*) from student group by gender having count(*)>2;



-- 给student表添加servlet和jsp成绩列
ALTER TABLE student ADD servlet INT,ADD jsp INT;
ALTER TABLE student ADD servlet INT;
ALTER TABLE student ADD address VARCHAR(10);
DESC student;
UPDATE student SET servlet=70,jsp=85 WHERE id=1;
UPDATE student SET servlet=65,jsp=90 WHERE id=2;
UPDATE student SET gender='女' WHERE id=2;
UPDATE student SET address='广州天河' WHERE id=1;
UPDATE student SET address='广州天河' WHERE id=2;
UPDATE student SET address='广州番禺' WHERE id=3;

INSERT INTO student VALUES(4,'陈六','男',28,75,80,'');
INSERT INTO student VALUES(5,'李七','男',30,64,83,NULL);
INSERT INTO student VALUES(6,'李八八','男',35,67,82,'广州天河');






