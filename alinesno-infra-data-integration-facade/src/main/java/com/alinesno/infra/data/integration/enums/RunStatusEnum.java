package com.alinesno.infra.data.integration.enums;

import com.alinesno.infra.data.integration.enums.base.BaseEnum;
import java.util.Arrays;
import java.util.Objects;

/**
 * 运行状态枚举类
 *
 * @author paul
 * @date 2024年3月10日
 */


public enum RunStatusEnum implements BaseEnum<Integer> {
	/**
	 * 运行
	 */
	RUN(1, "运行"),
	/**
	 * 停止
	 */
	STOP(2, "停止");

	private Integer code;
	private String desc;

	public static RunStatusEnum getEnum(Integer code) {
		return Arrays.stream(values()).filter(b -> Objects.equals(b.code, code)).findFirst().orElse(null);
	}

	public static String getEnumDesc(Integer code) {
		RunStatusEnum e = getEnum(code);
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

	private RunStatusEnum(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

}
