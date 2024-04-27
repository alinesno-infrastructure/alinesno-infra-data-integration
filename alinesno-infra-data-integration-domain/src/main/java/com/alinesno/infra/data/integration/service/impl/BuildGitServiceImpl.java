package com.alinesno.infra.data.integration.service.impl;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSONObject;
import com.alinesno.infra.common.core.service.impl.IBaseServiceImpl;
import com.alinesno.infra.common.facade.response.ResultCodeEnum;
import com.alinesno.infra.data.integration.dto.BingFormGitlabDto;
import com.alinesno.infra.data.integration.dto.FileDto;
import com.alinesno.infra.data.integration.entity.BuildGitEntity;
import com.alinesno.infra.data.integration.enums.BingGitEnum;
import com.alinesno.infra.data.integration.enums.GitRepositoryEnum;
import com.alinesno.infra.data.integration.mapper.BuildGitMapper;
import com.alinesno.infra.data.integration.service.IBuildGitService;
import com.alinesno.infra.data.integration.vo.ResponseBean;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author paul
 * @date 2024年3月10日
 */
@Service
public class BuildGitServiceImpl extends IBaseServiceImpl<BuildGitEntity, BuildGitMapper> implements IBuildGitService {
	// 日志记录
	private static final Logger log = LoggerFactory.getLogger(BuildGitServiceImpl.class);

	private String OPERATOR_ID = "operator_id" ;

//	@Autowired
//	private IBuildApplicationService applicationService;


	private static String kettlePath;

	@Value("${kettle.upload-path}")
	public void setKettlePath(String kettlePath) {
		this.kettlePath = kettlePath;
	}

	@Override
	public void bingGithub(String accountId, String githubAccessToken, JSONObject userInfo) {

		QueryWrapper<BuildGitEntity> wrapper = new QueryWrapper<BuildGitEntity>();
		wrapper.eq(OPERATOR_ID, accountId).eq("git_type", GitRepositoryEnum.GITHUB.getName());

		BuildGitEntity e = getOne(wrapper);

		e.setAccessPrivateToken(githubAccessToken);
		e.setHasBing(BingGitEnum.HAS_BING.getV());
		e.setBingGitInfo(userInfo.toJSONString());

		this.update(e);
	}

	@Override
	public void unBingGit(String id, String gitType) {

		BuildGitEntity e = getById(id);

		if (GitRepositoryEnum.GITHUB.getName().equals(e.getGitType())) { // 解绑github

			e.setHasBing(BingGitEnum.NOT_BING.getV());
			this.update(e);

		} else if (GitRepositoryEnum.GITEE.getName().equals(e.getGitType())) { // 解绑gitee

			e.setHasBing(BingGitEnum.NOT_BING.getV());
			this.update(e);

		} else if (GitRepositoryEnum.GITLAB.getName().equals(e.getGitType())) { // 解绑gitlab

			e.setHasBing(BingGitEnum.NOT_BING.getV());
			this.update(e);

		} else if (GitRepositoryEnum.GITEA.getName().equals(e.getGitType())) { // 解绑gitea

			e.setHasBing(BingGitEnum.NOT_BING.getV());
			this.update(e);

		}

	}

	@Override
	public void bingGitee(String accountId, JSONObject accessToken, JSONObject userInfo) {

		QueryWrapper<BuildGitEntity> wrapper = new QueryWrapper<BuildGitEntity>();
		wrapper.eq(OPERATOR_ID, accountId).eq("git_type", GitRepositoryEnum.GITEE.getName());

		BuildGitEntity e = getOne(wrapper);

		e.setAccessPrivateToken(accessToken.getString("access_token"));
		e.setRefreshToken(accessToken.getString("refresh_token"));
		e.setExpiresIn(accessToken.getIntValue("expires_in"));

		e.setHasBing(BingGitEnum.HAS_BING.getV());
		e.setBingGitInfo(userInfo.toJSONString());

		this.update(e);

	}

