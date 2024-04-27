package com.alinesno.infra.data.integration.service.impl;

import com.alinesno.infra.common.core.service.impl.IBaseServiceImpl;
import com.alinesno.infra.data.integration.dto.QuartzDTO;
import com.alinesno.infra.data.integration.entity.BaseKettleEntity;
import com.alinesno.infra.data.integration.entity.JobEntity;
import com.alinesno.infra.data.integration.entity.QuartzEntity;
import com.alinesno.infra.data.integration.entity.TransEntity;
import com.alinesno.infra.data.integration.mapper.JobMapper;
import com.alinesno.infra.data.integration.mapper.QuartzMapper;
import com.alinesno.infra.data.integration.mapper.TransMapper;
import com.alinesno.infra.data.integration.service.IQuartzService;
import com.alinesno.infra.data.integration.service.QuartsJobService;
import com.alinesno.infra.data.integration.utils.QuartsUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author paul
 * @date 2024年3月10日
 */
@Service
public class QuartzServiceImpl extends IBaseServiceImpl<QuartzEntity, QuartzMapper> implements IQuartzService {
    //日志记录
    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(QuartzServiceImpl.class);

    @Autowired
    private QuartsJobService quartsJobService;

    @Autowired
    private JobMapper jobMapper;

    @Autowired
    private TransMapper transMapper;

    @Override
    public void updateByCron(QuartzEntity quartzEntity) {
        super.update(quartzEntity);

        //获取任务并更新定时策略
        LambdaQueryWrapper<JobEntity> jobWrapper = new LambdaQueryWrapper<>();
        jobMapper.selectList(jobWrapper.eq(BaseKettleEntity::getQuartz, quartzEntity.getId())).forEach(entity -> {
            // 获取定时任务需要的参数
            QuartzDTO quartzDTO = QuartsUtils.getQuartzDTO(entity, quartzEntity.getQuartzCron());
            // 根据定时策略添加任务
            quartsJobService.updateCronJob(quartzDTO);
        });

        //获取转换并更新定时策略
        LambdaQueryWrapper<TransEntity> transWrapper = new LambdaQueryWrapper<>();
        transMapper.selectList(transWrapper.eq(BaseKettleEntity::getQuartz, quartzEntity.getId())).forEach(entity -> {
            // 获取定时任务需要的参数
            QuartzDTO quartzDTO = QuartsUtils.getQuartzDTO(entity, quartzEntity.getQuartzCron());
            // 根据定时策略添加任务
            quartsJobService.updateCronJob(quartzDTO);
        });

    }

    @Override
    public void deleteByCron(List<String> ids) {

        List<QuartzEntity> quartzEntities = mapper.selectBatchIds(ids);

        quartzEntities.forEach(quartzEntity -> {
            //获取任务并更新定时策略
            LambdaQueryWrapper<JobEntity> jobWrapper = new LambdaQueryWrapper<>();
            jobMapper.selectList(jobWrapper.eq(BaseKettleEntity::getQuartz, quartzEntity.getId())).forEach(entity -> {
                // 获取定时任务需要的参数
                QuartzDTO quartzDTO = QuartsUtils.getQuartzDTO(entity, quartzEntity.getQuartzCron());
                // 根据定时策略添加任务
                quartsJobService.removeJob(quartzDTO);
            });

            //获取转换并更新定时策略
            LambdaQueryWrapper<TransEntity> transWrapper = new LambdaQueryWrapper<>();
            transMapper.selectList(transWrapper.eq(BaseKettleEntity::getQuartz, quartzEntity.getId())).forEach(entity -> {
                // 获取定时任务需要的参数
                QuartzDTO quartzDTO = QuartsUtils.getQuartzDTO(entity, quartzEntity.getQuartzCron());
                // 根据定时策略添加任务
                quartsJobService.removeJob(quartzDTO);
            });
        });

        mapper.deleteBatchIds(ids);
    }
}
