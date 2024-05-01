package com.alinesno.infra.data.integration.api.controller;

import com.alinesno.infra.common.core.constants.SpringInstanceScope;
import com.alinesno.infra.common.facade.pageable.DatatablesPageBean;
import com.alinesno.infra.common.facade.pageable.TableDataInfo;
import com.alinesno.infra.common.facade.response.AjaxResult;
import com.alinesno.infra.common.facade.wrapper.RpcWrapper;
import com.alinesno.infra.common.web.adapter.plugins.TranslateCode;
import com.alinesno.infra.common.web.adapter.rest.BaseController;
import com.alinesno.infra.data.integration.entity.JobEntity;
import com.alinesno.infra.data.integration.entity.TransEntity;
import com.alinesno.infra.data.integration.entity.TransMonitorEntity;
import com.alinesno.infra.data.integration.entity.TransRunHisEntity;
import com.alinesno.infra.data.integration.enums.RunStatusEnum;
import com.alinesno.infra.data.integration.service.IJobService;
import com.alinesno.infra.data.integration.service.ITransMonitorService;
import com.alinesno.infra.data.integration.service.ITransRunHisService;
import com.alinesno.infra.data.integration.service.ITransService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 【转换任务】Rest
 *
 * @author paul
 * @date 2024年3月10日
 */
@RestController
@Scope(SpringInstanceScope.PROTOTYPE)
@RequestMapping("/api/integration/Trans")
public class TransRest extends BaseController<TransEntity, ITransService> {

    //日志记录
    private static final Logger log = LoggerFactory.getLogger(TransRest.class);

    @Autowired
    private ITransService transService;

    @Autowired
    private ITransRunHisService transRunHisService;

    @Autowired
    private ITransMonitorService transMonitorService;

    @Autowired
    private IJobService jobService ;

//    @DataFilter
    @TranslateCode(plugin = "transPlugin")
    @ResponseBody
    @PostMapping("/datatables")
    public TableDataInfo datatables(HttpServletRequest request, Model model, DatatablesPageBean page) {
        log.debug("page = {}", ToStringBuilder.reflectionToString(page));
        return toPage(model, getFeign(), page);
    }

    @ResponseBody
    @GetMapping("/getList")
    public AjaxResult getList() {
        return toAjax(this.getFeign().findAll());
    }

    /**
     * 单个启动定时任务
     *
     * @param id 根据id查询
     * @return {@link AjaxResult}
     */
    @ResponseBody
    @PostMapping("/startQuartzTrans/{id}")
    public AjaxResult startQuartzTrans(@PathVariable String id) {
        //todo 测试先运行单次
        getFeign().start(id);
        return this.ok();
    }

    @Override
    public AjaxResult save(Model model, @RequestBody TransEntity entity) throws Exception {
        entity.setStatus(RunStatusEnum.STOP.getCode());
        return super.save(model, entity);
    }

//    @Override
//    public AjaxResult update(Model model, @RequestBody TransEntity entity) {
//        getFeign().updateByCron(entity);
//        return ok();
//    }

    @Override
    public AjaxResult delete(@PathVariable String ids) {
        getFeign().deleteByCron(Arrays.asList(ids.split(",")));

        //删除转换前，取出转换ID
        List<String> translist=  Arrays.asList(ids.split(",")) ;
        for (String trans:translist) {
            RpcWrapper<TransMonitorEntity> transMonitorWrapper =new RpcWrapper<>();
            transMonitorWrapper.eq("monitorTransid",trans);
            transMonitorService.deleteByWrapper(transMonitorWrapper);

            RpcWrapper<TransRunHisEntity> transRunHisWrapper =new RpcWrapper<>();
            transRunHisWrapper.eq("monitorTransId",trans);
            transRunHisService.deleteByWrapper(transRunHisWrapper);

        }

        return ok();
    }

    @Override
    public ITransService getFeign() {
        return this.transService;
    }

    /**
     * 停止单个转换任务的定时策略
     *
     * @param id 根据id查询
     * @return {@link AjaxResult}
     */
    @ResponseBody
    @PostMapping("/stopQuartzTrans/{id}")
    public AjaxResult stopQuartzTrans(@PathVariable String id) {
        //todo 测试先运行单次
        getFeign().stop(id);
        UpdateWrapper<TransMonitorEntity> transMonitorUpdateWrapper = new UpdateWrapper<>();
        transMonitorUpdateWrapper.eq("monitor_transid",id)
                                 .set("monitor_status",2);
        transMonitorService.update(null,transMonitorUpdateWrapper);
        return this.ok();
    }

    /**
     * 检查转换任务是否被作业任务使用，如被使用，则返回错误
     * @param ids      转换任务ID清单
     * @return
     */
    @ResponseBody
    @GetMapping("/checTransIfUsed")
    public AjaxResult checTransIfUsed(HttpServletRequest request, String ids)  {
        if ( ids == null ) {
            return AjaxResult.error("请求删除的id为空!");
        }

        List<String> idList = Arrays.asList(ids.split(","));

        StringBuffer msg = new StringBuffer();

        String operatorId = null ;
        // 设置用户
//        ManagerAccountEntity account = CurrentAccountJwt.get() ;
//        if ( account != null ) {
//            operatorId = account.getId();
//        }


        for (String id : idList) {
            //检查是否已被作业引用
            QueryWrapper<JobEntity> jobWrapper = new QueryWrapper<>();
            jobWrapper.like("trans_ids",id);
            jobWrapper.eq("operator_id", operatorId);
            List<JobEntity> joblist = jobService.list(jobWrapper);
            if ( joblist != null && joblist.size() > 0 )	{
                Set<String> jobSet = new HashSet<String>() ;
                List<String> usedList = new ArrayList<String>();
                for (JobEntity jobEntity : joblist) {
                    jobSet.add(String.valueOf(jobEntity.getId())) ;
                }

                for( String jobID :jobSet ){
                    usedList.add(jobID) ;
                }

                List<JobEntity> useJoblist = jobService.findByIds( usedList );
                int i = 0 ;
                for (JobEntity jobEntity : useJoblist) {
                    //避免提示信息太长，只取前3个任务的名称
                    if ( i == 3 ) {
                        msg =  msg.deleteCharAt( msg.length() - 1 ) ;
                        msg.append("等");
                        break;
                    }
                    msg.append(jobEntity.getName()).append(",");
                    i = i + 1 ;

                }
            }

        }

        if ( msg.length() > 0 ) {
            if (   msg.lastIndexOf(",") == msg.length() - 1    ) {
                msg.deleteCharAt( msg.length() - 1 ) ;
            }

            return AjaxResult.error("转换任务已被\"" + msg.toString() + "\"引用,不能删除!") ;
        } else{
            return AjaxResult.success() ;

        }

    }
}
