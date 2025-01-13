package com.alinesno.infra.data.integration.service;

import com.alinesno.infra.data.integration.dto.QuartzDTO;
import org.quartz.*;

/**
 * 定时任务接口
 *
 * @author paul
 * @version 1.0.0
 */
public interface QuartsJobService {

    /**
     * 启动调度任务
     *
     * @param jobDetail 执行任务
     * @param trigger   触发器
     */
    public void startScheduler(JobDetail jobDetail, Trigger trigger);

    /**
     * 任务执行实例
     *
     * @param jobName      任务名称
     * @param jobGroupName 任务分组名
     * @param jobDataMap   执行参数
     * @param jobClass     执行类-实现了{@code org.quartz.Job}接口的类
     * @return {@link JobDetail}
     */
    public JobDetail getJobDetail(String jobName, String jobGroupName, JobDataMap jobDataMap, Class<? extends Job> jobClass);

    /**
     * cron触发器
     *
     * @param triggerName      触发器名
     * @param triggerGroupName 触发器分组名
     * @param cron             定时策略
     * @return {@link CronTrigger}
     */
    public CronTrigger getCronTrigger(String triggerName, String triggerGroupName, String cron);

    /**
     * 一次任务触发器
     *
     * @param triggerName      触发器名
     * @param triggerGroupName 触发器分组名
     * @return {@link SimpleTrigger}
     */
    public SimpleTrigger getOnceTrigger(String triggerName, String triggerGroupName);

    /**
     * 添加定时任务
     *
     * @param dto {@link QuartzDTO}
     */
    public void addJob(QuartzDTO dto);

    /**
     * 添加定时任务
     *
     * @param dto {@link QuartzDTO}
     */
    public void addCronJob(QuartzDTO dto);

    /**
     * 添加只执行一次的定时任务
     *
     * @param dto {@link QuartzDTO}
     */
    public void addOnceJob(QuartzDTO dto);

    /**
     * 修改定时任务
     *
     * @param triggerName      触发器名称
     * @param triggerGroupName 触发器分组
     * @param cron             cron表达式
     */
    public void updateCronJob(String triggerName, String triggerGroupName, String cron);

    /**
     * 修改定时任务
     *
     * @param dto {@link QuartzDTO}
     */
    public void updateCronJob(QuartzDTO dto);

    /**
     * 移除定时任务
     *
     * @param dto {@link QuartzDTO}
     */
    public void removeJob(QuartzDTO dto);

    /**
     * 获取定时任务状态
     *
     * @param triggerName      触发器名称
     * @param triggerGroupName 触发器分组
     * @return
     */
    public String getTriggerState(String triggerName, String triggerGroupName);
}
