package com.alinesno.infra.data.integration.constant;

import com.alinesno.infra.data.integration.utils.FileUtil;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.nio.charset.Charset;

/**
 * 配置在yml文件中的常量数据
 *
 * @author paul
 * @date 2024年3月10日
 */
@Component
@ConfigurationProperties(prefix = "kettle")
public class KettleConfig {

    /**
     * 日志文件输出路径
     */
    public static String logFilePath;

    /**
     * kettle编码设置
     */
    public static Charset encoding;

    /**
     * ktr或kjb文件保存路径，单个文件执行的时候需要保存ktr、kjb文件
     */
    public static String uploadPath;

    /**
     * kettle所在路径，初始化会自动生成.kettle文件在该目录,kettle.properties,repositories.xml,shared.xml都在里面
     */
    public static String kettleHome;

    /**
     * kettle插件包所在路径 eg: D:\Development\kettle\8.3\data-integration\plugins
     */
    public static String kettlePluginPackages;

    public static String kettlePluginBaseFolder;

    public void setLogFilePath(String logFilePath) {
        KettleConfig.logFilePath = FileUtil.replaceSeparator(logFilePath);
        ;
    }

    public void setEncoding(Charset encoding) {
        KettleConfig.encoding = encoding;
    }

    public void setUploadPath(String uploadPath) {
        KettleConfig.uploadPath = uploadPath;
    }

    public void setKettleHome(String kettleHome) {
        KettleConfig.kettleHome = kettleHome;
    }

    public void setKettlePluginPackages(String kettlePluginPackages) {
        KettleConfig.kettlePluginPackages = kettlePluginPackages;
    }

    public void setKettlePluginBaseFolder(String kettlePluginBaseFolder) {
        KettleConfig.kettlePluginBaseFolder = kettlePluginBaseFolder;
    }

}