	@Override
	public ResponseBean bingGitlab(String accountId, BingFormGitlabDto dto) {

//		String gitlabId = dto.getGitlabId();
//
//		BuildGitEntity git = getById(gitlabId);
//		git.setGitUrl(dto.getGitUrl());
//		git.setGitUserName(dto.getGitUserName());
//		git.setAccessPrivateToken(dto.getAccessPrivateToken());
//		git.setHasBing(BingGitEnum.HAS_BING.getV());
//		git.setPassword(dto.getPassword());
//		git.setBranchName(dto.getBranchName());
//		this.update(git);
		return checkGitUrl(dto);
	}

	@Override
	public void bingGitea(String accountId, JSONObject accessToken, JSONObject userInfo) {

		QueryWrapper<BuildGitEntity> wrapper = new QueryWrapper<BuildGitEntity>();
		wrapper.eq(OPERATOR_ID, accountId).eq("git_type", GitRepositoryEnum.GITEA.getName());

		BuildGitEntity git = getOne(wrapper);

		git.setAccessPrivateToken(accessToken.getString("access_token"));
		git.setRefreshToken(accessToken.getString("refresh_token"));
		git.setExpiresIn(accessToken.getIntValue("expires_in"));

		git.setHasBing(BingGitEnum.HAS_BING.getV());
		git.setBingGitInfo(userInfo.toJSONString());

		this.update(git);
	}

	@Override
	public void bingGitea(String accountId, BingFormGitlabDto dto, JSONObject userInfo) {
		String gitlabId = dto.getGitlabId();

		BuildGitEntity git = getById(gitlabId);
		git.setGitUserName(dto.getGitUserName());
		git.setPassword(dto.getPassword());
		git.setHasBing(BingGitEnum.HAS_BING.getV());

		// 获取用户信息
		git.setBingGitInfo(userInfo.toJSONString());

		this.update(git);

	}


	@Override
	public ResponseBean checkGitUrl(BingFormGitlabDto dto){
		//默认校验成功
		ResponseBean result = new ResponseBean() ;
		result.setCode(ResultCodeEnum.SUCCESS);
		log.debug("开始检查仓库地址：{},用户:{}是否有效,", dto.getGitUrl(),dto.getGitUserName());
		try {
			UsernamePasswordCredentialsProvider credentials = new UsernamePasswordCredentialsProvider(dto.getGitUserName(), dto.getPassword());
			Git.lsRemoteRepository()
					.setRemote(dto.getGitUrl())
					.setCredentialsProvider(credentials)
					.call();
		} catch ( GitAPIException e) {
			// Username or password is not valid
			result.setCode(ResultCodeEnum.FAIL);
			result.setMessage("仓库地址,用户名,密码无效!");
		}
		return result ;

	}

	/**
	 * 先根遍历序递归删除文件夹
	 *
	 * @param dirFile 要被删除的文件或者目录
	 * @return 删除成功返回true, 否则返回false
	 */
	public static boolean deleteFile(File dirFile) {
		// 如果dir对应的文件不存在，则退出
		if (!dirFile.exists()) {
			return false;
		}

		if (dirFile.isFile()) {
			return dirFile.delete();
		} else {

			for (File file : dirFile.listFiles()) {
				deleteFile(file);
			}
		}
		return dirFile.delete();
	}

