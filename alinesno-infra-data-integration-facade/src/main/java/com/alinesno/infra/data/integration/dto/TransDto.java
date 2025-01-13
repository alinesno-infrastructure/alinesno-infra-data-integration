package com.alinesno.infra.data.integration.dto;

import com.alinesno.infra.common.facade.mapper.entity.InfraBaseEntity;
import lombok.Data;

/**
 * @author paul
 * @version 1.0.0
 */
@Data
public class TransDto extends InfraBaseEntity {
//    private String id;
    private String categoryId;
    private String fileId;
    private String name;
    private String description;
    private String type;
    private String path;
    private String quartz;
    private String syncStrategy;
    private String logLevel;
    private String params;
    private String status;
    private String categoryName;
}
