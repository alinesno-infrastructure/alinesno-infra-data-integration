package com.alinesno.infra.data.integration.vo;

import java.io.Serializable;

/**
 * 任务统计的VO
 *
 * @author paul
 * @version 1.0.0
 */
public class TaskCountVO implements Serializable {

    /**
     * 日期
     */

    private String curDay;

    /**
     * 总任务数
     */
    private Long total = 0l;

    /**
     * 成功任务数
     */
    private Long success = 0l;

    /**
     * 失败任务数
     */
    private Long fail = 0l;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getSuccess() {
        return success;
    }

    public void setSuccess(Long success) {
        this.success = success;
    }

    public Long getFail() {
        return fail;
    }

    public void setFail(Long fail) {
        this.fail = fail;
    }

    public String getCurDay() {
        return curDay;
    }

    public void setCurDay(String curDay) {
        this.curDay = curDay;
    }
}
