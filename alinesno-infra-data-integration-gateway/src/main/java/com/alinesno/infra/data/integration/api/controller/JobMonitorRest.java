package com.alinesno.infra.data.integration.api.controller;

import com.alinesno.infra.common.core.constants.SpringInstanceScope;
import com.alinesno.infra.common.web.adapter.rest.BaseController;
import com.alinesno.infra.common.facade.pageable.DatatablesPageBean;
import com.alinesno.infra.common.facade.pageable.TableDataInfo;
import com.alinesno.infra.common.facade.response.AjaxResult;
import com.alinesno.infra.common.web.adapter.plugins.TranslateCode;
import com.alinesno.infra.data.integration.entity.JobMonitorEntity;
import com.alinesno.infra.data.integration.service.IJobMonitorService;
import com.alinesno.infra.data.integration.vo.ResponseBean;
import com.alinesno.infra.data.integration.vo.TaskCountVO;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 【作业任务监控】Rest
 *
 * @author paul
 * @date 2024年3月10日
 */
@RestController
@Scope(SpringInstanceScope.PROTOTYPE)
@RequestMapping("/api/integration/JobMonitor")
public class JobMonitorRest extends BaseController<JobMonitorEntity, IJobMonitorService> {

    //日志记录
    private static final Logger log = LoggerFactory.getLogger(JobMonitorRest.class);

    @Autowired
    private IJobMonitorService jobMonitorService;

//    @DataFilter
    @TranslateCode(plugin = "jobMonitorPlugin")
    @ResponseBody
    @PostMapping("/datatables")
    public TableDataInfo datatables(HttpServletRequest request, Model model, DatatablesPageBean page) {
        log.debug("page = {}", ToStringBuilder.reflectionToString(page));
        return toPage(model, getFeign(), page);
    }

    /**
     * 获取属性类型
     */
    @GetMapping("getJobType")
    public AjaxResult getJobType() {
        return toAjax(jobMonitorService.findAll());
    }

    /**
     * 对作业任务执行结果统计
     *
     * @return {@link ResponseBean}
     */
    @ResponseBody
    @GetMapping("/taskCount")
    public AjaxResult taskCount(HttpServletRequest request) {
        // 获取当前用户id
//        UserVo account = CurrentAccountJwt.getUserVo().getUser();
//        return this.toAjax(jobMonitorService.taskCount(account.getUserId()));

        String operatorId = null ;
        TaskCountVO  taskCountVO = jobMonitorService.taskCount(operatorId);
        return taskCountVO!=null?AjaxResult.success(taskCountVO):AjaxResult.error();

    }

    @Override
    public IJobMonitorService getFeign() {
        return this.jobMonitorService;
    }
}
