package com.alinesno.infra.data.integration.service;

import com.alibaba.fastjson.JSONObject;
import com.alinesno.infra.common.facade.services.IBaseService;
import com.alinesno.infra.data.integration.entity.JobRunHisEntity;

/**
 * 【请填写功能名称】Service接口
 *
 * @author paul
 * @version 1.0.0
 */
public interface IJobRunHisService extends IBaseService<JobRunHisEntity>{

    /**
     * 新、保存作业执行记录状态
     *
     * @param jobRunHisEntity {@link JobRunHisEntity}
     */
    public void updateJobRunHis(JobRunHisEntity jobRunHisEntity);

    /**
     * 查询当天任务数、成功数量、失败数量
     *
     * @return 返回任务统计 VO
     */
    public JSONObject taskCount(String operatorId) ;


    /**
     * 查询当月任务数、成功数量、失败数量
     *
     * @return 返回任务统计 VO
     */
    public JSONObject taskCountChart(String operatorId);
}
