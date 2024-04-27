package com.alinesno.infra.data.integration.entity;

import java.util.Date;

//import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONField;
import com.alinesno.infra.common.facade.mapper.entity.InfraBaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.afterturn.easypoi.excel.annotation.Excel;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 功能名： 【作业监控】
 * 数据表：  job_monitor
 * 表备注：
 *
 * @author paul
 * @date 2024年3月10日
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
