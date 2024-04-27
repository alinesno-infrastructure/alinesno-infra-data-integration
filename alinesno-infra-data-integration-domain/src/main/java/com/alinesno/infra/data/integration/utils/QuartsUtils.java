package com.alinesno.infra.data.integration.utils;


import com.alinesno.infra.common.facade.mapper.entity.BaseEntity;
import com.alinesno.infra.data.integration.dto.QuartzDTO;
import com.alinesno.infra.data.integration.service.BaseJob;
import com.google.common.collect.ImmutableMap;
import org.quartz.JobDataMap;

/**
 * 定时任务工具类
 *
 * @author paul
 * @date 2024年3月10日
 */
public class QuartsUtils {

    /**
     * 根据定时策略和作业组装执行参数
     *
     * @param entity 作业信息
     * @param cron   定时策略
     * @param aClass 定时任务类
     * @return {@link QuartzDTO}
     */
    public static QuartzDTO getQuartzDTO(BaseEntity entity, String cron, Class<? extends BaseJob> aClass) {
        QuartzDTO dto = getQuartzDTO(entity, cron);
        dto.setJobClass(aClass);
        return dto;
    }

    /**
     * 根据定时策略和作业组装执行参数
     *
     * @param entity 作业信息
     * @param cron   定时策略
     * @return {@link QuartzDTO}
     */
    public static QuartzDTO getQuartzDTO(BaseEntity entity, String cron) {
        //通过
        QuartzDTO dto = new QuartzDTO();
        dto.setJobName("JOB@" + entity.getId());
        dto.setJobGroupName("JOB_GROUP@" + entity.getId());
        dto.setTriggerName("JOB_TRIGGER@" + entity.getId());
        dto.setTriggerGroupName("JOB_TRIGGER_GROUP@" + entity.getId());
        if (StringUtil.hasText(cron)) {
            dto.setCron(cron);
        }
        dto.setJobDataMap(new JobDataMap(ImmutableMap.of("id", entity.getId())));
        return dto;
    }
}
