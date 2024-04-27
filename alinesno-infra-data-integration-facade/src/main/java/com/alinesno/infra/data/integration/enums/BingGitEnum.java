package com.alinesno.infra.data.integration.enums;

/**
 * 是否绑定
 * @author paul
 * @date 2024年3月10日
 */
public enum BingGitEnum {

	HAS_BING(1) ,
	NOT_BING(0) ;

	private int v ;

	private BingGitEnum(int v) {
		this.v = v ;
	}

	public int getV() {
		return v;
	}

}
