CREATE DATABASE IF NOT EXISTS `test` DEFAULT CHARACTER SET utf8;

-- DROP TABLE IF EXISTS `example`;
CREATE TABLE IF NOT EXISTS `example` (
  `id` BIGINT(20) AUTO_INCREMENT COMMENT 'Id',
  `name` VARCHAR(100) DEFAULT '' COMMENT '名称',
  `code` VARCHAR(20) DEFAULT '' COMMENT '编码',
  `state` CHAR(1) DEFAULT '' COMMENT '状态',
  `grade` TINYINT(1) DEFAULT 0 COMMENT '等级',
  `score` INT(4) DEFAULT 0 COMMENT '得分',
  `price` DECIMAL(14,2) DEFAULT 0.00 COMMENT '价格',
  `birth` DATE DEFAULT NULL COMMENT '生日',
  `sleep` TIME DEFAULT NULL COMMENT '睡眠',
  `createdDate` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `createdBy` BIGINT(20) DEFAULT -1 COMMENT '创建人',
  `modifiedDate` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `modifiedBy` BIGINT(20) DEFAULT -1 COMMENT '操作人',
  `version` TIMESTAMP DEFAULT 0,
  PRIMARY KEY (`id`)
) COMMENT = '示例';
