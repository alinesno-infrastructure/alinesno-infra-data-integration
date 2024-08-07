package com.alinesno.infra.data.integration.quartz;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import com.alinesno.infra.data.integration.dto.FileDto;
import com.alinesno.infra.data.integration.entity.BuildGitEntity;
import com.alinesno.infra.data.integration.entity.JobEntity;
import com.alinesno.infra.data.integration.entity.JobMonitorEntity;
import com.alinesno.infra.data.integration.entity.JobRunHisEntity;
import com.alinesno.infra.data.integration.enums.RunTypeEnum;
import com.alinesno.infra.data.integration.execute.JobExecute;
import com.alinesno.infra.data.integration.service.*;
import com.alinesno.infra.data.integration.vo.ResponseBean;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.logging.LogLevel;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.stream.Collectors;
import com.alinesno.infra.common.facade.mapper.entity.BaseEntity;

/**
 * 转换定时任务执行器
 *
 * @author paul
 * @date 2024年3月10日
 */
@Component
public class JobQuartz implements BaseJob {

    private static final Logger log = LoggerFactory.getLogger(JobQuartz.class);

    @Autowired
    private IJobService jobService;

    @Autowired
    private ITransService transService;

    @Autowired
    private IJobMonitorService jobMonitorService;

    @Autowired
    private IJobRunHisService jobRunHisService;

    @Autowired
    private IBuildGitService buildGitService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        //执行开始时间
        DateTime jobStartTime = new DateTime();
        // 本次执行时间
        Date lastExecuteTime = jobExecutionContext.getFireTime();
        // 下一次任务时间
        Date nexExecuteTime = jobExecutionContext.getNextFireTime();
        // 运行状态
        boolean runStatus = true;
        // 获取传入过来的转换ID
        String jobId = String.valueOf(jobExecutionContext.getMergedJobDataMap().getLong("id"));
        // 获取转换
        JobEntity jobEntity = jobService.findOne(jobId);

        // 修改监控表数据
        JobMonitorEntity jobMonitorEntity = new JobMonitorEntity();
        jobMonitorEntity.setMonitorJobId(jobId);
        //最近完成时间
        jobMonitorEntity.setRunStatus(String.valueOf(System.currentTimeMillis()));
        jobMonitorEntity.setLastExecuteTime(lastExecuteTime);
        jobMonitorEntity.setNextExecuteTime(nexExecuteTime);
        jobMonitorEntity.setOperatorId(jobEntity.getOperatorId());
        jobMonitorEntity.setOrgId(jobEntity.getOrgId());
        jobMonitorEntity.setApplicationId(jobEntity.getApplicationId());
        jobMonitorEntity.setApplicationName(jobEntity.getApplicationName());
        jobMonitorEntity.setMonitorStatus(jobEntity.getStatus());
        jobMonitorService.updateMonitor(jobMonitorEntity, runStatus);


        //记录作业执行记录
        JobRunHisEntity jobRunHisEntity = new JobRunHisEntity();
        jobRunHisEntity.setRunJobId(jobId);
        jobRunHisEntity.setStartTime(jobStartTime);
        jobRunHisEntity.setOperatorId(jobEntity.getOperatorId());
        jobRunHisEntity.setOrgId(jobEntity.getOrgId());
        jobRunHisEntity.setApplicationId(jobEntity.getApplicationId());

        //设置任务和转换路径
        ResponseBean responseBean = setPath(jobEntity);
        if ( responseBean.getCode() == 400 ) {
            jobRunHisEntity.setRemark(responseBean.getMessage() );

        }

        // 设置执行参数
        Map<String, String> params = new HashMap<>(2);
        //todo 策略待做
        // 执行转换并返回日志
        String logText = "";
        try {
            // 判断是执行资源库还是执行文件脚本
            if (RunTypeEnum.getEnum(jobEntity.getType()) == RunTypeEnum.FILE) {
                logText = JobExecute.run(jobEntity.getPath(), params
                        , LogLevel.getLogLevelForCode(jobEntity.getLogLevel()));
            } else {
                throw new IllegalStateException("Unexpected value: " + RunTypeEnum.getEnum(jobEntity.getType()));
            }
        } catch (KettleException e) {
            runStatus = false;
            String msg = "执行作业失败!";
            log.error(msg, e);
            logText = msg + e;
        }

        //执行结束时间
        DateTime jobEndTime = new DateTime();
        jobRunHisEntity.setEndTime(jobEndTime);
        jobRunHisEntity.setRunStatus(runStatus ? 1 : 0);
        jobRunHisEntity.setRemark(jobRunHisEntity.getRemark() + System.lineSeparator() +  logText);
        jobRunHisEntity.setApplicationName(jobEntity.getApplicationName());
        jobRunHisService.updateJobRunHis(jobRunHisEntity);



    }

    /**
     * 设置任务路径
     *
     * @param jobEntity {@link JobEntity}
     */
    private ResponseBean setPath(JobEntity jobEntity) {
        //进行 “任务” 所属 “转换” 的判断
        if (StrUtil.isNotBlank(jobEntity.getTransIds())) {
            //根据转换id获取到转换实体类并放入dto
            List<FileDto> fileDtoList = transService.lambdaQuery().in(BaseEntity::getId, Arrays.asList(jobEntity.getTransIds().split(","))).list().stream().map(transEntity -> {
                BuildGitEntity buildGit = buildGitService.getById(transEntity.getGitId()) ;
                FileDto fileDto = new FileDto();
                fileDto.setStorageId(transEntity.getFileId());
                fileDto.setGitId(transEntity.getGitId());
                fileDto.setFilePath(transEntity.getRelativeLocation());
                fileDto.setFileName(transEntity.getName());
                fileDto.setBuildGit(buildGit);
                return fileDto;
            }).collect(Collectors.toList());
            //下载 “任务” 所属 “转换” 的文件到本地
            //StorageUtils.initFile(fileDtoList);
            buildGitService.initFile(fileDtoList);
        }
        //下载转换到本地并获取文件路径
        //String fullPath = StorageUtils.initFile(jobEntity.getFileId(), jobEntity.getName());

        BuildGitEntity buildGit = buildGitService.getById(jobEntity.getGitId()) ;

        //从gitlab中下载转换到本地并获取文件路径
        ResponseBean responseBean = buildGitService.downloadFile(buildGit, jobEntity.getRelativeLocation(), jobEntity.getName());
        if ( responseBean.getCode() == 200 ) {
            //保存文件路径
            jobEntity.setPath(responseBean.getMessage());
        }
        return responseBean ;

    }


}
