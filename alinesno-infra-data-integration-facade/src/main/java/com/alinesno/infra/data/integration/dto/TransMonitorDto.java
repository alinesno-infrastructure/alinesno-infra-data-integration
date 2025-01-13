package com.alinesno.infra.data.integration.dto;

import com.alinesno.infra.common.facade.mapper.entity.InfraBaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * @author paul
 * @version 1.0.0
 */

@Data
public class TransMonitorDto extends InfraBaseEntity {
    private String monitorTransId;
    private int monitorSuccess;
    private int monitorFail;
    private int monitorStatus;
    private String runStatus;
    private Date lastExecuteTime;
    private Date nextExecuteTime;
    private String name;
    private String type;
}
