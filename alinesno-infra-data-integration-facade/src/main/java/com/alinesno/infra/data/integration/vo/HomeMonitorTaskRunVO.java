package com.alinesno.infra.data.integration.vo;

/**
 * 页面监控条形图的VO
 *
 * @author paul
 * @date 2024年3月10日
 */
public class HomeMonitorTaskRunVO {

    /**
     * 运行时间
     */
    private String runTime;
    /**
     * 作业执行量
     */
    private Integer jobRunNum;
    /**
     * 转换执行量
     */
    private Integer transRunNum;

    public String getRunTime() {
        return runTime;
    }

    public void setRunTime(String runTime) {
        this.runTime = runTime;
    }

    public Integer getJobRunNum() {
        return jobRunNum;
    }

    public void setJobRunNum(Integer jobRunNum) {
        this.jobRunNum = jobRunNum;
    }

    public Integer getTransRunNum() {
        return transRunNum;
    }

    public void setTransRunNum(Integer transRunNum) {
        this.transRunNum = transRunNum;
    }

    @Override
    public String toString() {
        return "HomeMonitorTaskRunVO{" +
                "runTime='" + runTime + '\'' +
                ", jobRunNum=" + jobRunNum +
                ", transRunNum=" + transRunNum +
                '}';
    }
}
