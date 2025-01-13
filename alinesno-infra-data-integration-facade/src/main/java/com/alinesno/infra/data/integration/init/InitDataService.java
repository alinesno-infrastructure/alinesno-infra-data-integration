package com.alinesno.infra.data.integration.init;

/**
 * 初始化一些基础的应用数据，用于用户体验
 *
 * @author paul
 * @version 1.0.0
 */
public interface InitDataService {

	/**
	 * 初始化代码仓库
	 *
	 * @param accountId
	 */
	void initAccountGitRepository(String accountId);

	/**
	 * 初始化示例仓库
	 *
	 * @param accountId
	 */
	void initDemo(String accountId);

}
