CREATE DATABASE IF NOT EXISTS `test` DEFAULT CHARACTER SET utf8;

-- DROP TABLE IF EXISTS `job`;
CREATE TABLE IF NOT EXISTS `job` (
  `JOB_NAME` VARCHAR(100) DEFAULT '' COMMENT '任务名',
  `JOB_GROUP` VARCHAR(30) DEFAULT 'DEFAULT' COMMENT '任务组',
  `JOB_CLASS_NAME` VARCHAR(200) DEFAULT '' COMMENT '任务实现类',
  `TRIGGER_TYPE` VARCHAR(10) DEFAULT '' COMMENT '触发器类型 SIMPLE 普通 CRON 表达式',
  `TRIGGER_CRON` VARCHAR(50) DEFAULT '' COMMENT 'CRON 表达式',
  `TRIGGER_INTERVAL` INT(11) DEFAULT 0 COMMENT '执行间隔（毫秒）',
  `TRIGGER_REPEAT` INT(2) DEFAULT 0 COMMENT '重复执行次数',
  `JAR_PATH` VARCHAR(255) DEFAULT '' COMMENT 'JAR包文件名，包括目录',
  `IS_ENABLE` CHAR(1) DEFAULT '0' COMMENT '0 禁用 1 启动',
   PRIMARY KEY (`JOB_NAME`)
) COMMENT = '定时任务';

-- DROP TABLE IF EXISTS `job_param`;
CREATE TABLE IF NOT EXISTS `job_param` (
  `JOB_NAME` VARCHAR(100) DEFAULT '' COMMENT '任务名',
  `PARAM_KEY` VARCHAR(30) DEFAULT '' COMMENT '参数名',
  `PARAM_VALUE` VARCHAR(200) DEFAULT '' COMMENT '参数值',
  `IS_ENABLE` CHAR(1) DEFAULT '0' COMMENT '0 禁用 1 启动',
   UNIQUE KEY UNIQUE_JOB_NAME_PARAM_KEY(`JOB_NAME`, `PARAM_KEY`)
) COMMENT = '定时任务参数';