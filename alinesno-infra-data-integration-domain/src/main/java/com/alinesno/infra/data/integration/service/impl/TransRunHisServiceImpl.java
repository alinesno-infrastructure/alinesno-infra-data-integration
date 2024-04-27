package com.alinesno.infra.data.integration.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alinesno.infra.common.core.service.impl.IBaseServiceImpl;
import com.alinesno.infra.data.integration.entity.TransRunHisEntity;
import com.alinesno.infra.data.integration.mapper.TransRunHisMapper;
import com.alinesno.infra.data.integration.service.ITransRunHisService;
import com.alinesno.infra.data.integration.vo.TaskCountVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author paul
 * @date 2024年3月10日
 */
@Service
public class TransRunHisServiceImpl extends IBaseServiceImpl< TransRunHisEntity, TransRunHisMapper> implements ITransRunHisService {
    //日志记录
    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(TransRunHisServiceImpl.class);

    @Override
    /**
     * 更新、保存作业执行记录状态
     *
     * @param JobRunHisEntity {@link JobRunHisEntity}
     */
    public void updateTransRunHis(TransRunHisEntity transRunHisEntity) {
        LambdaQueryWrapper<TransRunHisEntity> queryWrapper = new LambdaQueryWrapper<>();
        TransRunHisEntity monitor = mapper.selectOne(queryWrapper.eq(TransRunHisEntity::getId, transRunHisEntity.getId()));
        //如果没有监控对象则保存新的监控对象
        if (monitor == null) {
            monitor = new TransRunHisEntity();
        }
        BeanUtil.copyProperties(transRunHisEntity, monitor);
        this.saveOrUpdate(monitor);
    }

    /**
     * 查询当天任务数、成功数量、失败数量
     *
     * @return 返回任务统计 VO
     */
    @Override
    public TaskCountVO taskCount(String operatorId){
       return this.mapper.taskCount(operatorId) ;

    };

    /**
     * 查询当月任务数、成功数量、失败数量
     *
     * @return 返回任务统计 VO
     */

    @Override
    public TaskCountVO taskCountMonth(String operatorId){

        return this.mapper.taskCountMonth(operatorId) ;

    };

    @Override
    public TaskCountVO taskCountYear(String operatorId){
        return this.mapper.taskCountYear(operatorId) ;
    };


}
