package com.alinesno.infra.data.integration.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alinesno.infra.common.facade.mapper.entity.InfraBaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 功能名： 【任务分类】
 * 数据表：  category
 * 表备注：
 *
 * @author paul
 * @version 1.0.0
 */
@ToString
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("category")
public class CategoryEntity extends InfraBaseEntity {
    private static final long serialVersionUID = 1L;

    // fields
    @ColumnComment("categoryName")
    @Excel(name = "categoryName")
    @TableField("category_name")
    private String categoryName;

}
