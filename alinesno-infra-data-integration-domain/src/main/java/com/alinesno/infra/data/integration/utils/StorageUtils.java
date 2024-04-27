package com.alinesno.infra.data.integration.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.http.HttpUtil;
import com.alinesno.infra.data.integration.dto.FileDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * 存储操作工具类
 *
 * @author paul
 * @date 2024年3月10日
 */
@Component
public class StorageUtils {

    //日志记录
    private static final Logger log = LoggerFactory.getLogger(StorageUtils.class);

    private static String kettlePath;
    private static String storageUrl;

    /**
     * 获取文件信息写入本地并返回文件路径
     *
     * @param id       任务id
     * @param fileName 文件名称
     * @return 返回文件路径
     */
    public static String initFile(String id, String fileName) {
        //检查本地路径
        checkDirPath();
        return writeFile(id, fileName);
    }

    /**
     * 获取多个文件并写入本地
     *
     * @param files 多个文件名称和id
     */
    public static void initFile(List<FileDto> files) {
        //检查本地路径
        checkDirPath();
        files.forEach(file -> writeFile(file.getStorageId(), file.getFileName()));
    }

    /**
     * 从存储组件获取文件并写入本地
     *
     * @param id       存储组件中的id
     * @param fileName 文件名称
     * @return
     */
    private static String writeFile(String id, String fileName) {
        String result = HttpUtil.get(storageUrl + id);

        //获取文件路径
        String filePath = kettlePath + "/" + fileName;
        log.info(filePath);
        //写入文件
        FileWriter fileWriter = new FileWriter(filePath);
        fileWriter.write(result);

        return filePath;
    }

    /**
     * 检查本地路径有没有文件，没有则创建
     */
    private static void checkDirPath() {
        //如果没有文件夹则创建
        if (FileUtil.isDirectory(kettlePath)) {
            FileUtil.mkdir(kettlePath);
        }
    }

    @Value("${kettle.upload-path}")
    public void setKettlePath(String kettlePath) {
        StorageUtils.kettlePath = kettlePath;
    }

    @Value("${alinesno.storage-url}")
    public void setStorageUrl(String storageUrl) {
        StorageUtils.storageUrl = storageUrl;
    }
}
