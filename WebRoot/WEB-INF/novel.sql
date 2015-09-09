/*
Navicat MySQL Data Transfer

Source Server         : 公司本机
Source Server Version : 50015
Source Host           : 127.0.0.1:3306
Source Database       : novel

Target Server Type    : MYSQL
Target Server Version : 50015
File Encoding         : 65001

Date: 2015-09-09 15:59:09
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `book`
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
  `id` varchar(32) NOT NULL default '',
  `bookId` varchar(32) NOT NULL,
  `pageNum` varchar(8) default NULL COMMENT '页码',
  `content` longtext COMMENT '正文',
  `gatherDate` datetime default NULL COMMENT '时间',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of book
-- ----------------------------

-- ----------------------------
-- Table structure for `tianya`
-- ----------------------------
DROP TABLE IF EXISTS `tianya`;
CREATE TABLE `tianya` (
  `id` varchar(32) NOT NULL,
  `bookId` varchar(32) NOT NULL COMMENT '外键：book.bookId关联',
  `authorId` varchar(128) default NULL COMMENT '作者id',
  `authorName` varchar(128) default NULL COMMENT '作者name',
  `articleName` varchar(128) default NULL COMMENT '作品name',
  `articleId` varchar(128) default NULL COMMENT '作品id',
  `articleUrl` varchar(256) default NULL COMMENT '正则的替换url',
  `homeUrl` varchar(256) default NULL COMMENT '作品首页',
  `pageNum` varchar(64) default NULL COMMENT '采集页码',
  `state` int(1) default NULL COMMENT '0：未通过；1：可以采集',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tianya
-- ----------------------------
