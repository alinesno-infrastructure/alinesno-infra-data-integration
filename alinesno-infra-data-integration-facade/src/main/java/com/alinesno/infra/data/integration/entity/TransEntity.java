package com.alinesno.infra.data.integration.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 功能名： 【转换任务】
 * 数据表：  trans
 * 表备注：
 * @author paul
 * @version 1.0.0
 */
@ToString
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("trans")
public class TransEntity extends BaseKettleEntity {

    @ColumnComment("仓库名称")
    @ColumnType(length = 64)
    @TableField("git_id")
    private String gitId;

    @ColumnComment("转换文件路径")
    @ColumnType(length = 255)
    @TableField("relative_location")
    private String relativeLocation;

}
