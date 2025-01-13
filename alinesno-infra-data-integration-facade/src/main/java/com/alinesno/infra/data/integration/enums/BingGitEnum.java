package com.alinesno.infra.data.integration.enums;

/**
 * 是否绑定
 * @author paul
 * @version 1.0.0
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
