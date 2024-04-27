package com.alinesno.infra.data.integration.service;


import com.alinesno.infra.common.facade.services.IBaseService;
import com.alinesno.infra.data.integration.entity.TransRunHisEntity;
import com.alinesno.infra.data.integration.vo.TaskCountVO;

/**
 * 【请填写功能名称】Service接口
 *
 * @author paul
 * @date 2024年3月10日
 */
public interface ITransRunHisService extends IBaseService<TransRunHisEntity>{
    /**
     * 新、保存作业执行记录状态
     *
     * @param transRunHisEntity {@link TransRunHisEntity}
     */
    public void updateTransRunHis(TransRunHisEntity transRunHisEntity);

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
     * 查询年度任务数、成功数量、失败数量
     *
     * @return 返回任务统计 VO
     */
    public TaskCountVO taskCountYear(String operatorId);
}
