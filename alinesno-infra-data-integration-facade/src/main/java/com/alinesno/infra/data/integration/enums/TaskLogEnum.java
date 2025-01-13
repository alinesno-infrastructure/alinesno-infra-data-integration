package com.alinesno.infra.data.integration.enums;

import com.alinesno.infra.data.integration.enums.base.BaseEnum;

import java.util.Arrays;
import java.util.Objects;


public enum TaskLogEnum implements BaseEnum<String> {
    /**
     * 资源库方式运行
     */
    //REP("IMPL", "数据库还原"),
    /**
     * 文件方式运行
     */
    //FILE("file", "文件方式运行"),

    /**
     * FTP方式运行
     */
    IMPL_DB("IMPL_DB", "数据库导入文件");

    private String code;
    private String desc;

    public static TaskLogEnum getEnum(String code) {
        return Arrays.stream(values()).filter(b -> Objects.equals(b.code, code)).findFirst().orElse(null);
    }

    public static String getEnumDesc(String code) {
        TaskLogEnum e = getEnum(code);
        return e != null ? e.desc : null;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    private TaskLogEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }


}
