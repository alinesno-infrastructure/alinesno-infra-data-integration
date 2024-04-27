package com.alinesno.infra.data.integration.dto;

import lombok.Data;

/**
 * 保存绑定的gitlab
 *
 * @author paul
 * @date 2024年3月10日
 */
@Data
public class BingFormGitlabDto {

	private String gitlabId ;
	private String gitUrl ;
	private String gitHost ;
	private String branchName ; // 项目分支
	private String gitUserName;
	private String password;
	private int gitUserId;
	private String gitType ;    // 仓库类型
	private String AccessPrivateToken;
}
