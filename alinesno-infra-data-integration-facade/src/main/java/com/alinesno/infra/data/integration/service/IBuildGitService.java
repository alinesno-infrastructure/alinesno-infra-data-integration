package com.alinesno.infra.data.integration.service;

import com.alibaba.fastjson.JSONObject;
import com.alinesno.infra.common.facade.services.IBaseService;
import com.alinesno.infra.data.integration.dto.BingFormGitlabDto;
import com.alinesno.infra.data.integration.dto.FileDto;
import com.alinesno.infra.data.integration.entity.BuildGitEntity;
import com.alinesno.infra.data.integration.vo.ResponseBean;

import java.util.List;

/**
 * 【请填写功能名称】Service接口
 *
 * @author paul
 * @date 2024年3月10日
 */
public interface IBuildGitService extends IBaseService<BuildGitEntity> {

	/**
	 * 编写github
	 *
	 * @param accountId
	 * @param githubAccessToken
	 */
	void bingGithub(String accountId, String githubAccessToken, JSONObject userInfo);

	/**
	 * 解除git绑定
	 *
	 * @param id
	 */

	void unBingGit(String id, String gitType);

	/**
	 * 保存gitee
	 *
	 * @param accountId
	 * @param accessToken
	 * @param userInfo
	 */
	void bingGitee(String accountId, JSONObject accessToken, JSONObject userInfo);

	/**
	 * 绑定gitlab用户信息
	 *
	 * @param accountId
	 * @param dto
	 */
	ResponseBean bingGitlab(String accountId, BingFormGitlabDto dto);

	/**
	 * 绑定gitea用户信息
	 *
	 * @param accountId
	 * @param accessToken
	 * @param userInfo
	 */
	void bingGitea(String accountId, JSONObject accessToken, JSONObject userInfo);

	/**
	 * 绑定 gitea
	 * @param accountId
	 * @param dto
	 */
	void bingGitea(String accountId, BingFormGitlabDto dto , JSONObject userInfo);


	ResponseBean checkGitUrl(BingFormGitlabDto dto);

	ResponseBean downloadFile(BuildGitEntity buildGit, String filePath, String fileName);

	void initFile(List<FileDto> files);

}
