package com.alinesno.infra.data.integration.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.alinesno.infra.common.core.constants.SpringInstanceScope;
import com.alinesno.infra.common.facade.pageable.DatatablesPageBean;
import com.alinesno.infra.common.facade.pageable.TableDataInfo;
import com.alinesno.infra.common.facade.response.AjaxResult;
import com.alinesno.infra.common.web.adapter.plugins.TranslateCode;
import com.alinesno.infra.common.web.adapter.rest.BaseController;
import com.alinesno.infra.data.integration.entity.JobRunHisEntity;
import com.alinesno.infra.data.integration.service.IJobRunHisService;
import com.alinesno.infra.data.integration.vo.ResponseBean;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 【请填写功能名称】Rest
 *
 * @author alinesno ${authorEmail}
 * @date 2021-09-23 11:16:52
 */
@RestController
@Scope(SpringInstanceScope.PROTOTYPE)
@RequestMapping("/api/integration/JobRunHis")
public class JobRunHisRest extends BaseController<JobRunHisEntity, IJobRunHisService> {

    //日志记录
    private static final Logger log = LoggerFactory.getLogger(JobRunHisRest.class);

    @Autowired
    private IJobRunHisService jobRunHisService;

//    @DataFilter
    @TranslateCode(plugin = "jobRunHisPlugin")
    @ResponseBody
    @PostMapping("/datatables")
    public TableDataInfo datatables(HttpServletRequest request, Model model, DatatablesPageBean page) {
        log.debug("page = {}", ToStringBuilder.reflectionToString(page));
        return this.toPage(model, this.getFeign() , page) ;
    }

    @Override
    public IJobRunHisService getFeign() {
        return this.jobRunHisService;
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
//
//        JSONObject resultData = jobRunHisService.taskCount(account.getUserId());
//        return resultData!=null?AjaxResult.success(resultData):AjaxResult.error();

        String operatorId = null ;
        JSONObject resultData = jobRunHisService.taskCount(operatorId);
        return resultData!=null?AjaxResult.success(resultData):AjaxResult.error();

    }

    /**
     * 取近一个月，任务每天的执行统计结果
     *
     * @return {@link ResponseBean}
     */
    @ResponseBody
    @GetMapping("/taskCountChart")
    public AjaxResult taskCountChart(HttpServletRequest request) {
        // 获取当前用户id
//        UserVo account = CurrentAccountJwt.getUserVo().getUser();
//
//        JSONObject resultData = jobRunHisService.taskCountChart(account.getUserId());
//        return resultData!=null?AjaxResult.success(resultData):AjaxResult.error();

        String operatorId = null ;
        JSONObject resultData = jobRunHisService.taskCount(operatorId);
        return resultData!=null?AjaxResult.success(resultData):AjaxResult.error();

    }
}
