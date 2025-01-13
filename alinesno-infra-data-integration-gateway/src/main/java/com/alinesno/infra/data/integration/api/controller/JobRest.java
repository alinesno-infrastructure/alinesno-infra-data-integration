package com.alinesno.infra.data.integration.api.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import com.alinesno.infra.common.core.constants.SpringInstanceScope;
import com.alinesno.infra.common.facade.pageable.DatatablesPageBean;
import com.alinesno.infra.common.facade.pageable.TableDataInfo;
import com.alinesno.infra.common.facade.response.AjaxResult;
import com.alinesno.infra.common.facade.wrapper.RpcWrapper;
import com.alinesno.infra.common.web.adapter.plugins.TranslateCode;
import com.alinesno.infra.common.web.adapter.rest.BaseController;
import com.alinesno.infra.data.integration.dto.JobDto;
import com.alinesno.infra.data.integration.entity.*;
import com.alinesno.infra.data.integration.enums.RunStatusEnum;
import com.alinesno.infra.data.integration.service.*;
import com.alinesno.infra.data.integration.vo.JobVo;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 【作业任务】Rest
 *
 * @author paul
 * @version 1.0.0
 */
@RestController
@Scope(SpringInstanceScope.PROTOTYPE)
@RequestMapping("/api/integration/Job")
public class JobRest extends BaseController<JobEntity, IJobService> {

    //日志记录
    private static final Logger log = LoggerFactory.getLogger(JobRest.class);

    @Autowired
    private IJobService jobService;

    @Autowired
    private IJobRunHisService jobRunHisService;

    @Autowired
    private IJobMonitorService jobMonitorService;

    @Autowired
    private ITransRunHisService transRunHisService;

    @Autowired
    private ITransMonitorService transMonitorService;

//    @DataFilter
    @TranslateCode(plugin = "jobPlugin")
    @ResponseBody
    @PostMapping("/datatables")
    public TableDataInfo datatables(HttpServletRequest request, Model model, DatatablesPageBean page) {
        log.debug("page = {}", ToStringBuilder.reflectionToString(page));
        return toPage(model, getFeign(), page);
    }

    /**
     * 单个启动定时任务
     *
     * @param id 根据id查询
     * @return {@link AjaxResult}
     */
    @ResponseBody
    @PostMapping("/startQuartzJob/{id}")
    public AjaxResult startQuartzTrans(@PathVariable String id) {
        getFeign().start(id);
        return this.ok();
    }

    /**
     * 重写保存方法
     *
     * @param model {@link Model}
     * @param dto   {@link JobDto}
     * @return {@link AjaxResult}
     * @throws Exception
     */
    @PostMapping("/newSave")
    public AjaxResult save(Model model, @RequestBody JobDto dto) throws Exception {
        JobEntity entity = new JobEntity();
        //dto转entity
        BeanUtils.copyProperties(dto, entity);
        //设置任务状态
        entity.setStatus(RunStatusEnum.STOP.getCode());
        //将转换List放入任务
        if ( dto.getTransIds() != null ) {
            //todo 后期把任务关联的转换保存到中间表
            entity.setTransIds(String.join(",", dto.getTransIds()));
        }

        return super.save(model, entity);
    }

    /**
     * 重写详情
     *
     * @param id
     * @return {@link AjaxResult}
     */
    @Override
    public AjaxResult detail(@PathVariable String id) {
        //获取实体类
        JobEntity entity = this.getFeign().findEntityById(id);
        JobVo jobVo = new JobVo();
        //entity转vo
        BeanUtils.copyProperties(entity, jobVo);
        jobVo.setId( entity.getId() );
        if ( StrUtil.isNotBlank(entity.getTransIds()) ) {
            //todo 后期把任务关联的转换保存到中间表
            jobVo.setTransIds(Arrays.stream(entity.getTransIds().split(",")).collect(Collectors.toList()));
        }
        return this.toAjax(jobVo);
    }

    /**
     * @param model {@link Model}
     * @param dto   {@link JobDto}
     * @return {@link AjaxResult}
     */
    @PutMapping("/newUpdate")
    public AjaxResult update(Model model, @RequestBody JobDto dto) {
        JobEntity entity = new JobEntity();
        //dto转entity
        BeanUtils.copyProperties(dto, entity);

        //将转换List放入任务
        if ( dto.getTransIds() != null ) {
            //todo 后期把任务关联的转换保存到中间表
            entity.setTransIds(String.join(",", dto.getTransIds()));
        }

        entity.setUpdateTime(new DateTime());
        getFeign().updateByCron(entity);
        return ok();
    }

    @Override
    public AjaxResult delete(@PathVariable String ids) {

        //删除任务前，取出任务清单
        List<JobEntity> joblist= jobService.findByIds(Arrays.asList(ids.split(",")));

        getFeign().deleteByCron(Arrays.asList(ids.split(",")));

        //停止作业及停止作业任务后，删除作业下转换的日志清单及转换的监控清单
        for (JobEntity job:joblist) {

            //停止作业及停止作业任务后，删除作业的日志清单及作业的监控清单
            RpcWrapper<JobMonitorEntity> jobMonitorWrapper =new RpcWrapper<>();
            jobMonitorWrapper.eq("monitorJobId",job.getId());
            jobMonitorService.deleteByWrapper(jobMonitorWrapper);

            RpcWrapper<JobRunHisEntity> jobRunHisWrapper =new RpcWrapper<>();
            jobRunHisWrapper.eq("runJobId",job.getId());
            jobRunHisService.deleteByWrapper(jobRunHisWrapper);

            if ( job.getTransIds() != null ){
                List<String> translist=  Arrays.asList(job.getTransIds().split(",")) ;
                for (String trans:translist) {

                    RpcWrapper<TransMonitorEntity> transMonitorWrapper =new RpcWrapper<>();
                    transMonitorWrapper.eq("monitorTransid",trans);
                    transMonitorService.deleteByWrapper(transMonitorWrapper);

                    RpcWrapper<TransRunHisEntity> transRunHisWrapper =new RpcWrapper<>();
                    transRunHisWrapper.eq("monitorTransId",trans);
                    transRunHisService.deleteByWrapper(transRunHisWrapper);
                }

            }

        }

        return ok();
    }

    /**
     * 获取成功任务数据接口
     *
     * @return
     */
    @ResponseBody
    @PostMapping("/getSuccessJob")
    public TableDataInfo getSuccessJob(HttpServletRequest request, Model model, DatatablesPageBean page) {
        log.debug("page = {}", ToStringBuilder.reflectionToString(page));
        return this.toPage(model, this.getFeign(), page);
    }

    @Override
    public IJobService getFeign() {
        return this.jobService;
    }

    /**
     * 停止单个作业任务的定时策略
     *
     * @param id 根据id查询
     * @return {@link AjaxResult}
     */
    @ResponseBody
    @PostMapping("/stopQuartzJob/{id}")
    public AjaxResult stopQuartzJob(@PathVariable String id) {
        getFeign().stop(id);
        UpdateWrapper<JobMonitorEntity> jobMonitorUpdateWrapper = new UpdateWrapper<>();
        jobMonitorUpdateWrapper.eq("monitor_job_id",id)
                .set("monitor_status",2);
        jobMonitorService.update(null,jobMonitorUpdateWrapper);
        return this.ok();
    }
}
