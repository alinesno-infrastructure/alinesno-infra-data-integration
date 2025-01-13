package com.alinesno.infra.data.integration.vo;

import java.util.List;

/**
 * 任务的vo
 *
 * @author paul
 * @version 1.0.0
 */
public class JobVo {
    private Long id;
    private String categoryId;
    /**
     * 转换文件id
     */
    private String fileId;
    /**
     * 关联的转换任务id
     */
    private List<String> transIds;
    private String name;
    private String description;
    private String type;
    private String path;
    private String quartz;
    private String syncStrategy;
    private String logLevel;
    private Integer status;
    private String gitId;
    private String relativeLocation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public List<String> getTransIds() {
        return transIds;
    }

    public void setTransIds(List<String> transIds) {
        this.transIds = transIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getQuartz() {
        return quartz;
    }

    public void setQuartz(String quartz) {
        this.quartz = quartz;
    }

    public String getSyncStrategy() {
        return syncStrategy;
    }

    public void setSyncStrategy(String syncStrategy) {
        this.syncStrategy = syncStrategy;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getGitId() {
        return gitId;
    }

    public void setGitId(String gitId) {
        this.gitId = gitId;
    }

    public String getRelativeLocation() {
        return relativeLocation;
    }

    public void setRelativeLocation(String relativeLocation) {
        this.relativeLocation = relativeLocation;
    }


}
