package com.alinesno.infra.data.integration.entity;


import com.alinesno.infra.common.facade.mapper.entity.InfraBaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 定时任务表
 *
 * @author paul
 * @date 2024年3月10日
 */
@ToString
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("quartz")
public class QuartzEntity extends InfraBaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 任务描述
     */
    @ColumnComment("quartzDescription")
    @Excel(name = "quartzDescription")
    @TableField("quartz_description")
    private String quartzDescription;

    /**
     * 定时策略
     */
    @ColumnComment("quartzCron")
    @Excel(name = "quartzCron")
    @TableField("quartz_cron")
    private String quartzCron;

}