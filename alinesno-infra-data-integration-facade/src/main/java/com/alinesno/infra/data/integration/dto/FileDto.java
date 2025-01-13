package com.alinesno.infra.data.integration.dto;


import com.alinesno.infra.data.integration.entity.BuildGitEntity;
import lombok.Data;

/**
 * 文件类型dto
 *
 * @author paul
 * @version 1.0.0
 */
@Data
public class FileDto {

    /**
     * 存储组件的id
     */
    private String storageId;


    /**
     * gitlab仓库ID
     */
    private String gitId;


    /**
     * 脚本文件在gitlab中的路径，如local/ods_classify_export.ktr
     */
    private String filePath;


    /**
     * 文件名称,如ods_classify_export.ktr，用于保存到本地时命名
     */
    private String fileName;

    private BuildGitEntity buildGit ;

}
