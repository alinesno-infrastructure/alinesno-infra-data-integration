package com.alinesno.infra.data.integration.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSONObject;
import com.alinesno.infra.common.core.service.impl.IBaseServiceImpl;
import com.alinesno.infra.data.integration.entity.JobRunHisEntity;
import com.alinesno.infra.data.integration.mapper.JobRunHisMapper;
import com.alinesno.infra.data.integration.service.IJobRunHisService;
import com.alinesno.infra.data.integration.service.ITransRunHisService;
import com.alinesno.infra.data.integration.vo.DashBoardDataItemVo;
import com.alinesno.infra.data.integration.vo.DashBoardDataVo;
import com.alinesno.infra.data.integration.vo.TaskCountVO;
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
public class JobRunHisServiceImpl extends IBaseServiceImpl< JobRunHisEntity, JobRunHisMapper> implements IJobRunHisService {
    //日志记录
    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(JobRunHisServiceImpl.class);

    @Autowired
    private ITransRunHisService transRunHisService;


    @Override
    /**
     * 更新、保存作业执行记录状态
     *
     * @param JobRunHisEntity {@link JobRunHisEntity}
     */
    public void updateJobRunHis(JobRunHisEntity jobRunHisEntity) {
        LambdaQueryWrapper<JobRunHisEntity> queryWrapper = new LambdaQueryWrapper<>();
        JobRunHisEntity monitor = mapper.selectOne(queryWrapper.eq(JobRunHisEntity::getId, jobRunHisEntity.getId()));
        //如果没有监控对象则保存新的监控对象
        if (monitor == null) {
            monitor = new JobRunHisEntity();
        }
        BeanUtil.copyProperties(jobRunHisEntity, monitor);
        this.saveOrUpdate(monitor);
    }

    /**
     * 查询任务数、成功数量、失败数量
     *
     * @return 返回任务统计 VO
     */
    @Override
    public JSONObject taskCount(String operatorId) {

        // 定义数据
        DashBoardDataVo statisticalTotalDataVo = createAndFillStatisticalNameData();
        DashBoardDataVo statisticalMonDataVo = createAndFillStatisticalNameData();
        DashBoardDataVo statisticalDayDataVo = createAndFillStatisticalNameData();

        //获取当天作业任务统计结果
        TaskCountVO jobCountVO = mapper.taskCount(operatorId);
        statisticalTotalDataVo.getJobTotalCount().setCount(jobCountVO.getTotal());
        statisticalTotalDataVo.getJobSuccessCount().setCount(jobCountVO.getSuccess());
        statisticalTotalDataVo.getJobFailCount().setCount(jobCountVO.getFail());

        //获取当天转换任务统计结果
        TaskCountVO taskCountVO = transRunHisService.taskCount(operatorId);
        statisticalTotalDataVo.getTransTotalCount().setCount(taskCountVO.getTotal());
        statisticalTotalDataVo.getTransSuccessCount().setCount(taskCountVO.getSuccess());
        statisticalTotalDataVo.getTransFailCount().setCount(taskCountVO.getFail());

        //设置年度作业和转换汇总数据
        TaskCountVO jobYearCountVO  = mapper.taskCountYear(operatorId);
        TaskCountVO tranYearCountVO  = transRunHisService.taskCountYear(operatorId);
        statisticalDayDataVo.getJobTotalCount().setCount( jobYearCountVO.getTotal() + tranYearCountVO.getTotal() );
        statisticalDayDataVo.getJobSuccessCount().setCount( jobYearCountVO.getSuccess() + tranYearCountVO.getSuccess() );
        statisticalDayDataVo.getJobFailCount().setCount( jobYearCountVO.getFail() + tranYearCountVO.getFail() );

        //获取当月作业任务统计结果
        TaskCountVO jobCountVOMonth = mapper.taskCountMonth(operatorId);
        statisticalMonDataVo.getJobTotalCount().setCount(jobCountVOMonth.getTotal());
        statisticalMonDataVo.getJobSuccessCount().setCount(jobCountVOMonth.getSuccess());
        statisticalMonDataVo.getJobFailCount().setCount(jobCountVOMonth.getFail());

        //获取当月转换任务统计结果
        TaskCountVO taskCountVOMonth = transRunHisService.taskCountMonth(operatorId);
        statisticalMonDataVo.getTransTotalCount().setCount(taskCountVOMonth.getTotal());
        statisticalMonDataVo.getTransSuccessCount().setCount(taskCountVOMonth.getSuccess());
        statisticalMonDataVo.getTransFailCount().setCount(taskCountVOMonth.getFail());

        JSONObject resultData = new JSONObject();
        resultData.put("statisticalTotalDataVo",statisticalTotalDataVo);
        //返回当月数据呈现
        resultData.put("statisticalMonDataVo",statisticalMonDataVo);
        resultData.put("statisticalDayDataVo",statisticalDayDataVo);

        return resultData;

    }

    private DashBoardDataVo createAndFillStatisticalNameData(){
        DashBoardDataVo dashBoardDataVo = new DashBoardDataVo();

        dashBoardDataVo.setJobTotalCount(this.fillStatisticalItemNameData("作业运行数"));
        dashBoardDataVo.setJobSuccessCount(this.fillStatisticalItemNameData("作业运行成功数"));
        dashBoardDataVo.setJobFailCount(this.fillStatisticalItemNameData("作业运行失败数"));
        dashBoardDataVo.setTransTotalCount(this.fillStatisticalItemNameData("转换运行数"));
        dashBoardDataVo.setTransSuccessCount(this.fillStatisticalItemNameData("转换运行成功数"));
        dashBoardDataVo.setTransFailCount(this.fillStatisticalItemNameData("转换运行失败数"));

        return dashBoardDataVo;

    }

    private DashBoardDataItemVo fillStatisticalItemNameData(String name){
        DashBoardDataItemVo dashBoardDataItemVo = new DashBoardDataItemVo();
        dashBoardDataItemVo.setName(name);
        return dashBoardDataItemVo;

    }


    /**
     * 查询近30天内每天的任务数、成功数量、失败数量
     *
     * @return 返回任务统计 VO
     */
    @Override
    public JSONObject taskCountChart(String operatorId){

        int[] totalCounts = new int[30];             //任务数
        int[] successCounts = new int[30];      //执行成功数
        int[] failCounts = new int[30];         //执行失败数

        List<TaskCountVO> taskCountVO = this.mapper.taskCountChart(operatorId);

        int i = 0 ;
        for (TaskCountVO countVO : taskCountVO) {
            totalCounts[i]   = countVO.getTotal().intValue();
            successCounts[i] = countVO.getSuccess().intValue();
            failCounts[i]    = countVO.getFail().intValue();
            i = i + 1 ;
        }

        JSONObject result = new JSONObject();
        result.put("totalData",totalCounts);
        result.put("successData",successCounts);
        result.put("failData",failCounts);

        return result;

    };

}
