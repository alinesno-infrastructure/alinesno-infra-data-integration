package com.alinesno.infra.data.integration.entity;

import java.util.Date;

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
 * 功能名： 【转换监控】
 * 数据表：  trans_monitor
 * 表备注：
 *
 * @author paul
 * @date 2024年3月10日
 */
@ToString
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("trans_monitor")
public class TransMonitorEntity  extends InfraBaseEntity {
    private static final long serialVersionUID = 1L;

    // fields
    /**
     * monitorTransId
     */
    @ColumnComment("monitorTransId")
    @Excel(name = "monitorTransId")
    @TableField("monitor_transid")
    private String monitorTransid;

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
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @TableField("next_execute_time")
    private Date nextExecuteTime;


}
