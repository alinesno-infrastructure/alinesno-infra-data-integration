package com.alinesno.infra.data.integration.api.controller;

import com.alinesno.infra.common.core.constants.SpringInstanceScope;
import com.alinesno.infra.common.facade.pageable.DatatablesPageBean;
import com.alinesno.infra.common.facade.pageable.TableDataInfo;
import com.alinesno.infra.common.web.adapter.plugins.TranslateCode;
import com.alinesno.infra.common.web.adapter.rest.BaseController;
import com.alinesno.infra.data.integration.entity.TransMonitorEntity;
import com.alinesno.infra.data.integration.service.ITransMonitorService;
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
 * 【转换监控任务】Rest
 *
 * @author paul
 * @date 2024年3月10日
 */
@RestController
@Scope(SpringInstanceScope.PROTOTYPE)
@RequestMapping("/api/integration/TransMonitor")
public class TransMonitorRest extends BaseController<TransMonitorEntity, ITransMonitorService> {

    //日志记录
    private static final Logger log = LoggerFactory.getLogger(TransMonitorRest.class);

    @Autowired
    private ITransMonitorService transMonitorService;

//    @DataFilter
    @TranslateCode(plugin = "transMonitorPlugin")
    @ResponseBody
    @PostMapping("/datatables")
    public TableDataInfo datatables(HttpServletRequest request, Model model, DatatablesPageBean page) {
        log.debug("page = {}", ToStringBuilder.reflectionToString(page));
        return toPage(model, getFeign(), page);
    }

    /**
     * 对作业任务执行结果统计
     *
     * @return {@link ResponseBean}
     */
    @ResponseBody
    @GetMapping("/taskCount")
    public ResponseBean taskCount(HttpServletRequest request) {
//        UserVo account = CurrentAccountJwt.getUserVo().getUser();
//        return ResponseGenerator.ok(transMonitorService.taskCount(account.getUserId()));
        ResponseBean result = new ResponseBean();
        String operatorId = null ;

        TaskCountVO taskCountVO = transMonitorService.taskCount(operatorId);
        result.setData(taskCountVO);
        return result ;

    }

    @Override
    public ITransMonitorService getFeign() {
        return this.transMonitorService;
    }
}
