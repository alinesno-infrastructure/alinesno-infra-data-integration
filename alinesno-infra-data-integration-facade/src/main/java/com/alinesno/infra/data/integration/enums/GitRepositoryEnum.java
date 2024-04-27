package com.alinesno.infra.data.integration.enums;

/**
 * 仓库类型
 * @author paul
 * @date 2024年3月10日
 */
public enum GitRepositoryEnum {

	GITLAB("gitlab") ,
	GITHUB("github") ,
	GITEE("gitee") ,
	GITEA("gitea") ,
	BITBUCKET("bitbucket") ,
	ALICODE("alicode")  ;

	private String name ;

	private GitRepositoryEnum(String name) {
		this.name = name ;
	}

	public String getName() {
		return this.name ;
	}

}
