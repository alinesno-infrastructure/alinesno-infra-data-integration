package com.alinesno.infra.data.integration.service;


import com.alinesno.infra.common.facade.services.IBaseService;
import com.alinesno.infra.data.integration.entity.JobMonitorEntity;
import com.alinesno.infra.data.integration.vo.TaskCountVO;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 【请填写功能名称】Service接口
 *
 * @author paul
 * @version 1.0.0
 */
public interface IJobMonitorService extends IBaseService<JobMonitorEntity> {

    /**
     * 查询任务数、成功数量、失败数量
     *
     * @return 返回任务统计 VO
     */
    public TaskCountVO taskCount(String operatorId);

    /**
     * 时间段内的次数统计
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return 返回列表
     */
    public List<Map<String, Object>> getEcharts(String operatorId,Date start, Date end);

    /**
     * 更新监控状态
     *
     * @param jobMonitorEntity {@link JobMonitorEntity}
     * @param success          是否运行成功
     */
    public void updateMonitor(JobMonitorEntity jobMonitorEntity, boolean success);
}
