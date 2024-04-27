package com.alinesno.infra.data.integration.entity;

import java.util.Date;
//import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONField;
import com.alinesno.infra.common.facade.mapper.entity.InfraBaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.afterturn.easypoi.excel.annotation.Excel;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnType;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 功能名： 【转换执行历史】
 * 数据表：  trans_run_his
 * 表备注：
 * @author paul
 * @date 2024年3月10日
 */
@ToString
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("trans_run_his")
public class TransRunHisEntity extends InfraBaseEntity {
    private static final long serialVersionUID = 1L;

    // fields
    /**
    * 转换ID
    */
    @ColumnComment("转换ID")
    @Excel(name="转换ID")
    @ColumnType(length = 255)
    @TableField("monitor_trans_id")
    private String monitorTransId;

    /**
    * 执行开始时间
    */
    @ColumnComment("执行开始时间")
    @Excel(name="执行开始时间",exportFormat = "yyyy-MM-dd HH:mm:ss")
    @ColumnType( value = MySqlTypeConstant.DATETIME,length = 25)
    @TableField("start_time")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /**
    * 执行结束时间
    */
    @ColumnComment("执行结束时间")
    @Excel(name="执行结束时间",exportFormat = "yyyy-MM-dd HH:mm:ss")
    @ColumnType( value = MySqlTypeConstant.DATETIME,length = 25)
    @TableField("end_time")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date  endTime;

    /**
    * 转换执行结果,0:失败；1:成功
    */
    @ColumnComment("转换执行结果,0:失败；1:成功")
    @Excel(name="转换执行结果,0:失败；1:成功")
    @ColumnType(length = 2)
    @TableField("run_status")
    private int runStatus;

    /**
    * 转换执行日志
    */
    @ColumnComment("转换执行日志")
    @Excel(name="转换执行日志")
    @ColumnType(value = MySqlTypeConstant.LONGTEXT)
    @TableField("remark")
    private String remark;

}
