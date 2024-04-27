package com.alinesno.infra.data.integration.quartz;

import cn.hutool.core.date.DateTime;
import com.alinesno.infra.data.integration.entity.BuildGitEntity;
import com.alinesno.infra.data.integration.entity.TransEntity;
import com.alinesno.infra.data.integration.entity.TransMonitorEntity;
import com.alinesno.infra.data.integration.entity.TransRunHisEntity;
import com.alinesno.infra.data.integration.enums.RunTypeEnum;
import com.alinesno.infra.data.integration.execute.TransExecute;
import com.alinesno.infra.data.integration.service.*;
import com.alinesno.infra.data.integration.vo.ResponseBean;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.logging.LogLevel;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 转换定时任务执行器
 *
 * @author paul
 * @date 2024年3月10日
 */
@Component
public class TransQuartz implements BaseJob {

    private static final Logger log = LoggerFactory.getLogger(TransQuartz.class);

    @Autowired
    private ITransService transService;

    @Autowired
    private ITransMonitorService transMonitorService;

    @Autowired
    private ITransRunHisService transRunHisService;

    @Autowired
    private IBuildGitService buildGitService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        //执行开始时间
        DateTime transStartTime = new DateTime();

        // 本次执行时间
        Date lastExecuteTime = jobExecutionContext.getFireTime();
        // 下一次任务时间
        Date nexExecuteTime = jobExecutionContext.getNextFireTime();
        // 运行状态
        boolean runStatus = true;
        // 获取传入过来的转换ID
        String transId = String.valueOf(jobExecutionContext.getMergedJobDataMap().getLong("id"));
        // 获取转换
        TransEntity transEntity = transService.findOne(transId);

        // 修改监控表数据
        TransMonitorEntity transMonitor = new TransMonitorEntity();
        transMonitor.setMonitorTransid(transId);
        transMonitor.setRunStatus(String.valueOf(System.currentTimeMillis())); //最近完成时间
        transMonitor.setLastExecuteTime(lastExecuteTime);
        transMonitor.setNextExecuteTime(nexExecuteTime);
        transMonitor.setOperatorId(transEntity.getOperatorId());
        transMonitor.setTenantId(transEntity.getTenantId());
        transMonitor.setApplicationId(transEntity.getApplicationId());
        transMonitor.setApplicationName(transEntity.getApplicationName());


        //记录作业执行记录
        TransRunHisEntity transRunHisEntity = new TransRunHisEntity();
        transRunHisEntity.setMonitorTransId(transId);
        transRunHisEntity.setStartTime(transStartTime);
        transRunHisEntity.setOperatorId(transEntity.getOperatorId());
        transRunHisEntity.setTenantId(transEntity.getTenantId());
        transRunHisEntity.setApplicationId(transEntity.getApplicationId());
        transRunHisEntity.setApplicationName(transEntity.getApplicationName());


        //设置任务和转换路径
        ResponseBean responseBean = setPath(transEntity);
        if ( responseBean.getCode() == 400 ) {
            transRunHisEntity.setRemark(responseBean.getMessage() );

        }

        // 设置执行参数
        Map<String, String> params = new HashMap<>(2);
        //todo 策略待做
        // 执行转换并返回日志
        String logText = "";
        try {
            // 判断是执行资源库还是执行文件脚本
            if (RunTypeEnum.getEnum(transEntity.getType()) == RunTypeEnum.FILE) {
                logText = TransExecute.run(transEntity.getPath(), params
                        , LogLevel.getLogLevelForCode(transEntity.getLogLevel()));
            } else {
                throw new IllegalStateException("Unexpected value: " + RunTypeEnum.getEnum(transEntity.getType()));
            }
        } catch (KettleException e) {
            runStatus = false;
            String msg = "执行转换失败";
            log.error(msg, e);
            logText = msg + e;
        }
        //执行结束时间
        DateTime transEndTime = new DateTime();

        transMonitor.setMonitorStatus(transEntity.getStatus());
        transMonitorService.updateMonitor(transMonitor, runStatus);

        transRunHisEntity.setRunStatus(runStatus ? 1 : 0);
        transRunHisEntity.setEndTime(transEndTime);
        transRunHisEntity.setRemark(transRunHisEntity.getRemark() + System.lineSeparator() +  logText);
        transRunHisService.updateTransRunHis(transRunHisEntity);

    }

    /**
     * 设置任务路径
     *
     * @param transEntity {@link TransEntity}
     */
    private ResponseBean setPath(TransEntity transEntity) {
        //下载转换到本地并获取文件路径
        //String fullPath = StorageUtils.initFile(transEntity.getFileId(), transEntity.getName());

        BuildGitEntity buildGit = buildGitService.getById(transEntity.getGitId()) ;

        //从gitlab中下载转换到本地并获取文件路径
        ResponseBean responseBean = buildGitService.downloadFile(buildGit, transEntity.getRelativeLocation(), transEntity.getName());
        if ( responseBean.getCode() == 200 ) {
            //保存文件路径
            transEntity.setPath(responseBean.getMessage());
        }
        return responseBean ;
    }


}
