package com.alinesno.infra.data.integration.service;

import com.alinesno.infra.common.facade.services.IBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * kettle执行动作的公共接口
 *
 * @author paul
 * @date 2024年3月10日
 */
public interface KettleService<Entity> extends IBaseService<Entity> {

    static final Logger log = LoggerFactory.getLogger(KettleService.class);

    /**
     * 启动所有任务
     */
    default void startAll() {
        log.debug("startAll");
    }

    /**
     * 停止所有任务
     */
    default void stopAll() {
        log.debug("stopAll");
    }

    /**
     * 通过id启动单个任务
     *
     * @param id 任务id
     */
    default void start(String id) {
        log.debug("start by {}", id);
    }

    /**
     * 删除定时任务
     *
     * @param ids 任务id
     */
    default void deleteQuartz(String ids) {
        log.debug("deleteQuartz by {}", ids);
    }

    /**
     * 删除定时任务
     *
     * @param ids 任务id
     */
    default void deleteQuartz(List<String> ids) {
        log.debug("deleteQuartz by {}", ids);
    }

    /**
     * 通过id停止单个任务
     *
     * @param id 任务id
     */
    default void stop(String id) {
        log.debug("stop by {}", id);
    }
}
