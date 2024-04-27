package com.alinesno.infra.data.integration.vo;

/**
 * @author paul
 * @date 2024年3月10日
 */
public class DashBoardDataItemVo {

    /**
     * 条目名称
     */
    private String name;

    /**
     * 条目值(数量)
     */
    private Long count = 0L;

    /**
     * 变化百分比
     */
    private String percent;

    /**
     * 变化方向
     */
    private boolean isUp = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public boolean isUp() {
        return isUp;
    }

    public void setUp(boolean up) {
        isUp = up;
    }
}
