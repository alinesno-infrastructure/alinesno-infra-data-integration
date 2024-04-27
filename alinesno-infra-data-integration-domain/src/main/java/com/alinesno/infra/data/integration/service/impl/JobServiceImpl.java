package com.alinesno.infra.data.integration.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alinesno.infra.data.integration.entity.JobEntity;
import com.alinesno.infra.data.integration.entity.JobMonitorEntity;
import com.alinesno.infra.data.integration.enums.RunStatusEnum;
import com.alinesno.infra.data.integration.mapper.JobMapper;
import com.alinesno.infra.data.integration.mapper.JobMonitorMapper;
import com.alinesno.infra.data.integration.quartz.JobQuartz;
import com.alinesno.infra.data.integration.service.IJobService;
import com.alinesno.infra.data.integration.service.QuartsJobService;
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
public class JobServiceImpl extends BaseKettleServiceImpl<JobEntity, JobMapper> implements IJobService {
    //日志记录
    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(JobServiceImpl.class);

    @Autowired
    private JobMonitorMapper jobMonitorMapper;

    @Autowired
    private QuartsJobService quartsJobService;

    /**
     * 通过id启动单个定时任务
     *
     * @param id 任务id
     */
    @Override
    public void start(String id) {
        //调用父类方法执行任务
        super.start(id);
        updatJobMonitorStatus(id,RunStatusEnum.getEnum(1));
    }

    @Override
    public void updateByCron(JobEntity entity) {
        super.update(entity);
        super.start(entity.getId().toString());
    }

    @Override
    public void deleteByCron(List<String> ids) {
        this.deleteQuartz(ids);
    }

    public void updatJobMonitorStatus(String id, RunStatusEnum statusEnum) {
        //根据任务id获取转换监控
        LambdaQueryWrapper<JobMonitorEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(JobMonitorEntity::getMonitorJobId, id);
        JobMonitorEntity jobMonitorEntity = jobMonitorMapper.selectOne(queryWrapper);
        if (jobMonitorEntity == null) {
            jobMonitorEntity = new JobMonitorEntity();
            jobMonitorEntity.setMonitorFail(0);
            jobMonitorEntity.setMonitorSuccess(0);
            jobMonitorEntity.setMonitorJobId(id);
            //jobMonitorEntity.setRunStatus(System.currentTimeMillis() + "-"); RunStatus 只记录最后执行时间
            jobMonitorEntity.setRunStatus(String.valueOf(System.currentTimeMillis()));
        } else {
            switch (statusEnum) {
                case RUN:
                    String runStatus = jobMonitorEntity.getRunStatus();
                    if (runStatus.endsWith("-")) {
                        //runStatus = runStatus.concat(String.valueOf(System.currentTimeMillis()));
                        runStatus = String.valueOf(System.currentTimeMillis()); //RunStatus 只记录最后执行时间
                    }
                    runStatus = String.valueOf(System.currentTimeMillis()); //RunStatus 只记录最后执行时间
                    jobMonitorEntity.setRunStatus(runStatus);               //RunStatus 只记录最后执行时间
                    break;
                case STOP:
                    jobMonitorEntity.setRunStatus(String.valueOf(System.currentTimeMillis()));
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + statusEnum);
            }
        }
        jobMonitorEntity.setMonitorStatus(statusEnum.getCode());

        //保存或更新实体类
        if ( jobMonitorEntity.getId() != null &&  StrUtil.isNotBlank(jobMonitorEntity.getId().toString())) {
            jobMonitorMapper.updateById(jobMonitorEntity);
        } else {
            jobMonitorMapper.insert(jobMonitorEntity);
        }

    }

    @Override
    public JobQuartz getJob() {
        return new JobQuartz();
    }
}
