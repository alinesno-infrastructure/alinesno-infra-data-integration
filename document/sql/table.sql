-- ----------------------------
-- Table structure for mq_serial_num
-- ----------------------------
DROP TABLE IF EXISTS `mq_serial_num`;
CREATE TABLE `mq_serial_num`  (
    `id` int(10) UNSIGNED NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of mq_serial_num
-- ----------------------------
INSERT INTO `mq_serial_num` VALUES (0);
INSERT INTO `mq_serial_num` VALUES (1);
INSERT INTO `mq_serial_num` VALUES (2);
INSERT INTO `mq_serial_num` VALUES (3);
INSERT INTO `mq_serial_num` VALUES (4);
INSERT INTO `mq_serial_num` VALUES (5);
INSERT INTO `mq_serial_num` VALUES (6);
INSERT INTO `mq_serial_num` VALUES (7);
INSERT INTO `mq_serial_num` VALUES (8);
INSERT INTO `mq_serial_num` VALUES (9);
INSERT INTO `mq_serial_num` VALUES (10);
INSERT INTO `mq_serial_num` VALUES (11);
INSERT INTO `mq_serial_num` VALUES (12);
INSERT INTO `mq_serial_num` VALUES (13);
INSERT INTO `mq_serial_num` VALUES (14);
INSERT INTO `mq_serial_num` VALUES (15);
INSERT INTO `mq_serial_num` VALUES (16);
INSERT INTO `mq_serial_num` VALUES (17);
INSERT INTO `mq_serial_num` VALUES (18);
INSERT INTO `mq_serial_num` VALUES (19);
INSERT INTO `mq_serial_num` VALUES (20);
INSERT INTO `mq_serial_num` VALUES (21);
INSERT INTO `mq_serial_num` VALUES (22);
INSERT INTO `mq_serial_num` VALUES (23);
INSERT INTO `mq_serial_num` VALUES (24);
INSERT INTO `mq_serial_num` VALUES (25);
INSERT INTO `mq_serial_num` VALUES (26);
INSERT INTO `mq_serial_num` VALUES (27);
INSERT INTO `mq_serial_num` VALUES (28);
INSERT INTO `mq_serial_num` VALUES (29);

-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers`  (
                                       `SCHED_NAME` varchar(120)  NOT NULL,
                                       `TRIGGER_NAME` varchar(200)  NOT NULL,
                                       `TRIGGER_GROUP` varchar(200)  NOT NULL,
                                       `BLOB_DATA` blob NULL,
                                       PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
                                       CONSTRAINT `QRTZ_BLOB_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
)  ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of qrtz_blob_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars`  (
                                   `SCHED_NAME` varchar(120)  NOT NULL,
                                   `CALENDAR_NAME` varchar(200)  NOT NULL,
                                   `CALENDAR` blob NOT NULL,
                                   PRIMARY KEY (`SCHED_NAME`, `CALENDAR_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of qrtz_calendars
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers`  (
                                       `SCHED_NAME` varchar(120)  NOT NULL,
                                       `TRIGGER_NAME` varchar(200)  NOT NULL,
                                       `TRIGGER_GROUP` varchar(200)  NOT NULL,
                                       `CRON_EXPRESSION` varchar(200)  NOT NULL,
                                       `TIME_ZONE_ID` varchar(80)  NULL DEFAULT NULL,
                                       PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
                                       CONSTRAINT `QRTZ_CRON_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of qrtz_cron_triggers
