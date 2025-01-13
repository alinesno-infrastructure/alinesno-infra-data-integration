package com.alinesno.infra.data.integration.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import com.alinesno.infra.data.integration.entity.TransEntity;
import com.alinesno.infra.data.integration.entity.TransMonitorEntity;
import com.alinesno.infra.data.integration.enums.RunStatusEnum;
import com.alinesno.infra.data.integration.mapper.TransMapper;
import com.alinesno.infra.data.integration.mapper.TransMonitorMapper;
import com.alinesno.infra.data.integration.quartz.TransQuartz;
import com.alinesno.infra.data.integration.service.ITransService;
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
 * @version 1.0.0
 */
@Service
public class TransServiceImpl extends BaseKettleServiceImpl<TransEntity, TransMapper> implements ITransService {
    //日志记录
    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(TransServiceImpl.class);

    @Autowired
    private TransMonitorMapper transMonitorMapper;

    @Override
    public void start(String id) {
        super.start(id);
        updateTransMonitorStatus(id,RunStatusEnum.getEnum(1));
    }

    /**
     * 修改监控信息状态
     *
     * @param id         作业ID
     * @param statusEnum 状态枚举
     */
    private void updateTransMonitorStatus(String id, RunStatusEnum statusEnum) {
        //根据任务id获取转换监控
        LambdaQueryWrapper<TransMonitorEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TransMonitorEntity::getMonitorTransid, id);
        TransMonitorEntity transMonitorEntity = transMonitorMapper.selectOne(queryWrapper);
        if (transMonitorEntity == null) {
            transMonitorEntity = new TransMonitorEntity();
            transMonitorEntity.setMonitorFail(0);
            transMonitorEntity.setMonitorSuccess(0);
            transMonitorEntity.setMonitorTransid(id);
            //transMonitorEntity.setRunStatus(System.currentTimeMillis() + "-"); RunStatus 只记录最后执行时间
            transMonitorEntity.setRunStatus(String.valueOf(System.currentTimeMillis()));
        } else {
            switch (statusEnum) {
                case RUN:
                    String runStatus = transMonitorEntity.getRunStatus();
                    if (runStatus.endsWith("-")) {
                        //runStatus = runStatus.concat(String.valueOf(System.currentTimeMillis()));
                        runStatus = String.valueOf(System.currentTimeMillis()); //RunStatus 只记录最后执行时间
                    }
                    //transMonitorEntity.setRunStatus(runStatus.concat(",").concat(System.currentTimeMillis() + "-"));
                    runStatus = String.valueOf(System.currentTimeMillis()); //RunStatus 只记录最后执行时间
                    transMonitorEntity.setRunStatus(runStatus);              //RunStatus 只记录最后执行时间
                    break;
                case STOP:
                    //transMonitorEntity.setRunStatus(transMonitorEntity.getRunStatus().concat(String.valueOf(System.currentTimeMillis())));
                    transMonitorEntity.setRunStatus(String.valueOf(System.currentTimeMillis())); //RunStatus 只记录最后执行时间
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + statusEnum);
            }
        }
        transMonitorEntity.setMonitorStatus(statusEnum.getCode());

        //保存或更新实体类
        if ( transMonitorEntity.getId() != null && StrUtil.isNotBlank(transMonitorEntity.getId().toString())) {
            transMonitorMapper.updateById(transMonitorEntity);
        } else {
            transMonitorMapper.insert(transMonitorEntity);
        }
    }

    @Override
    public TransQuartz getJob() {
        return new TransQuartz();
    }

    @Override
    public void updateByCron(TransEntity entity) {
        entity.setUpdateTime(new DateTime());
        super.update(entity);
        super.start( entity.getId().toString() );
    }

    @Override
    public void deleteByCron(List<String> ids) {
        this.deleteQuartz(ids);
    }
}
