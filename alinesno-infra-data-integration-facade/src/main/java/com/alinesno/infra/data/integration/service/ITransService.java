package com.alinesno.infra.data.integration.service;


import com.alinesno.infra.data.integration.entity.TransEntity;

import java.util.List;

/**
 * 【请填写功能名称】Service接口
 *
 * @author paul
 * @date 2024年3月10日
 */
public interface ITransService extends KettleService<TransEntity> {

    /**
     * 更新任务接口时更新定时任务
     *
     * @param entity {@link TransEntity}
     */
    public void updateByCron(TransEntity entity);

    /**
     * 删除任务接口时删除定时任务
     *
     * @param ids 一个或多个id
     */
    public void deleteByCron(List<String> ids);

}
