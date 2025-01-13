package com.alinesno.infra.data.integration.service;


import com.alinesno.infra.data.integration.entity.JobEntity;

import java.util.List;

/**
 * 【请填写功能名称】Service接口
 *
 * @author paul
 * @version 1.0.0
 */
public interface IJobService extends KettleService<JobEntity> {

    /**
     * 更新任务接口时更新定时任务
     *
     * @param entity {@link JobEntity}
     */
    public void updateByCron(JobEntity entity);

    /**
     * 删除任务接口时删除定时任务
     *
     * @param ids 一个或多个id
     */
    public void deleteByCron(List<String> ids);
}
