package com.alinesno.infra.data.integration.service.impl;

import com.alinesno.infra.common.core.service.impl.IBaseServiceImpl;
import com.alinesno.infra.common.facade.mapper.repository.IBaseMapper;
import com.alinesno.infra.data.integration.entity.BaseKettleEntity;
import com.alinesno.infra.data.integration.entity.QuartzEntity;
import com.alinesno.infra.data.integration.enums.RunStatusEnum;
import com.alinesno.infra.data.integration.mapper.QuartzMapper;
import com.alinesno.infra.data.integration.service.BaseJob;
import com.alinesno.infra.data.integration.service.KettleService;
import com.alinesno.infra.data.integration.service.QuartsJobService;
import com.alinesno.infra.data.integration.utils.QuartsUtils;
import com.alinesno.infra.data.integration.dto.QuartzDTO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Arrays;
import java.util.List;

/**
 * kettle操作的基础实现类
 *
 * @author paul
 * @date 2024年3月10日
 */
public class BaseKettleServiceImpl<Entity extends BaseKettleEntity, M extends IBaseMapper<Entity>> extends IBaseServiceImpl<Entity, M> implements KettleService<Entity> {

    @Autowired
    private QuartsJobService quartsJobService;

    @Autowired
    private QuartzMapper quartzMapper;

    @Override
    public void startAll() {
        LambdaQueryWrapper<Entity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Entity::getStatus, RunStatusEnum.STOP.getCode());
        List<Entity> jobsList = mapper.selectList(wrapper);
        jobsList.forEach(job -> start(String.valueOf(job.getId())));
    }

    //停止所有的任务
    @Override
    public void stopAll() {
        log.debug("stopAll");
        LambdaQueryWrapper<Entity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Entity::getStatus, RunStatusEnum.RUN.getCode());
        List<Entity> jobsList = mapper.selectList(wrapper);
        jobsList.forEach(job -> stop(String.valueOf(job.getId())));

    }

    @Override
    public void start(String id) {
        //获取转换任务
        Entity entity = this.findOne(id);

        //获取转换任务的定时任务
        QuartzEntity quartzEntity = quartzMapper.selectById(entity.getQuartz());

        //修改转换状态并更新实体类
        entity.setStatus(RunStatusEnum.RUN.getCode());
        mapper.updateById(entity);

        // 获取定时任务需要的参数
        QuartzDTO quartzDTO = QuartsUtils.getQuartzDTO(entity, quartzEntity.getQuartzCron(), getJob().getClass());
        // 根据定时策略添加任务
        quartsJobService.addJob(quartzDTO);
    }

    //删除第二步
    @Override
    public void deleteQuartz(String ids) {
        String[] split = ids.split(",");
        this.deleteQuartz(Arrays.asList(split));
    }

    //删除第三步
    @Override
    public void deleteQuartz(List<String> ids) {
        findByIds(ids).forEach(jobEntity -> {
            QuartzDTO quartzDTO = QuartsUtils.getQuartzDTO(jobEntity, null, getJob().getClass());
            quartsJobService.removeJob(quartzDTO);
        });
        mapper.deleteBatchIds(ids);
    }

    //实现停止作业、转换任务功能
    @Override
    public void stop(String id) {
        log.debug("stop by {}", id);

        //获取转换任务
        Entity entity = this.findOne(id);

        //获取转换任务的定时任务
        QuartzEntity quartzEntity = quartzMapper.selectById(entity.getQuartz());

        //修改转换状态并更新实体类
        entity.setStatus(RunStatusEnum.STOP.getCode());
        mapper.updateById(entity);

        // 获取定时任务需要的参数
        QuartzDTO quartzDTO = QuartsUtils.getQuartzDTO(entity, quartzEntity.getQuartzCron(), getJob().getClass());
        // 根据定时策略移除任务
        quartsJobService.removeJob(quartzDTO);
    }

//    /**
//     * 重写父类删除方法 第一步
//     *
//     * @param ids
//     */
//    @Override
//    public void deleteByIds(@NotNull String[] ids) {
//        this.deleteQuartz(String.join(",", ids));
//    }

    public BaseJob getJob() {
        return null;
    }

    /**
     * 获取定时器数据库操作
     *
     * @return {@link QuartzMapper}
     */
    public QuartzMapper getQuartzMapper() {
        return this.quartzMapper;
    }

    /**
     * 获取定时任务接口
     *
     * @return {@link QuartsJobService}
     */
    public QuartsJobService getQuartsJobService() {
        return this.quartsJobService;
    }
}
