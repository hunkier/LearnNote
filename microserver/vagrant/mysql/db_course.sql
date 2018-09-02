/*
 Navicat Premium Data Transfer

 Source Server         : microserver
 Source Server Type    : MySQL
 Source Server Version : 50636
 Source Host           : 192.168.33.10:3306
 Source Schema         : db_course

 Target Server Type    : MySQL
 Target Server Version : 50636
 File Encoding         : 65001

 Date: 02/09/2018 09:23:26
*/


CREATE DATABASE `db_course` CHARACTER SET 'utf8' COLLATE 'utf8_bin';

use  `db_course`;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for pe_course
-- ----------------------------
DROP TABLE IF EXISTS `pe_course`;
CREATE TABLE `pe_course`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '课程名称',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '课程描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for pe_user_course
-- ----------------------------
DROP TABLE IF EXISTS `pe_user_course`;
CREATE TABLE `pe_user_course`  (
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `course_id` int(11) NULL DEFAULT NULL COMMENT '课程id',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
