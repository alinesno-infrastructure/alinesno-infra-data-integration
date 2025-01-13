package com.alinesno.infra.data.integration.entity;


import com.alinesno.infra.common.facade.mapper.entity.InfraBaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * kettle属性的基础实现类
 *
 * @author paul
 * @version 1.0.0
 */
@ToString
@EqualsAndHashCode(callSuper = true)
@Data
public class BaseKettleEntity extends InfraBaseEntity {

    @TableField("name")
    private String name;
    
    @TableField("description")
    private String description;
   
    @TableField("type")
    private String type;
  
    @TableField("path")
    private String path;
   
    @TableField("quartz")
    private String quartz;
  
    @TableField("strategy")
    private String syncStrategy;
  
    @TableField("log_level")
    private String logLevel;
  
    @TableField("status")
    private Integer status;

    @TableField("category_id")
    private String categoryId;

    /**
     * 转换文件id
     */
    @TableField("file_id")
    private String fileId;


}
