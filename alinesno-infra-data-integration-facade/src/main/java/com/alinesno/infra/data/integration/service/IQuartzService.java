package com.alinesno.infra.data.integration.service;


import com.alinesno.infra.common.facade.services.IBaseService;
import com.alinesno.infra.data.integration.entity.QuartzEntity;

import java.util.List;

/**
 * 【请填写功能名称】Service接口
 *
 * @author paul
 * @date 2024年3月10日
 */
public interface IQuartzService extends IBaseService<QuartzEntity> {

    /**
     * 更新定时任务的时候更新对应任务的调度
     *
     * @param quartzEntity {@link QuartzEntity}
     */
    public void updateByCron(QuartzEntity quartzEntity);

    /**
     * 删除定时任务的时候删除对应定时调度
     *
     * @param ids 一条或多条定时任务
     */
    public void deleteByCron(List<String> ids);
}
