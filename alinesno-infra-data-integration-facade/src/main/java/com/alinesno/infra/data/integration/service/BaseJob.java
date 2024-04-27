package com.alinesno.infra.data.integration.service;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 基础job方法
 *
 * @author paul
 * @date 2024年3月10日
 */
public interface BaseJob extends Job {

    @Override
    void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException;
}
