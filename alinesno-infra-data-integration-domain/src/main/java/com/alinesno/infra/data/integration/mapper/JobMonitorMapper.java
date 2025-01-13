package com.alinesno.infra.data.integration.mapper;

import com.alinesno.infra.common.facade.mapper.repository.IBaseMapper;
import com.alinesno.infra.data.integration.entity.JobMonitorEntity;
import com.alinesno.infra.data.integration.vo.TaskCountVO;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 【请填写功能名称】Mapper接口
 *
 * @author paul
 * @version 1.0.0
 */
@Repository
public interface JobMonitorMapper extends IBaseMapper<JobMonitorEntity> {

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

}