	/**
	 * 从gitlab仓库中下载文件
	 * @param filePath   gitlab中脚本文件的完整路径
	 * @param fileName   下载后的文件名
	 * @return 从gitlab仓库中下载的文件
	 */
	@Override
	public ResponseBean downloadFile(BuildGitEntity buildGit, String filePath, String fileName) {

		ResponseBean result = new ResponseBean() ;
		boolean if_success = true ;
		//克隆远程仓库
		Git git = null;

		//获取文件路径 通过代码仓库id区分文件，避免不同仓库有同名的任务
//		checkDirPath();
//		String downloadFilePath = kettlePath + "/" + fileName;
		checkDirPath( kettlePath + File.separator + buildGit.getId() + File.separator );
		String downloadFilePath = kettlePath + File.separator + buildGit.getId() + File.separator + fileName;
		deleteFile(new File(kettlePath + File.separator + filePath));

		try {
			git = Git.cloneRepository()
					.setURI(buildGit.getGitUrl())
					.setCredentialsProvider(new UsernamePasswordCredentialsProvider(buildGit.getGitUserName(), buildGit.getPassword()))
					.setBranch(buildGit.getBranchName())
					.setDirectory(new File(kettlePath+ "/"+filePath))
					.call();

			//获取最新的提交
			RevCommit commit = null;

			commit = git.log().setMaxCount(1).call().iterator().next();

			//获取提交的根树对象
			RevWalk walk = new RevWalk(git.getRepository());
			RevTree tree = null;

			tree = walk.parseTree(commit.getTree().getId());

			//创建树游走对象
			TreeWalk treeWalk = new TreeWalk(git.getRepository());

			treeWalk.addTree(tree);

			treeWalk.setRecursive(true);

			//根据文件名查找文件并下载
			while (treeWalk.next()) {
				log.debug("Got path:{}",treeWalk.getPathString());
				if (treeWalk.getPathString().equals(filePath)) {
					log.info("Git path:{} ,filePath:{} ",treeWalk.getPathString(),filePath);
					File file = new File(downloadFilePath);

					ObjectId objectId = treeWalk.getObjectId(0);
					ObjectLoader loader = git.getRepository().open(objectId);
					loader.copyTo(new FileOutputStream(file));
				}
			}

			//清理工作目录
			walk.dispose();
			git.close();
			deleteFile(new File(kettlePath+ "/"+filePath));

		} catch ( GitAPIException e ) {
			e.printStackTrace();
			if_success = false ;
			log.error(e.getMessage());
			result.setCode(ResultCodeEnum.FAIL);
			result.setMessage(e.getMessage());
		} catch ( IncorrectObjectTypeException e ) {
			e.printStackTrace();
			if_success = false ;
			log.error(e.getMessage());
			result.setCode(ResultCodeEnum.FAIL);
			result.setMessage(e.getMessage());
		} catch ( CorruptObjectException e ) {
			e.printStackTrace();
			if_success = false ;
			log.error(e.getMessage());
			result.setCode(ResultCodeEnum.FAIL);
			result.setMessage(e.getMessage());
		} catch ( MissingObjectException e ) {
			e.printStackTrace();
			if_success = false ;
			log.error(e.getMessage());
			result.setCode(ResultCodeEnum.FAIL);
			result.setMessage(e.getMessage());
		} catch ( IOException e ) {
			e.printStackTrace();
			if_success = false ;
			log.error(e.getMessage());
			result.setCode(ResultCodeEnum.FAIL);
			result.setMessage(e.getMessage());
		}

		if ( if_success ) {
			result.setCode(ResultCodeEnum.SUCCESS);
			result.setMessage(downloadFilePath);
		}

		return result ;

	}

	/**
	 * 获取多个文件并写入本地
	 *
	 * @param files 多个文件名称和id
	 */
	@Override
	public void initFile(List<FileDto> files) {
		//检查本地路径
		for (FileDto file : files) {
			//检查本地路径
			checkDirPath(kettlePath +File.separator + file.getBuildGit().getId() + File.separator);
			downloadFile(file.getBuildGit(), file.getFilePath(), file.getFileName() ) ;
		}
	}


	/**
	 * 检查本地路径有没有文件，没有则创建
	 */
	private static void checkDirPath(String filePath) {

		if ( !FileUtil.exist(filePath) ) {
			log.debug("需要创建的目录名称为：{}",filePath);
			FileUtil.mkdir(filePath);
		}


	}



}