-- ----------------------------
INSERT INTO `qrtz_cron_triggers` VALUES ('Scheduler', 'JOB_TRIGGER@0ea17cf6b776b1d97993a99814284fc2', 'JOB_TRIGGER_GROUP@0ea17cf6b776b1d97993a99814284fc2', '0 0/5 * * * ? *', 'Asia/Shanghai');
INSERT INTO `qrtz_cron_triggers` VALUES ('Scheduler', 'JOB_TRIGGER@15de381b73b53695a87ba6ce5b3aacb9', 'JOB_TRIGGER_GROUP@15de381b73b53695a87ba6ce5b3aacb9', '0 0/5 * * * ? *', 'Asia/Shanghai');
INSERT INTO `qrtz_cron_triggers` VALUES ('Scheduler', 'JOB_TRIGGER@24645d95a0806d5fcf1794f3ae153cf1', 'JOB_TRIGGER_GROUP@24645d95a0806d5fcf1794f3ae153cf1', '0 0/5 * * * ? *', 'Asia/Shanghai');
INSERT INTO `qrtz_cron_triggers` VALUES ('Scheduler', 'JOB_TRIGGER@3f46e8b5f56a09b0675cf1b110d1eece', 'JOB_TRIGGER_GROUP@3f46e8b5f56a09b0675cf1b110d1eece', '0 0/5 * * * ? *', 'Asia/Shanghai');
INSERT INTO `qrtz_cron_triggers` VALUES ('Scheduler', 'JOB_TRIGGER@54e7fdbb7f3592501da5b97607c4c4b7', 'JOB_TRIGGER_GROUP@54e7fdbb7f3592501da5b97607c4c4b7', '0 0/5 * * * ? *', 'Asia/Shanghai');
INSERT INTO `qrtz_cron_triggers` VALUES ('Scheduler', 'JOB_TRIGGER@8f561d9c029f76b5a75ee18cba9ddb80', 'JOB_TRIGGER_GROUP@8f561d9c029f76b5a75ee18cba9ddb80', '0 0/5 * * * ? *', 'Asia/Shanghai');
INSERT INTO `qrtz_cron_triggers` VALUES ('Scheduler', 'JOB_TRIGGER@a292e78e4e5ea3f3badb7c8034bed5e0', 'JOB_TRIGGER_GROUP@a292e78e4e5ea3f3badb7c8034bed5e0', '0 0/5 * * * ? *', 'Asia/Shanghai');
INSERT INTO `qrtz_cron_triggers` VALUES ('Scheduler', 'JOB_TRIGGER@c5e0f9f664504ebb95c40d469aa65c7a', 'JOB_TRIGGER_GROUP@c5e0f9f664504ebb95c40d469aa65c7a', '0 0/5 * * * ? *', 'Asia/Shanghai');

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers`  (
                                        `SCHED_NAME` varchar(120)  NOT NULL,
                                        `ENTRY_ID` varchar(95)  NOT NULL,
                                        `TRIGGER_NAME` varchar(200)  NOT NULL,
                                        `TRIGGER_GROUP` varchar(200)  NOT NULL,
                                        `INSTANCE_NAME` varchar(200)  NOT NULL,
                                        `FIRED_TIME` bigint(20) NOT NULL,
                                        `SCHED_TIME` bigint(20) NOT NULL,
                                        `PRIORITY` int(11) NOT NULL,
                                        `STATE` varchar(16)  NOT NULL,
                                        `JOB_NAME` varchar(200)  NULL DEFAULT NULL,
                                        `JOB_GROUP` varchar(200)  NULL DEFAULT NULL,
                                        `IS_NONCONCURRENT` varchar(1)  NULL DEFAULT NULL,
                                        `REQUESTS_RECOVERY` varchar(1)  NULL DEFAULT NULL,
                                        PRIMARY KEY (`SCHED_NAME`, `ENTRY_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of qrtz_fired_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details`  (
                                     `SCHED_NAME` varchar(120)  NOT NULL,
                                     `JOB_NAME` varchar(200)  NOT NULL,
                                     `JOB_GROUP` varchar(200)  NOT NULL,
                                     `DESCRIPTION` varchar(250)  NULL DEFAULT NULL,
                                     `JOB_CLASS_NAME` varchar(250)  NOT NULL,
                                     `IS_DURABLE` varchar(1)  NOT NULL,
                                     `IS_NONCONCURRENT` varchar(1)  NOT NULL,
                                     `IS_UPDATE_DATA` varchar(1)  NOT NULL,
                                     `REQUESTS_RECOVERY` varchar(1)  NOT NULL,
                                     `JOB_DATA` blob NULL,
                                     PRIMARY KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE
)ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of qrtz_job_details
-- ----------------------------
INSERT INTO `qrtz_job_details` VALUES ('Scheduler', 'JOB@0ea17cf6b776b1d97993a99814284fc2', 'JOB_GROUP@0ea17cf6b776b1d97993a99814284fc2', NULL, 'com.alinesno.cloud.data.etl.quartz.TransQuartz', '0', '0', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787000737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C77080000001000000001740002696474002030656131376366366237373662316439373939336139393831343238346663327800);
INSERT INTO `qrtz_job_details` VALUES ('Scheduler', 'JOB@15de381b73b53695a87ba6ce5b3aacb9', 'JOB_GROUP@15de381b73b53695a87ba6ce5b3aacb9', NULL, 'com.alinesno.cloud.data.etl.quartz.JobQuartz', '0', '0', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787000737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C77080000001000000001740002696474002031356465333831623733623533363935613837626136636535623361616362397800);
INSERT INTO `qrtz_job_details` VALUES ('Scheduler', 'JOB@24645d95a0806d5fcf1794f3ae153cf1', 'JOB_GROUP@24645d95a0806d5fcf1794f3ae153cf1', NULL, 'com.alinesno.cloud.data.etl.quartz.TransQuartz', '0', '0', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787000737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C77080000001000000001740002696474002032343634356439356130383036643566636631373934663361653135336366317800);
INSERT INTO `qrtz_job_details` VALUES ('Scheduler', 'JOB@3f46e8b5f56a09b0675cf1b110d1eece', 'JOB_GROUP@3f46e8b5f56a09b0675cf1b110d1eece', NULL, 'com.alinesno.cloud.data.etl.quartz.TransQuartz', '0', '0', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787000737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C77080000001000000001740002696474002033663436653862356635366130396230363735636631623131306431656563657800);
INSERT INTO `qrtz_job_details` VALUES ('Scheduler', 'JOB@54e7fdbb7f3592501da5b97607c4c4b7', 'JOB_GROUP@54e7fdbb7f3592501da5b97607c4c4b7', NULL, 'com.alinesno.cloud.data.etl.quartz.TransQuartz', '0', '0', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787000737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C77080000001000000001740002696474002035346537666462623766333539323530316461356239373630376334633462377800);
INSERT INTO `qrtz_job_details` VALUES ('Scheduler', 'JOB@8f561d9c029f76b5a75ee18cba9ddb80', 'JOB_GROUP@8f561d9c029f76b5a75ee18cba9ddb80', NULL, 'com.alinesno.cloud.data.etl.quartz.TransQuartz', '0', '0', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787000737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C77080000001000000001740002696474002038663536316439633032396637366235613735656531386362613964646238307800);
INSERT INTO `qrtz_job_details` VALUES ('Scheduler', 'JOB@a292e78e4e5ea3f3badb7c8034bed5e0', 'JOB_GROUP@a292e78e4e5ea3f3badb7c8034bed5e0', NULL, 'com.alinesno.cloud.data.etl.quartz.JobQuartz', '0', '0', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787000737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C77080000001000000001740002696474002061323932653738653465356561336633626164623763383033346265643565307800);
INSERT INTO `qrtz_job_details` VALUES ('Scheduler', 'JOB@c5e0f9f664504ebb95c40d469aa65c7a', 'JOB_GROUP@c5e0f9f664504ebb95c40d469aa65c7a', NULL, 'com.alinesno.cloud.data.etl.quartz.JobQuartz', '0', '0', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787000737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C77080000001000000001740002696474002063356530663966363634353034656262393563343064343639616136356337617800);

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks`  (
                               `SCHED_NAME` varchar(120)  NOT NULL,
                               `LOCK_NAME` varchar(40)  NOT NULL,
                               PRIMARY KEY (`SCHED_NAME`, `LOCK_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of qrtz_locks
-- ----------------------------
INSERT INTO `qrtz_locks` VALUES ('Scheduler', 'TRIGGER_ACCESS');

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps`  (
                                             `SCHED_NAME` varchar(120)  NOT NULL,
                                             `TRIGGER_GROUP` varchar(200)  NOT NULL,
                                             PRIMARY KEY (`SCHED_NAME`, `TRIGGER_GROUP`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of qrtz_paused_trigger_grps
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state`  (
                                         `SCHED_NAME` varchar(120)  NOT NULL,
                                         `INSTANCE_NAME` varchar(200)  NOT NULL,
                                         `LAST_CHECKIN_TIME` bigint(20) NOT NULL,
                                         `CHECKIN_INTERVAL` bigint(20) NOT NULL,
                                         PRIMARY KEY (`SCHED_NAME`, `INSTANCE_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of qrtz_scheduler_state
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers`  (
                                         `SCHED_NAME` varchar(120)  NOT NULL,
                                         `TRIGGER_NAME` varchar(200)  NOT NULL,
                                         `TRIGGER_GROUP` varchar(200)  NOT NULL,
                                         `REPEAT_COUNT` bigint(20) NOT NULL,
                                         `REPEAT_INTERVAL` bigint(20) NOT NULL,
                                         `TIMES_TRIGGERED` bigint(20) NOT NULL,
                                         PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
                                         CONSTRAINT `QRTZ_SIMPLE_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of qrtz_simple_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers`  (
                                          `SCHED_NAME` varchar(120)  NOT NULL,
                                          `TRIGGER_NAME` varchar(200)  NOT NULL,
                                          `TRIGGER_GROUP` varchar(200)  NOT NULL,
                                          `STR_PROP_1` varchar(512)  NULL DEFAULT NULL,
                                          `STR_PROP_2` varchar(512)  NULL DEFAULT NULL,
                                          `STR_PROP_3` varchar(512)  NULL DEFAULT NULL,
                                          `INT_PROP_1` int(11) NULL DEFAULT NULL,
                                          `INT_PROP_2` int(11) NULL DEFAULT NULL,
                                          `LONG_PROP_1` bigint(20) NULL DEFAULT NULL,
                                          `LONG_PROP_2` bigint(20) NULL DEFAULT NULL,
                                          `DEC_PROP_1` decimal(13, 4) NULL DEFAULT NULL,
                                          `DEC_PROP_2` decimal(13, 4) NULL DEFAULT NULL,
                                          `BOOL_PROP_1` varchar(1)  NULL DEFAULT NULL,
                                          `BOOL_PROP_2` varchar(1)  NULL DEFAULT NULL,
                                          PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
                                          CONSTRAINT `QRTZ_SIMPROP_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of qrtz_simprop_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers`  (
                                  `SCHED_NAME` varchar(120)  NOT NULL,
                                  `TRIGGER_NAME` varchar(200)  NOT NULL,
                                  `TRIGGER_GROUP` varchar(200)  NOT NULL,
                                  `JOB_NAME` varchar(200)  NOT NULL,
                                  `JOB_GROUP` varchar(200)  NOT NULL,
                                  `DESCRIPTION` varchar(250)  NULL DEFAULT NULL,
                                  `NEXT_FIRE_TIME` bigint(20) NULL DEFAULT NULL,
                                  `PREV_FIRE_TIME` bigint(20) NULL DEFAULT NULL,
                                  `PRIORITY` int(11) NULL DEFAULT NULL,
                                  `TRIGGER_STATE` varchar(16)  NOT NULL,
                                  `TRIGGER_TYPE` varchar(8)  NOT NULL,
                                  `START_TIME` bigint(20) NOT NULL,
                                  `END_TIME` bigint(20) NULL DEFAULT NULL,
                                  `CALENDAR_NAME` varchar(200)  NULL DEFAULT NULL,
                                  `MISFIRE_INSTR` smallint(6) NULL DEFAULT NULL,
                                  `JOB_DATA` blob NULL,
                                  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
                                  INDEX `SCHED_NAME`(`SCHED_NAME` ASC, `JOB_NAME` ASC, `JOB_GROUP` ASC) USING BTREE,
                                  CONSTRAINT `QRTZ_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of qrtz_triggers
-- ----------------------------
INSERT INTO `qrtz_triggers` VALUES ('Scheduler', 'JOB_TRIGGER@0ea17cf6b776b1d97993a99814284fc2', 'JOB_TRIGGER_GROUP@0ea17cf6b776b1d97993a99814284fc2', 'JOB@0ea17cf6b776b1d97993a99814284fc2', 'JOB_GROUP@0ea17cf6b776b1d97993a99814284fc2', NULL, 1703750400000, 1703750100000, 5, 'WAITING', 'CRON', 1684722094000, 0, NULL, 0, '');
INSERT INTO `qrtz_triggers` VALUES ('Scheduler', 'JOB_TRIGGER@15de381b73b53695a87ba6ce5b3aacb9', 'JOB_TRIGGER_GROUP@15de381b73b53695a87ba6ce5b3aacb9', 'JOB@15de381b73b53695a87ba6ce5b3aacb9', 'JOB_GROUP@15de381b73b53695a87ba6ce5b3aacb9', NULL, 1703750400000, 1703750100000, 5, 'WAITING', 'CRON', 1684480940000, 0, NULL, 0, '');
INSERT INTO `qrtz_triggers` VALUES ('Scheduler', 'JOB_TRIGGER@24645d95a0806d5fcf1794f3ae153cf1', 'JOB_TRIGGER_GROUP@24645d95a0806d5fcf1794f3ae153cf1', 'JOB@24645d95a0806d5fcf1794f3ae153cf1', 'JOB_GROUP@24645d95a0806d5fcf1794f3ae153cf1', NULL, 1703750400000, 1703750100000, 5, 'WAITING', 'CRON', 1685608732000, 0, NULL, 0, '');
INSERT INTO `qrtz_triggers` VALUES ('Scheduler', 'JOB_TRIGGER@3f46e8b5f56a09b0675cf1b110d1eece', 'JOB_TRIGGER_GROUP@3f46e8b5f56a09b0675cf1b110d1eece', 'JOB@3f46e8b5f56a09b0675cf1b110d1eece', 'JOB_GROUP@3f46e8b5f56a09b0675cf1b110d1eece', NULL, 1703750400000, 1703750100000, 5, 'WAITING', 'CRON', 1684479722000, 0, NULL, 0, '');
INSERT INTO `qrtz_triggers` VALUES ('Scheduler', 'JOB_TRIGGER@54e7fdbb7f3592501da5b97607c4c4b7', 'JOB_TRIGGER_GROUP@54e7fdbb7f3592501da5b97607c4c4b7', 'JOB@54e7fdbb7f3592501da5b97607c4c4b7', 'JOB_GROUP@54e7fdbb7f3592501da5b97607c4c4b7', NULL, 1703750400000, 1703750100000, 5, 'WAITING', 'CRON', 1684480974000, 0, NULL, 0, '');
INSERT INTO `qrtz_triggers` VALUES ('Scheduler', 'JOB_TRIGGER@8f561d9c029f76b5a75ee18cba9ddb80', 'JOB_TRIGGER_GROUP@8f561d9c029f76b5a75ee18cba9ddb80', 'JOB@8f561d9c029f76b5a75ee18cba9ddb80', 'JOB_GROUP@8f561d9c029f76b5a75ee18cba9ddb80', NULL, 1703750400000, 1703750100000, 5, 'WAITING', 'CRON', 1685924019000, 0, NULL, 0, '');
INSERT INTO `qrtz_triggers` VALUES ('Scheduler', 'JOB_TRIGGER@a292e78e4e5ea3f3badb7c8034bed5e0', 'JOB_TRIGGER_GROUP@a292e78e4e5ea3f3badb7c8034bed5e0', 'JOB@a292e78e4e5ea3f3badb7c8034bed5e0', 'JOB_GROUP@a292e78e4e5ea3f3badb7c8034bed5e0', NULL, 1703750400000, 1703750100000, 5, 'WAITING', 'CRON', 1684467133000, 0, NULL, 0, '');
INSERT INTO `qrtz_triggers` VALUES ('Scheduler', 'JOB_TRIGGER@c5e0f9f664504ebb95c40d469aa65c7a', 'JOB_TRIGGER_GROUP@c5e0f9f664504ebb95c40d469aa65c7a', 'JOB@c5e0f9f664504ebb95c40d469aa65c7a', 'JOB_GROUP@c5e0f9f664504ebb95c40d469aa65c7a', NULL, 1703750400000, 1703750100000, 5, 'WAITING', 'CRON', 1684480366000, 0, NULL, 0, '');
