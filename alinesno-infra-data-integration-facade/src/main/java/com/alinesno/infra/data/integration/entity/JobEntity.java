package com.alinesno.infra.data.integration.entity;


import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 功能名： 【作业】
 * 数据表：  job
 * 表备注：
 *
 * @author paul
 * @version 1.0.0
 */
@ToString
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("job")
public class JobEntity extends BaseKettleEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 关联的转换任务id
     */
    @ColumnComment("transIds")
    @Excel(name = "transIds")
    @TableField("trans_ids")
    private String transIds;


    @ColumnComment("仓库名称")
    @ColumnType(length = 64)
    @TableField("git_id")
    private String gitId;


    @ColumnComment("作业文件路径")
    @ColumnType(length = 255)
    @TableField("relative_location")
    private String relativeLocation;

}
