package com.alinesno.infra.data.integration.dto;

import com.alinesno.infra.common.facade.mapper.entity.InfraBaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * @author paul
 * @version 1.0.0
 */
@Data
public class JobMonitorDto extends InfraBaseEntity {
//    private String id;
    private String monitorJobId;
    private int monitorSuccess;
    private int monitorFail;
    private int monitorStatus;
    private String runStatus;
    private Date lastExecuteTime;
    private Date nextExecuteTime;
    private String name;
    private String type;

//    @Override
//    public String getId() {
//        return id;
//    }
//
//    @Override
//    public void setId(String id) {
//        this.id = id;
//    }
}
