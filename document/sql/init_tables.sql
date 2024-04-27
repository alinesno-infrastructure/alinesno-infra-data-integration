/*
 Navicat Premium Data Transfer

 Source Server         : localhost3306
 Source Server Type    : MySQL
 Source Server Version : 80100
 Source Host           : localhost:3306
 Source Schema         : dev_alinesno_infra_data_etl_v100

 Target Server Type    : MySQL
 Target Server Version : 80100
 File Encoding         : 65001

 Date: 11/04/2024 15:49:43
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for build_git
-- ----------------------------
DROP TABLE IF EXISTS `build_git`;
CREATE TABLE `build_git`  (
                              `git_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '仓库名称',
                              `git_icon` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '仓库图标',
                              `git_host` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '仓库地址',
                              `git_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '仓库地址',
                              `branch_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '项目分支',
                              `git_user_id` int NULL DEFAULT NULL COMMENT 'gitlab账号id',
                              `git_namespace` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '仓库空间',
                              `git_type` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '仓库类型',
                              `inner_git` int NULL DEFAULT NULL COMMENT '内置gitlab',
                              `has_bing` int NULL DEFAULT NULL COMMENT '是否绑定',
                              `access_private_token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
                              `refresh_token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '刷新token',
                              `expires_in` int NULL DEFAULT NULL COMMENT '超时时间',
                              `bing_git_info` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '绑定第三方git账号信息',
                              `has_delete` int NULL DEFAULT NULL COMMENT '是否删除',
                              `delete_manager` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '删除人员',
                              `application_id` bigint NULL DEFAULT NULL COMMENT '所属应用',
                              `application_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '应用名称',
                              `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '所属租户',
                              `field_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '字段权限',
                              `department_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '部门权限',
                              `id` bigint NOT NULL COMMENT '主键',
                              `field_prop` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '字段属性',
                              `add_time` datetime NULL DEFAULT NULL COMMENT '添加时间',
                              `delete_time` datetime NULL DEFAULT NULL COMMENT '删除时间',
                              `has_status` int NULL DEFAULT NULL COMMENT '状态',
                              `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                              `operator_id` bigint NULL DEFAULT NULL COMMENT '操作员',
                              `last_update_operator_id` bigint NULL DEFAULT NULL COMMENT '最后更新操作员',
                              `git_user_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'gitlab账号',
                              `password` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'gitlab账号密码',
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
                             `category_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'categoryName',
                             `has_delete` int NULL DEFAULT NULL COMMENT '是否删除',
                             `delete_manager` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '删除人员',
                             `application_id` bigint NULL DEFAULT NULL COMMENT '所属应用',
                             `application_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '应用名称',
                             `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '所属租户',
                             `field_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '字段权限',
                             `department_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '部门权限',
                             `id` bigint NOT NULL COMMENT '主键',
                             `field_prop` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '字段属性',
                             `add_time` datetime NULL DEFAULT NULL COMMENT '添加时间',
                             `delete_time` datetime NULL DEFAULT NULL COMMENT '删除时间',
                             `has_status` int NULL DEFAULT NULL COMMENT '状态',
                             `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                             `operator_id` bigint NULL DEFAULT NULL COMMENT '操作员',
                             `last_update_operator_id` bigint NULL DEFAULT NULL COMMENT '最后更新操作员',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for job
-- ----------------------------
DROP TABLE IF EXISTS `job`;
CREATE TABLE `job`  (
                        `trans_ids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'transIds',
                        `git_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '仓库名称',
                        `relative_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '作业文件路径',
                        `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
                        `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
                        `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
                        `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
                        `quartz` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
                        `strategy` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
                        `log_level` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
                        `status` int NULL DEFAULT NULL,
                        `category_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
                        `file_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
                        `has_delete` int NULL DEFAULT NULL COMMENT '是否删除',
                        `delete_manager` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '删除人员',
                        `application_id` bigint NULL DEFAULT NULL COMMENT '所属应用',
                        `application_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '应用名称',
                        `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '所属租户',
                        `field_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '字段权限',
                        `department_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '部门权限',
                        `id` bigint NOT NULL COMMENT '主键',
                        `field_prop` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '字段属性',
                        `add_time` datetime NULL DEFAULT NULL COMMENT '添加时间',
                        `delete_time` datetime NULL DEFAULT NULL COMMENT '删除时间',
                        `has_status` int NULL DEFAULT NULL COMMENT '状态',
                        `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                        `operator_id` bigint NULL DEFAULT NULL COMMENT '操作员',
                        `last_update_operator_id` bigint NULL DEFAULT NULL COMMENT '最后更新操作员',
                        PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for job_monitor
-- ----------------------------
DROP TABLE IF EXISTS `job_monitor`;
CREATE TABLE `job_monitor`  (
                                `monitor_job_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'monitorJobId',
                                `monitor_success` int NULL DEFAULT NULL COMMENT 'monitorSuccess',
                                `monitor_fail` int NULL DEFAULT NULL COMMENT 'monitorFail',
                                `monitor_status` int NULL DEFAULT NULL COMMENT 'monitorStatus',
                                `run_status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'runStatus',
                                `last_execute_time` date NULL DEFAULT NULL COMMENT 'lastExecuteTime',
                                `next_execute_time` date NULL DEFAULT NULL COMMENT 'nextExecuteTime',
                                `has_delete` int NULL DEFAULT NULL COMMENT '是否删除',
                                `delete_manager` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '删除人员',
                                `application_id` bigint NULL DEFAULT NULL COMMENT '所属应用',
                                `application_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '应用名称',
                                `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '所属租户',
                                `field_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '字段权限',
                                `department_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '部门权限',
                                `id` bigint NOT NULL COMMENT '主键',
                                `field_prop` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '字段属性',
                                `add_time` datetime NULL DEFAULT NULL COMMENT '添加时间',
                                `delete_time` datetime NULL DEFAULT NULL COMMENT '删除时间',
                                `has_status` int NULL DEFAULT NULL COMMENT '状态',
                                `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                                `operator_id` bigint NULL DEFAULT NULL COMMENT '操作员',
                                `last_update_operator_id` bigint NULL DEFAULT NULL COMMENT '最后更新操作员',
                                PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for job_run_his
-- ----------------------------
DROP TABLE IF EXISTS `job_run_his`;
CREATE TABLE `job_run_his`  (
                                `run_job_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '作业ID',
                                `start_time` datetime NULL DEFAULT NULL COMMENT '执行开始时间',
                                `end_time` datetime NULL DEFAULT NULL COMMENT '执行结束时间',
                                `run_status` int NULL DEFAULT NULL COMMENT '作业执行结果,0:失败；1:成功',
                                `remark` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '作业执行日志',
                                `has_delete` int NULL DEFAULT NULL COMMENT '是否删除',
                                `delete_manager` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '删除人员',
                                `application_id` bigint NULL DEFAULT NULL COMMENT '所属应用',
                                `application_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '应用名称',
                                `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '所属租户',
                                `field_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '字段权限',
                                `department_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '部门权限',
                                `id` bigint NOT NULL COMMENT '主键',
                                `field_prop` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '字段属性',
                                `add_time` datetime NULL DEFAULT NULL COMMENT '添加时间',
                                `delete_time` datetime NULL DEFAULT NULL COMMENT '删除时间',
                                `has_status` int NULL DEFAULT NULL COMMENT '状态',
                                `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                                `operator_id` bigint NULL DEFAULT NULL COMMENT '操作员',
                                `last_update_operator_id` bigint NULL DEFAULT NULL COMMENT '最后更新操作员',
                                PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mq_serial_num
-- ----------------------------
DROP TABLE IF EXISTS `mq_serial_num`;
CREATE TABLE `mq_serial_num`  (
    `id` int UNSIGNED NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers`  (
                                       `SCHED_NAME` varchar(120) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                                       `TRIGGER_NAME` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                                       `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                                       `BLOB_DATA` blob NULL,
                                       PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
                                       CONSTRAINT `QRTZ_BLOB_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars`  (
                                   `SCHED_NAME` varchar(120) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                                   `CALENDAR_NAME` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                                   `CALENDAR` blob NOT NULL,
                                   PRIMARY KEY (`SCHED_NAME`, `CALENDAR_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers`  (
                                       `SCHED_NAME` varchar(120) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                                       `TRIGGER_NAME` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                                       `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                                       `CRON_EXPRESSION` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                                       `TIME_ZONE_ID` varchar(80) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
                                       PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
                                       CONSTRAINT `QRTZ_CRON_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers`  (
                                        `SCHED_NAME` varchar(120) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                                        `ENTRY_ID` varchar(95) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                                        `TRIGGER_NAME` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                                        `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                                        `INSTANCE_NAME` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                                        `FIRED_TIME` bigint NOT NULL,
                                        `SCHED_TIME` bigint NOT NULL,
                                        `PRIORITY` int NOT NULL,
                                        `STATE` varchar(16) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                                        `JOB_NAME` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
                                        `JOB_GROUP` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
                                        `IS_NONCONCURRENT` varchar(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
                                        `REQUESTS_RECOVERY` varchar(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
                                        PRIMARY KEY (`SCHED_NAME`, `ENTRY_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details`  (
                                     `SCHED_NAME` varchar(120) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                                     `JOB_NAME` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                                     `JOB_GROUP` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                                     `DESCRIPTION` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
                                     `JOB_CLASS_NAME` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                                     `IS_DURABLE` varchar(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                                     `IS_NONCONCURRENT` varchar(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                                     `IS_UPDATE_DATA` varchar(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                                     `REQUESTS_RECOVERY` varchar(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                                     `JOB_DATA` blob NULL,
                                     PRIMARY KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks`  (
                               `SCHED_NAME` varchar(120) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                               `LOCK_NAME` varchar(40) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                               PRIMARY KEY (`SCHED_NAME`, `LOCK_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps`  (
                                             `SCHED_NAME` varchar(120) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                                             `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                                             PRIMARY KEY (`SCHED_NAME`, `TRIGGER_GROUP`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state`  (
                                         `SCHED_NAME` varchar(120) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                                         `INSTANCE_NAME` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                                         `LAST_CHECKIN_TIME` bigint NOT NULL,
                                         `CHECKIN_INTERVAL` bigint NOT NULL,
                                         PRIMARY KEY (`SCHED_NAME`, `INSTANCE_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers`  (
                                         `SCHED_NAME` varchar(120) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                                         `TRIGGER_NAME` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                                         `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                                         `REPEAT_COUNT` bigint NOT NULL,
                                         `REPEAT_INTERVAL` bigint NOT NULL,
                                         `TIMES_TRIGGERED` bigint NOT NULL,
                                         PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
                                         CONSTRAINT `QRTZ_SIMPLE_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers`  (
                                          `SCHED_NAME` varchar(120) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                                          `TRIGGER_NAME` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                                          `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                                          `STR_PROP_1` varchar(512) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
                                          `STR_PROP_2` varchar(512) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
                                          `STR_PROP_3` varchar(512) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
                                          `INT_PROP_1` int NULL DEFAULT NULL,
                                          `INT_PROP_2` int NULL DEFAULT NULL,
                                          `LONG_PROP_1` bigint NULL DEFAULT NULL,
                                          `LONG_PROP_2` bigint NULL DEFAULT NULL,
                                          `DEC_PROP_1` decimal(13, 4) NULL DEFAULT NULL,
                                          `DEC_PROP_2` decimal(13, 4) NULL DEFAULT NULL,
                                          `BOOL_PROP_1` varchar(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
                                          `BOOL_PROP_2` varchar(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
                                          PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
                                          CONSTRAINT `QRTZ_SIMPROP_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers`  (
                                  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                                  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                                  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                                  `JOB_NAME` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                                  `JOB_GROUP` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                                  `DESCRIPTION` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
                                  `NEXT_FIRE_TIME` bigint NULL DEFAULT NULL,
                                  `PREV_FIRE_TIME` bigint NULL DEFAULT NULL,
                                  `PRIORITY` int NULL DEFAULT NULL,
                                  `TRIGGER_STATE` varchar(16) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                                  `TRIGGER_TYPE` varchar(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                                  `START_TIME` bigint NOT NULL,
                                  `END_TIME` bigint NULL DEFAULT NULL,
                                  `CALENDAR_NAME` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
                                  `MISFIRE_INSTR` smallint NULL DEFAULT NULL,
                                  `JOB_DATA` blob NULL,
                                  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
                                  INDEX `SCHED_NAME`(`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE,
                                  CONSTRAINT `QRTZ_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for quartz
-- ----------------------------
DROP TABLE IF EXISTS `quartz`;
CREATE TABLE `quartz`  (
                           `quartz_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'quartzDescription',
                           `quartz_cron` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'quartzCron',
                           `has_delete` int NULL DEFAULT NULL COMMENT '是否删除',
                           `delete_manager` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '删除人员',
                           `application_id` bigint NULL DEFAULT NULL COMMENT '所属应用',
                           `application_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '应用名称',
                           `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '所属租户',
                           `field_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '字段权限',
                           `department_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '部门权限',
                           `id` bigint NOT NULL COMMENT '主键',
                           `field_prop` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '字段属性',
                           `add_time` datetime NULL DEFAULT NULL COMMENT '添加时间',
                           `delete_time` datetime NULL DEFAULT NULL COMMENT '删除时间',
                           `has_status` int NULL DEFAULT NULL COMMENT '状态',
                           `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                           `operator_id` bigint NULL DEFAULT NULL COMMENT '操作员',
                           `last_update_operator_id` bigint NULL DEFAULT NULL COMMENT '最后更新操作员',
                           PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for trans
-- ----------------------------
DROP TABLE IF EXISTS `trans`;
CREATE TABLE `trans`  (
                          `git_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '仓库名称',
                          `relative_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '转换文件路径',
                          `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
                          `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
                          `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
                          `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
                          `quartz` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
                          `strategy` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
                          `log_level` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
                          `status` int NULL DEFAULT NULL,
                          `category_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
                          `file_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
                          `has_delete` int NULL DEFAULT NULL COMMENT '是否删除',
                          `delete_manager` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '删除人员',
                          `application_id` bigint NULL DEFAULT NULL COMMENT '所属应用',
                          `application_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '应用名称',
                          `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '所属租户',
                          `field_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '字段权限',
                          `department_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '部门权限',
                          `id` bigint NOT NULL COMMENT '主键',
                          `field_prop` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '字段属性',
                          `add_time` datetime NULL DEFAULT NULL COMMENT '添加时间',
                          `delete_time` datetime NULL DEFAULT NULL COMMENT '删除时间',
                          `has_status` int NULL DEFAULT NULL COMMENT '状态',
                          `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                          `operator_id` bigint NULL DEFAULT NULL COMMENT '操作员',
                          `last_update_operator_id` bigint NULL DEFAULT NULL COMMENT '最后更新操作员',
                          PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for trans_monitor
-- ----------------------------
DROP TABLE IF EXISTS `trans_monitor`;
CREATE TABLE `trans_monitor`  (
                                  `monitor_transid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'monitorTransId',
                                  `monitor_success` int NULL DEFAULT NULL COMMENT 'monitorSuccess',
                                  `monitor_fail` int NULL DEFAULT NULL COMMENT 'monitorFail',
                                  `monitor_status` int NULL DEFAULT NULL COMMENT 'monitorStatus',
                                  `run_status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'runStatus',
                                  `last_execute_time` date NULL DEFAULT NULL COMMENT 'lastExecuteTime',
                                  `next_execute_time` date NULL DEFAULT NULL COMMENT 'nextExecuteTime',
                                  `has_delete` int NULL DEFAULT NULL COMMENT '是否删除',
                                  `delete_manager` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '删除人员',
                                  `application_id` bigint NULL DEFAULT NULL COMMENT '所属应用',
                                  `application_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '应用名称',
                                  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '所属租户',
                                  `field_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '字段权限',
                                  `department_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '部门权限',
                                  `id` bigint NOT NULL COMMENT '主键',
                                  `field_prop` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '字段属性',
                                  `add_time` datetime NULL DEFAULT NULL COMMENT '添加时间',
                                  `delete_time` datetime NULL DEFAULT NULL COMMENT '删除时间',
                                  `has_status` int NULL DEFAULT NULL COMMENT '状态',
                                  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                                  `operator_id` bigint NULL DEFAULT NULL COMMENT '操作员',
                                  `last_update_operator_id` bigint NULL DEFAULT NULL COMMENT '最后更新操作员',
                                  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for trans_run_his
-- ----------------------------
DROP TABLE IF EXISTS `trans_run_his`;
CREATE TABLE `trans_run_his`  (
                                  `monitor_trans_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '转换ID',
                                  `start_time` datetime NULL DEFAULT NULL COMMENT '执行开始时间',
                                  `end_time` datetime NULL DEFAULT NULL COMMENT '执行结束时间',
                                  `run_status` int NULL DEFAULT NULL COMMENT '转换执行结果,0:失败；1:成功',
                                  `remark` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '转换执行日志',
                                  `has_delete` int NULL DEFAULT NULL COMMENT '是否删除',
                                  `delete_manager` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '删除人员',
                                  `application_id` bigint NULL DEFAULT NULL COMMENT '所属应用',
                                  `application_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '应用名称',
                                  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '所属租户',
                                  `field_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '字段权限',
                                  `department_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '部门权限',
                                  `id` bigint NOT NULL COMMENT '主键',
                                  `field_prop` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '字段属性',
                                  `add_time` datetime NULL DEFAULT NULL COMMENT '添加时间',
                                  `delete_time` datetime NULL DEFAULT NULL COMMENT '删除时间',
                                  `has_status` int NULL DEFAULT NULL COMMENT '状态',
                                  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                                  `operator_id` bigint NULL DEFAULT NULL COMMENT '操作员',
                                  `last_update_operator_id` bigint NULL DEFAULT NULL COMMENT '最后更新操作员',
                                  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
