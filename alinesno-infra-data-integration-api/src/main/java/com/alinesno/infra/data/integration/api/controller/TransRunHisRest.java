package com.alinesno.infra.data.integration.api.controller;

import com.alinesno.infra.common.core.constants.SpringInstanceScope;
import com.alinesno.infra.common.facade.pageable.DatatablesPageBean;
import com.alinesno.infra.common.facade.pageable.TableDataInfo;
import com.alinesno.infra.common.web.adapter.plugins.TranslateCode;
import com.alinesno.infra.common.web.adapter.rest.BaseController;
import com.alinesno.infra.data.integration.entity.TransRunHisEntity;
import com.alinesno.infra.data.integration.service.ITransRunHisService;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 【请填写功能名称】Rest
 *
 * @author alinesno ${authorEmail}
 * @date 2021-09-23 11:17:05
 */
@RestController
@Scope(SpringInstanceScope.PROTOTYPE)
@RequestMapping("/api/integration/TransRunHis")
public class TransRunHisRest extends BaseController<TransRunHisEntity, ITransRunHisService> {

    //日志记录
    private static final Logger log = LoggerFactory.getLogger(TransRunHisRest.class);

    @Autowired
    private ITransRunHisService transRunHisService;

//    @DataFilter
    @TranslateCode(plugin = "transRunHisPlugin")
    @ResponseBody
    @PostMapping("/datatables")
    public TableDataInfo datatables(HttpServletRequest request, Model model, DatatablesPageBean page) {
        log.debug("page = {}", ToStringBuilder.reflectionToString(page));
        return this.toPage(model, this.getFeign() , page) ;
    }

    @Override
    public ITransRunHisService getFeign() {
        return this.transRunHisService;
    }
}
