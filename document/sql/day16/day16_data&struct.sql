/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50616
Source Host           : localhost:3306
Source Database       : day16

Target Server Type    : MYSQL
Target Server Version : 50616
File Encoding         : 65001

Date: 2015-08-07 16:02:06
*/

create database day16 default character set utf8;

use day16;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for dept
-- ----------------------------
DROP TABLE IF EXISTS `dept`;
CREATE TABLE `dept` (
  `id` int(11) NOT NULL,
  `deptName` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dept
-- ----------------------------
INSERT INTO `dept` VALUES ('1', '软件开发部');
INSERT INTO `dept` VALUES ('2', '应用维护部');
INSERT INTO `dept` VALUES ('3', '秘书部');
INSERT INTO `dept` VALUES ('4', '总经办');
INSERT INTO `dept` VALUES ('5', '秘书部');

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
  `id` int(11) NOT NULL,
  `empName` varchar(20) DEFAULT NULL,
  `deptId` int(11) DEFAULT NULL,
  `bossId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `employee_dept_fk` (`deptId`),
  CONSTRAINT `employee_dept_fk` FOREIGN KEY (`deptId`) REFERENCES `dept` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of employee
-- ----------------------------
INSERT INTO `employee` VALUES ('1', '张三', '1', null);
INSERT INTO `employee` VALUES ('2', '李四', '1', '1');
INSERT INTO `employee` VALUES ('3', '王五', '2', '2');
INSERT INTO `employee` VALUES ('4', '陈六', '3', '3');

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `id` int(4) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('0001', '张三');
INSERT INTO `student` VALUES ('0002', '李四');
INSERT INTO `student` VALUES ('0003', '王五');

-- ----------------------------
-- Table structure for test_log
-- ----------------------------
DROP TABLE IF EXISTS `test_log`;
CREATE TABLE `test_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of test_log
-- ----------------------------
INSERT INTO `test_log` VALUES ('1', '员工表插入了一条数据');
INSERT INTO `test_log` VALUES ('2', '员工表插入了一条数据');
INSERT INTO `test_log` VALUES ('3', '员工表修改了一条数据');
INSERT INTO `test_log` VALUES ('4', '员工表删除了一条记录');
INSERT INTO `test_log` VALUES ('5', '员工表删除了一条记录');

-- ----------------------------
-- Procedure structure for pro_findById
-- ----------------------------
DROP PROCEDURE IF EXISTS `pro_findById`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `pro_findById`(in eid int)
begin 
  select * from employee where id=eid;
end
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for pro_findById2
-- ----------------------------
DROP PROCEDURE IF EXISTS `pro_findById2`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `pro_findById2`(in eid int,out vname varchar(20))
begin 
  select empName into vname from employee where id=eid;
end
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for pro_test
-- ----------------------------
DROP PROCEDURE IF EXISTS `pro_test`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `pro_test`()
begin 
    -- 可以写多个sql语句
    select * from employee;
  end
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for pro_testIf
-- ----------------------------
DROP PROCEDURE IF EXISTS `pro_testIf`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `pro_testIf`(in num int, out str varchar(20))
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
end
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for pro_testInOut
-- ----------------------------
DROP PROCEDURE IF EXISTS `pro_testInOut`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `pro_testInOut`(inout n int)
begin 
  -- 查看变量
  select n;
  set n=500;
end
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for pro_testOut
-- ----------------------------
DROP PROCEDURE IF EXISTS `pro_testOut`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `pro_testOut`(out str varchar(20))
begin
      -- 给参数赋值
  set str='hello sql procedure';
end
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for pro_testWhile
-- ----------------------------
DROP PROCEDURE IF EXISTS `pro_testWhile`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `pro_testWhile`(in num int,out result int)
begin 
  -- 定义一个局部变量
  declare i int default 1;
  declare vsum int default 0;
  while i<=num do 
      set vsum=vsum+i;
      set i=i+1;
  end while;
  set result=vsum;
end
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `tri_empAdd`;
DELIMITER ;;
CREATE TRIGGER `tri_empAdd` AFTER INSERT ON `employee` FOR EACH ROW -- 当员工表插入一条数据时
  insert into test_log(content) values('员工表插入了一条数据')
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `tri_empUpdate`;
DELIMITER ;;
CREATE TRIGGER `tri_empUpdate` AFTER UPDATE ON `employee` FOR EACH ROW -- 当员工表更新了一条数据
  insert into test_log(content) values ('员工表修改了一条数据')
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `tri_empDel`;
DELIMITER ;;
CREATE TRIGGER `tri_empDel` AFTER DELETE ON `employee` FOR EACH ROW insert into test_log(content) values('员工表删除了一条记录')
;;
DELIMITER ;
