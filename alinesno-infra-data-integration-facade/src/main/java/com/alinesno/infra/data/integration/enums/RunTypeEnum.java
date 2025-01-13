package com.alinesno.infra.data.integration.enums;

import com.alinesno.infra.data.integration.enums.base.BaseEnum;

import java.util.Arrays;
import java.util.Objects;

/**
 * 脚本运行类型枚举类
 *
 * @author paul
 * @version 1.0.0
 */


public enum RunTypeEnum implements BaseEnum<String> {
    /**
     * 资源库方式运行
     */
    REP("rep", "资源库方式运行"),
    /**
     * 文件方式运行
     */
    FILE("file", "文件方式运行"),

    /**
     * FTP方式运行
     */
    FTP("ftp", "FTP方式运行");

    private String code;
    private String desc;

    public static RunTypeEnum getEnum(String code) {
        return Arrays.stream(values()).filter(b -> Objects.equals(b.code, code)).findFirst().orElse(null);
    }

    public static String getEnumDesc(String code) {
        RunTypeEnum e = getEnum(code);
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

	private RunTypeEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
    
    
}
