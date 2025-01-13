package com.alinesno.infra.data.integration.dto;

import org.quartz.Job;
import org.quartz.JobDataMap;

/**
 * 定时任务需要的参数
 *
 * @author paul
 * @version 1.0.0
 */

public class QuartzDTO {
	/**
	 * 任务名-自定义
	 */
	private String jobName;

	/**
	 * 任务所属组名-自定义
	 */
	private String jobGroupName;

	/**
	 * 触发器名-自定义
	 */
	private String triggerName;

	/**
	 * 触发器所属组名-自定义
	 */
	private String triggerGroupName;

	/**
	 * cron定时策略
	 */
	private String cron;

	/**
	 * 任务执行参数-可以传入任意类型的数据给执行类
	 */
	private JobDataMap jobDataMap;

	/**
	 * 任务执行类-实现了{@code org.quartz.Job}接口的类
	 */
	Class<? extends Job> jobClass;

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobGroupName() {
		return jobGroupName;
	}

	public void setJobGroupName(String jobGroupName) {
		this.jobGroupName = jobGroupName;
	}

	public String getTriggerName() {
		return triggerName;
	}

	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}

	public String getTriggerGroupName() {
		return triggerGroupName;
	}

	public void setTriggerGroupName(String triggerGroupName) {
		this.triggerGroupName = triggerGroupName;
	}

	public String getCron() {
		return cron;
	}

	public void setCron(String cron) {
		this.cron = cron;
	}

	public JobDataMap getJobDataMap() {
		return jobDataMap;
	}

	public void setJobDataMap(JobDataMap jobDataMap) {
		this.jobDataMap = jobDataMap;
	}

	public Class<? extends Job> getJobClass() {
		return jobClass;
	}

	public void setJobClass(Class<? extends Job> jobClass) {
		this.jobClass = jobClass;
	}

}
