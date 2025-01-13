package com.alinesno.infra.data.integration.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alinesno.infra.common.core.service.impl.IBaseServiceImpl;
import com.alinesno.infra.data.integration.entity.TransMonitorEntity;
import com.alinesno.infra.data.integration.mapper.TransMonitorMapper;
import com.alinesno.infra.data.integration.service.ITransMonitorService;
import com.alinesno.infra.data.integration.vo.TaskCountVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author paul
 * @version 1.0.0
 */
@Service
public class TransMonitorServiceImpl extends IBaseServiceImpl<TransMonitorEntity, TransMonitorMapper> implements ITransMonitorService {
    //日志记录
    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(TransMonitorServiceImpl.class);

    /**
     * 查询任务数、成功数量、失败数量
     *
     * @return 返回任务统计 VO
     */
    @Override
    public TaskCountVO taskCount(String operatorId) {
        return mapper.taskCount(operatorId);
    }

    /**
     * 时间段内的次数统计
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return 返回列表
     */
    @Override
    public List<Map<String, Object>> getEcharts(String operatorId,Date start, Date end) {
        return mapper.getEcharts(operatorId,start, end);
    }

    /**
     * 更新监控状态
     *
     * @param transMonitor {@link TransMonitorEntity}
     * @param success      是否运行成功
     */
    @Override
    public void updateMonitor(TransMonitorEntity transMonitor, boolean success) {
        LambdaQueryWrapper<TransMonitorEntity> queryWrapper = new LambdaQueryWrapper<>();
        TransMonitorEntity monitor = mapper.selectOne(queryWrapper.eq(TransMonitorEntity::getMonitorTransid, transMonitor.getMonitorTransid()));
        //如果没有监控对象则保存新的监控对象
        if (monitor == null) {
            monitor = new TransMonitorEntity();
            BeanUtil.copyProperties(transMonitor, monitor);
        }
        monitor.setMonitorStatus(transMonitor.getMonitorStatus());
        monitor.setRunStatus(transMonitor.getRunStatus());
        monitor.setLastExecuteTime(transMonitor.getLastExecuteTime());
        monitor.setNextExecuteTime(transMonitor.getNextExecuteTime());
        monitor.setOperatorId(transMonitor.getOperatorId());

        if (success) {
            monitor.setMonitorSuccess(monitor.getMonitorSuccess() + 1);
        } else {
            monitor.setMonitorFail(monitor.getMonitorFail() + 1);
        }

        this.saveOrUpdate(monitor);
    }

}
