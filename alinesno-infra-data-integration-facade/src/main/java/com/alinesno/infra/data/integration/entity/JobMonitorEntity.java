package com.alinesno.infra.data.integration.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alibaba.fastjson.annotation.JSONField;
import com.alinesno.infra.common.facade.mapper.entity.InfraBaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

/**
 * 功能名： 【作业监控】
 * 数据表：  job_monitor
 * 表备注：
 *
 * @author paul
 * @version 1.0.0
 */
@ToString
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("job_monitor")
public class JobMonitorEntity extends InfraBaseEntity {
    private static final long serialVersionUID = 1L;

    // fields
    /**
     * monitorJobId
     */
    @ColumnComment("monitorJobId")
    @Excel(name = "monitorJobId")
    @TableField("monitor_job_id")
    private String monitorJobId;

    /**
     * monitorSuccess
     */
    @ColumnComment("monitorSuccess")
    @Excel(name = "monitorSuccess")
    @TableField("monitor_success")
    private int monitorSuccess;

    /**
     * monitorFail
     */
    @ColumnComment("monitorFail")
    @Excel(name = "monitorFail")
    @TableField("monitor_fail")
    private int monitorFail;

    /**
     * monitorStatus
     */
    @ColumnComment("monitorStatus")
    @Excel(name = "monitorStatus")
    @TableField("monitor_status")
    private int monitorStatus;

    /**
     * runStatus
     */
    @ColumnComment("runStatus")
    @Excel(name = "runStatus")
    @TableField("run_status")
    private String runStatus;

    /**
     * lastExecuteTime
     */
    @ColumnComment("lastExecuteTime")
    @Excel(name = "lastExecuteTime", exportFormat = "yyyy-MM-dd")
    @TableField("last_execute_time")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastExecuteTime;

    /**
     * nextExecuteTime
     */
    @ColumnComment("nextExecuteTime")
    @Excel(name = "nextExecuteTime", exportFormat = "yyyy-MM-dd")
    @TableField("next_execute_time")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date nextExecuteTime;

}
