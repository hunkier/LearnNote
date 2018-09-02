/*
 Navicat Premium Data Transfer

 Source Server         : microserver
 Source Server Type    : MySQL
 Source Server Version : 50641
 Source Host           : 192.168.33.10:3306
 Source Schema         : db_user

 Target Server Type    : MySQL
 Target Server Version : 50641
 File Encoding         : 65001

 Date: 28/08/2018 11:45:18
*/

CREATE DATABASE `db_user` CHARACTER SET 'utf8' COLLATE 'utf8_bin';

use  `db_user`;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for pe_user
-- ----------------------------
DROP TABLE IF EXISTS `pe_user`;
CREATE TABLE `pe_user`  (
  `id` int(32) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '昵称',
  `password` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `real_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `mobile` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `email` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Compact;


-- ----------------------------
-- Table structure for pe_teacher
-- ----------------------------
DROP TABLE IF EXISTS `pe_teacher`;
CREATE TABLE `pe_teacher`  (
  `user_id` int(11) NOT NULL COMMENT '主键',
  `intro` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '介绍',
  `stars` int(11) NULL DEFAULT NULL COMMENT '评星',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Records of pe_user
-- ----------------------------
INSERT INTO `pe_user` VALUES (1, 'hunk', 'e10adc3949ba59abbe56e057f20f883e', 'hunk', '18812345678', '805015788@qq.com');

SET FOREIGN_KEY_CHECKS = 1;
