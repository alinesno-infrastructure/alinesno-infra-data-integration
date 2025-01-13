package com.alinesno.infra.data.integration.mapper;

import com.alinesno.infra.common.facade.mapper.repository.IBaseMapper;
import com.alinesno.infra.data.integration.entity.TransRunHisEntity;
import com.alinesno.infra.data.integration.vo.TaskCountVO;

/**
 * 【请填写功能名称】Mapper接口
 *
 * @author paul
 * @version 1.0.0
 */
public interface TransRunHisMapper extends IBaseMapper<TransRunHisEntity> {


    /**
     * 查询当天任务数、成功数量、失败数量
     *
     * @return 返回任务统计 VO
     */
    public TaskCountVO taskCount(String operatorId);

    /**
     * 查询当月任务数、成功数量、失败数量
     *
     * @return 返回任务统计 VO
     */
    public TaskCountVO taskCountMonth(String operatorId);


    /**
     * 查询当年任务数、成功数量、失败数量
     *
     * @return 返回任务统计 VO
     */
    public TaskCountVO taskCountYear(String operatorId);
}
