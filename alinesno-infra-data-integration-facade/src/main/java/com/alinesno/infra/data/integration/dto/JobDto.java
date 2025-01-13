package com.alinesno.infra.data.integration.dto;



import com.alinesno.infra.common.facade.mapper.entity.InfraBaseEntity;
import lombok.Data;

import java.util.List;

/**
 * 任务类型的
 *
 * @author paul
 * @version 1.0.0
 */
@Data
public class JobDto extends InfraBaseEntity {

    private String categoryId;
    /**
     * 转换文件id
     */
    private String fileId;
    /**
     * 关联的转换任务id
     */
    private List<String> transIds;
    private String name;
    private String description;
    private String type;
    private String path;
    private String quartz;
    private String syncStrategy;
    private String logLevel;
    private String status;
    private String categoryName;
    private String gitId;
    private String relativeLocation;
}
