package com.alinesno.infra.data.integration.api.controller;

import com.alinesno.infra.common.core.constants.SpringInstanceScope;
import com.alinesno.infra.common.facade.pageable.DatatablesPageBean;
import com.alinesno.infra.common.facade.pageable.TableDataInfo;
import com.alinesno.infra.common.facade.response.AjaxResult;
import com.alinesno.infra.common.facade.response.ResultCodeEnum;
import com.alinesno.infra.common.web.adapter.rest.BaseController;
import com.alinesno.infra.data.integration.entity.JobEntity;
import com.alinesno.infra.data.integration.entity.QuartzEntity;
import com.alinesno.infra.data.integration.entity.TransEntity;
import com.alinesno.infra.data.integration.service.IJobService;
import com.alinesno.infra.data.integration.service.IQuartzService;
import com.alinesno.infra.data.integration.service.ITransService;
import com.alinesno.infra.data.integration.vo.ResponseBean;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
 * 【定时任务】Rest
 *
 * @author paul
 * @date 2024年3月10日
 */
@RestController
@Scope(SpringInstanceScope.PROTOTYPE)
@RequestMapping("/api/integration/Quartz")
public class QuartzRest extends BaseController<QuartzEntity, IQuartzService> {

    //日志记录
    private static final Logger log = LoggerFactory.getLogger(QuartzRest.class);

    @Autowired
    private IQuartzService quartzService;

    @Autowired
    private IJobService jobService ;

    @Autowired
    private ITransService transService ;

//    @DataFilter
    @ResponseBody
//    @RequiresUser
    @PostMapping("/datatables")
    public TableDataInfo datatables(HttpServletRequest request, Model model, DatatablesPageBean page) {
        log.debug("page = {}", ToStringBuilder.reflectionToString(page));
        return this.toPage(model, this.getFeign(), page);
    }

    /**
     * 获取所有定时策略的接口
     *
     * @return {@link AjaxResult}
     */
    @GetMapping({"getList"})
    protected AjaxResult getList() {
        return this.toAjax(getFeign().findAll());
    }

    @Override
    public AjaxResult update(Model model,@RequestBody QuartzEntity entity) {
        getFeign().updateByCron(entity);
        return ok();
    }

    @Override
    public AjaxResult delete(@PathVariable String ids) {
        getFeign().deleteByCron(Arrays.asList(ids.split(",")));
        return ok();
    }

    @Override
    public IQuartzService getFeign() {
        return this.quartzService;
    }

    /**
     * 检查定时策略是否被作业任务使用，如被使用，则返回错误
     * @param ids      定时策略ID清单
     * @return
     */
    @ResponseBody
    @GetMapping("/checkQuartzIfUsed")
    public AjaxResult checkQuartzIfUsed(HttpServletRequest request, String ids)  {
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

        ResponseBean responseBean = checkTransHasUsed(idList, operatorId);
        if ( responseBean.getCode() == 400 ){
            return AjaxResult.error(responseBean.getMessage()) ;
        }


        //检查是否已被作业引用
        QueryWrapper<JobEntity> jobWrapper = new QueryWrapper<>();
        jobWrapper.in("quartz",idList);
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
                if ( i == 3 ) {
                    msg =  msg.deleteCharAt( msg.length() - 1 ) ;
                    msg.append("等");
                    break;
                }
                msg.append(jobEntity.getName()).append(",");
                i = i + 1 ;

            }
            if (   msg.lastIndexOf(",") == msg.length() - 1   ) {
                msg.deleteCharAt( msg.length() - 1 ) ;
            }

            return AjaxResult.error("定时策略已被\""+ msg.deleteCharAt( msg.length() - 1 )+"\"引用,不能删除!") ;
        } else {
            return AjaxResult.success() ;
        }

    }

    /**
     * 检查定时策略是否被转换任务使用，如被使用，则返回错误
     * @param idList      定时策略ID
     * @param operatorId  操作员ID
     * @return
     */
    private ResponseBean checkTransHasUsed(List<String> idList,String operatorId){

        ResponseBean result = new ResponseBean();
        StringBuffer msg = new StringBuffer();

        //检查是否已被转换引用
        QueryWrapper<TransEntity> tranWrapper = new QueryWrapper<>();
        tranWrapper.in("quartz",idList);
        tranWrapper.eq("operator_id", operatorId);
        List<TransEntity> tranlist = transService.list(tranWrapper);
        if ( tranlist != null && tranlist.size() > 0 )	{
            Set<String> tranSet = new HashSet<String>() ;
            List<String> usedList = new ArrayList<String>();
            for (TransEntity transEntity : tranlist) {
                tranSet.add(String.valueOf(transEntity.getId())) ;
            }

            for( String tranID :tranSet ){
                usedList.add(tranID) ;
            }

            List<TransEntity> useTranlist = transService.findByIds( usedList );
            int i = 0 ;
            for (TransEntity tranEntity : useTranlist) {
                //避免提示信息太长，只取前3个任务的名称
                if ( i == 3 ) {
                   msg =  msg.deleteCharAt( msg.length() - 1 ) ;
                   msg.append("等");
                   break;
                }
                msg.append(tranEntity.getName()).append(",");
                i = i + 1 ;
            }

            if (   msg.lastIndexOf(",") == msg.length() - 1   ) {
                msg.deleteCharAt( msg.length() - 1 ) ;
            }

            result.setCode(ResultCodeEnum.FAIL) ;
            result.setMessage("定时策略已被\"" + msg.toString()  + "\"引用,不能删除!") ;

        } else {
            result.setCode(ResultCodeEnum.SUCCESS) ;
        }
        return result ;

    }

}

