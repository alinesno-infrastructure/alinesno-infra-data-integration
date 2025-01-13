package com.alinesno.infra.data.integration.enums;

import com.alinesno.infra.data.integration.enums.base.BaseEnum;

import java.util.Arrays;
import java.util.Objects;

/**
 * 运行结果枚举类
 *
 * @author paul
 * @version 1.0.0
 */


public enum RunResultEnum implements BaseEnum<Integer> {
    /**
     * 运行成功
     */
    SUCCESS(1, "成功"),
    /**
     * 运行失败
     */
    FAIL(0, "失败");

    private Integer code;
    private String desc;

    public static RunResultEnum getEnum(Integer code) {
        return Arrays.stream(values()).filter(b -> Objects.equals(b.code, code)).findFirst().orElse(null);
    }

    public static String getEnumDesc(Integer code) {
        RunResultEnum e = getEnum(code);
        return e != null ? e.desc : null;
    }

	@Override
    public Integer getCode() {
		return code;
	}

	@Override
    public String getDesc() {
		return desc;
	}

	private RunResultEnum(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}
    
    
}
