package com.alinesno.infra.data.integration.vo;


/**
 * @author paul
 * @date 2024年3月10日
 */
public class DashBoardDataVo {

    /**
     * 总作业数
     */
    private DashBoardDataItemVo jobTotalCount;

    /**
     * 作业成功数
     */
    private DashBoardDataItemVo jobSuccessCount;

    /**
     * 作业失败数
     */
    private DashBoardDataItemVo jobFailCount;

    /**
     * 总转换数
     */
    private DashBoardDataItemVo TransTotalCount;

    /**
     * 转换成功数
     */
    private DashBoardDataItemVo TransSuccessCount;

    /**
     * 转换失败数
     */
    private DashBoardDataItemVo TransFailCount;

    public DashBoardDataItemVo getJobTotalCount() {
        return jobTotalCount;
    }

    public void setJobTotalCount(DashBoardDataItemVo jobTotalCount) {
        this.jobTotalCount = jobTotalCount;
    }

    public DashBoardDataItemVo getJobSuccessCount() {
        return jobSuccessCount;
    }

    public void setJobSuccessCount(DashBoardDataItemVo jobSuccessCount) {
        this.jobSuccessCount = jobSuccessCount;
    }

    public DashBoardDataItemVo getJobFailCount() {
        return jobFailCount;
    }

    public void setJobFailCount(DashBoardDataItemVo jobFailCount) {
        this.jobFailCount = jobFailCount;
    }

    public DashBoardDataItemVo getTransTotalCount() {
        return TransTotalCount;
    }

    public void setTransTotalCount(DashBoardDataItemVo transTotalCount) {
        TransTotalCount = transTotalCount;
    }

    public DashBoardDataItemVo getTransSuccessCount() {
        return TransSuccessCount;
    }

    public void setTransSuccessCount(DashBoardDataItemVo transSuccessCount) {
        TransSuccessCount = transSuccessCount;
    }

    public DashBoardDataItemVo getTransFailCount() {
        return TransFailCount;
    }

    public void setTransFailCount(DashBoardDataItemVo transFailCount) {
        TransFailCount = transFailCount;
    }
}
