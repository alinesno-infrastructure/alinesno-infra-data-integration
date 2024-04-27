package com.alinesno.infra.data.integration.service.impl;

import com.alinesno.infra.data.integration.entity.BuildGitEntity;
import com.alinesno.infra.data.integration.enums.BingGitEnum;
import com.alinesno.infra.data.integration.enums.GitRepositoryEnum;
import com.alinesno.infra.data.integration.init.InitDataService;
import com.alinesno.infra.data.integration.service.IBuildGitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * 初始化项目和代码各个工程
 *
 * @author paul
 * @date 2024年3月10日
 */
@Service
public class InitDataServiceImpl implements InitDataService {

	@Autowired
	private IBuildGitService buildGitService ;

	public static final int INNER_GIT = 1 ;

	/**
	 * 初始化代码仓库
	 */
	@Override
	public void initAccountGitRepository(String userId) {

		List<BuildGitEntity> listGit = new ArrayList<BuildGitEntity>() ;

		BuildGitEntity gitlab = new BuildGitEntity();
		gitlab.setGitName(GitRepositoryEnum.GITLAB.getName());
		gitlab.setGitIcon(GitRepositoryEnum.GITLAB.getName());
		gitlab.setFieldProp("GitLab 是目前主流的企业内部 Git 代码托管解决方案，您可以绑定公网的GitLab代码仓库，实现丰富的 Git 代码管理功能");
		gitlab.setGitType(GitRepositoryEnum.GITLAB.getName());
		gitlab.setInnerGit(INNER_GIT) ;
		gitlab.setHasBing(BingGitEnum.NOT_BING.getV());
		if ( userId != null ) {
			gitlab.setOperatorId(Long.valueOf(userId));
		}

		BuildGitEntity gitee = new BuildGitEntity();
		gitee.setGitName(GitRepositoryEnum.GITEE.getName());
		gitee.setGitIcon(GitRepositoryEnum.GITEE.getName());
		gitee.setFieldProp("gitee代码托管·协作开发平台，开发者超过 800 万，托管项目超过 2000 万，汇聚几乎所有本土原创开源项目");
		gitee.setGitType(GitRepositoryEnum.GITEE.getName());
		gitee.setInnerGit(INNER_GIT) ;
		gitee.setHasBing(BingGitEnum.NOT_BING.getV());
		if ( userId != null ) {
			gitlab.setOperatorId(Long.valueOf(userId));
		}

		BuildGitEntity github = new BuildGitEntity();
		github.setGitName(GitRepositoryEnum.GITHUB.getName());
		github.setGitIcon(GitRepositoryEnum.GITHUB.getName());
		github.setFieldProp("Github除了Git代码仓库托管及基本的Web管理界面以外，还提供了订阅、讨论组、文本渲染、在线文件编辑器、协作图谱（报表）、代码片段分享（Gist）等功能");
		github.setGitType(GitRepositoryEnum.GITHUB.getName());
		github.setInnerGit(INNER_GIT) ;
		github.setHasBing(BingGitEnum.NOT_BING.getV());
		if ( userId != null ) {
			gitlab.setOperatorId(Long.valueOf(userId));
		}

		BuildGitEntity gitea = new BuildGitEntity();
		gitea.setGitName(GitRepositoryEnum.GITEA.getName());
		gitea.setGitIcon(GitRepositoryEnum.GITEA.getName());
		gitea.setFieldProp("平台内置的一个简单git管理工具，一款极易搭建的自助Git服务，Git来有效管理您的项目");
		gitea.setGitType(GitRepositoryEnum.GITEA.getName());
		gitea.setInnerGit(INNER_GIT) ;
		gitea.setHasBing(BingGitEnum.NOT_BING.getV());
		if ( userId != null ) {
			gitlab.setOperatorId(Long.valueOf(userId));
		}

		listGit.add(gitlab) ;
		listGit.add(gitee) ;
		listGit.add(github) ;
		listGit.add(gitea) ;

		buildGitService.saveBatch(listGit);
	}

	@Override
	public void initDemo(String accountId) {

		// 1. 初始化示例项目

		// 2. 初始化示例数据库

		// 3. 初始化数据库表

	}
}
