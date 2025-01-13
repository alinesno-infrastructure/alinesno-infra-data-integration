package com.alinesno.infra.data.integration.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alibaba.fastjson.annotation.JSONField;
import com.alinesno.infra.common.facade.mapper.entity.InfraBaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnType;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

/**
 * 功能名： 【作业执行历史】
 * 数据表：  job_run_his
 * 表备注：
 * @author paul
 * @version 1.0.0
 */
@ToString
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("job_run_his")
public class JobRunHisEntity extends InfraBaseEntity {
    private static final long serialVersionUID = 1L;

    // fields
    /**
    * 作业ID
    */
    @ColumnComment("作业ID")
    @Excel(name="作业ID")
    @ColumnType(length = 255)
    @TableField("run_job_id")
    private String runJobId;

    /**
    * 执行开始时间
    */
    @ColumnComment("执行开始时间")
    @Excel(name="执行开始时间",exportFormat = "yyyy-MM-dd")
    @ColumnType( value = MySqlTypeConstant.DATETIME,length = 25)
    @TableField("start_time")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /**
    * 执行结束时间
    */
    @ColumnComment("执行结束时间")
    @Excel(name="执行结束时间",exportFormat = "yyyy-MM-dd")
    @ColumnType( value = MySqlTypeConstant.DATETIME,length = 25)
    @TableField("end_time")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /**
    * 作业执行结果,0:失败；1:成功
    */
    @ColumnComment("作业执行结果,0:失败；1:成功")
    @Excel(name="作业执行结果,0:失败；1:成功")
    @ColumnType(length = 2)
    @TableField("run_status")
    private int runStatus;

    /**
    * 作业执行日志
    */
    @ColumnComment("作业执行日志")
    @Excel(name="作业执行日志")
    @TableField("remark")
    @ColumnType(value = MySqlTypeConstant.LONGTEXT)
    private String remark;

}
