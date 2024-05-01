package com.alinesno.infra.data.integration.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.alinesno.infra.common.core.constants.SpringInstanceScope;
import com.alinesno.infra.common.facade.pageable.DatatablesPageBean;
import com.alinesno.infra.common.facade.pageable.TableDataInfo;
import com.alinesno.infra.common.facade.response.AjaxResult;
import com.alinesno.infra.common.facade.response.ResultCodeEnum;
import com.alinesno.infra.common.web.adapter.plugins.TranslateCode;
import com.alinesno.infra.common.web.adapter.rest.BaseController;
import com.alinesno.infra.data.integration.dto.BingFormGitlabDto;
import com.alinesno.infra.data.integration.entity.BuildGitEntity;
import com.alinesno.infra.data.integration.entity.JobEntity;
import com.alinesno.infra.data.integration.entity.TransEntity;
import com.alinesno.infra.data.integration.enums.GitRepositoryEnum;
import com.alinesno.infra.data.integration.git.params.GitSystemParams;
import com.alinesno.infra.data.integration.git.utils.AccessTokenUtils;
import com.alinesno.infra.data.integration.init.InitDataService;
import com.alinesno.infra.data.integration.service.IBuildGitService;
import com.alinesno.infra.data.integration.service.IJobService;
import com.alinesno.infra.data.integration.service.ITransService;
import com.alinesno.infra.data.integration.vo.ResponseBean;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 【请填写功能名称】Controller
 *
 * @author alinesno
 * @date 2021-06-05
 */
@RestController
@Scope(SpringInstanceScope.PROTOTYPE)
@RequestMapping("/api/integration/buildGit")
public class BuildGitRest extends BaseController<BuildGitEntity, IBuildGitService> {

	// 日志记录
	private static final Logger log = LoggerFactory.getLogger(BuildGitRest.class);

	@Autowired
	private IBuildGitService buildGitService;

	@Autowired
	private InitDataService initDataService;

	@Autowired
	private AccessTokenUtils accessTokenUtils ;

	@Autowired
	private GitSystemParams gitParams;

	@Autowired
	private IJobService jobService ;

	@Autowired
	private ITransService transService ;

	private  String operatorId = "operator_id";


    @TranslateCode
	@ResponseBody
//	@DataFilter
	@PostMapping("/datatables")
	public TableDataInfo datatables(HttpServletRequest request, Model model, DatatablesPageBean page) {
		log.debug("page = {}", ToStringBuilder.reflectionToString(page));

//		String userId = CurrentAccountJwt.getUserId();
		String userId = null ;

		long countGitRepository = 0 ;

		if ( userId != null ) {
			QueryWrapper<BuildGitEntity> wrapper = new QueryWrapper<BuildGitEntity>();
			wrapper.eq(operatorId, userId);
			countGitRepository = buildGitService.count(wrapper);
		} else {
			countGitRepository = buildGitService.count();
		}


		// 初始化用户仓库
		if ( countGitRepository == 0 ) {
			initDataService.initAccountGitRepository(userId);
		}

		TableDataInfo tableInfo = this.toPage(model, this.getFeign(), page);

		return tableInfo;
	}

	@Override
	public IBuildGitService getFeign() {
		return this.buildGitService;
	}

	@ResponseBody
	@PostMapping("saveEntity")
	public AjaxResult save(Model model, @RequestBody BuildGitEntity entity) {

		log.debug("===> save Entity:{}", ToStringBuilder.reflectionToString(entity));

		if (entity.getGitType().equals("gitlab")) {
			entity.setGitIcon("fab fa-gitlab");
		} else if (entity.getGitType().equals("gitee")) {
			entity.setGitIcon("fab fa-gitkraken");
		} else if (entity.getGitType().equals("github")) {
			entity.setGitIcon("fab fa-github");
		}

//		ManagerAccountEntity e = CurrentAccountJwt.get();
//
//		if (e != null) {
//			entity.setOperatorId(e.getId());
//			entity.setTenantId(e.getTenantId());
//			entity.setDepartmentId(e.getDepartmentId());
//		}

		getFeign().save(entity);

		return AjaxResult.success();
	}

	/**
	 * 绑定gitlab账号
	 *
	 * @param gitType
	 * @return
	 */
	@GetMapping("bindingGit")
	public AjaxResult bindingGit(String gitType) {

		log.debug("git type = {}" , gitType);

		return AjaxResult.success();
	}

	/**
	 * 绑定gitlab配置
	 *
	 * @return
	 */
	@PostMapping("/bingFormGitlab")
	public AjaxResult bingFormGitlab(@RequestBody BingFormGitlabDto dto) {
		ResponseBean responseBean = new ResponseBean() ;

		log.debug("BingFormGitlabDto = {}", dto.toString());
//		String accountId = CurrentAccountJwt.getUserId();

		String accountId = null ;

		if(GitRepositoryEnum.GITEA.getName().equals(dto.getGitType())) {


			String userInfo = accessTokenUtils.getGiteaUserInfo(dto.getAccessPrivateToken(), dto.getGitHost() + "/api/v1/user") ;

			buildGitService.bingGitea(accountId, dto , JSONObject.parseObject(userInfo));
		}else if(GitRepositoryEnum.GITLAB.getName().equals(dto.getGitType())) {
			//检查gitlab仓库地址、账号、密码是否正常
			responseBean = buildGitService.bingGitlab(accountId, dto);
		}

		if ( responseBean.getCode() == 200) {
			return AjaxResult.success();
		}else{
			return AjaxResult.error(responseBean.getMessage()) ;
		}


	}

	/**
	 * 解绑git
	 *
	 * @param id
	 * @return
	 */
	@GetMapping("/unBing")
	public AjaxResult unBing(String id, String gitType) {
		buildGitService.unBingGit(id, gitType);
		return AjaxResult.success();
	}

	/**
	 * 让用户跳转到 GitHub 这里不能加@ResponseBody，因为这里是要跳转而不是返回响应
	 * 另外LoginController也不能用@RestController修饰
	 *
	 * @return 跳转url
	 */
	@GetMapping("/getGithubAuthurl")
	public AjaxResult getGithubAuthurl(String gitType) {

		String url = null;

		// github
		if (GitRepositoryEnum.GITHUB.getName().equals(gitType)) {
			String authUrl = gitParams.getOauthParams().getAuthorizeUrl();
			String clientId = gitParams.getOauthParams().getClientId();
			String redirectUrl = gitParams.getOauthParams().getRedirectUrl();

			url = authUrl + "/login/oauth/authorize" + "?client_id=" + clientId + "&redirect_uri=" + redirectUrl
					+ "&scope=read:user,repo";
		} else if (GitRepositoryEnum.GITEE.getName().equals(gitType)) { // gitee

			String authUrl = gitParams.getGiteeOauthParams().getAuthorizeUrl();
			String clientId = gitParams.getGiteeOauthParams().getClientId();
			String redirectUrl = gitParams.getGiteeOauthParams().getRedirectUrl();

			url = authUrl + "/oauth/authorize" + "?client_id=" + clientId + "&redirect_uri=" + redirectUrl
					+ "&response_type=code";
		} else if (GitRepositoryEnum.GITEA.getName().equals(gitType)) { // gitea

			String authUrl = gitParams.getGiteaOauthParams().getAuthorizeUrl();
			String clientId = gitParams.getGiteaOauthParams().getClientId();
			String redirectUrl = gitParams.getGiteaOauthParams().getRedirectUrl();

			url = authUrl + "/login/oauth/authorize" + "?client_id=" + clientId + "&redirect_uri=" + redirectUrl
					+ "&response_type=code&state=STATE";
		}
		log.info("授权url:{}", url);

		return AjaxResult.success("操作成功.", url);
	}

	/**
	 * 检查git仓库是否被作业任务使用，如被使用，则返回错误
	 * @param ids      仓库ID清单
	 * @return
	 */
	@ResponseBody
	@GetMapping("/checkGitIfUsed")
	public AjaxResult checkGitIfUsed(HttpServletRequest request, String ids)  {
		if ( ids == null ) {
			return AjaxResult.error("请求删除的id为空!");
		}

		List<String> idList = Arrays.asList(ids.split(","));

		StringBuffer msg = new StringBuffer();

		String operatorId = null ;
//		// 设置用户
//		ManagerAccountEntity account = CurrentAccountJwt.get() ;
//		if ( account != null ) {
//			operatorId = account.getId();
//		}

		ResponseBean responseBean = checkTransHasUsed(idList, operatorId);
		if ( responseBean.getCode() == 400 ){
			return AjaxResult.error(responseBean.getMessage()) ;
		}


		//检查是否已被作业引用
		QueryWrapper<JobEntity> jobWrapper = new QueryWrapper<>();
		jobWrapper.in("git_id",idList);
		jobWrapper.eq("operator_id", operatorId);
		List<JobEntity> joblist = jobService.list(jobWrapper);
		if ( joblist != null && joblist.size() > 0 )	{
			Set<String> jobSet = new HashSet<String>() ;
			List<String> usedList = new ArrayList<String>();
			for (JobEntity jobEntity : joblist) {
				jobSet.add(String.valueOf(jobEntity.getId())) ;
			}

			for( String jobID :jobSet ){
				usedList.add(jobID) ;
			}

			List<JobEntity> useJoblist = jobService.findByIds( usedList );
			int i = 0 ;
			for (JobEntity jobEntity : useJoblist) {
				//避免提示信息太长，只取前3个任务的名称
				if ( i == 3 ) {
					msg =  msg.deleteCharAt( msg.length() - 1 ) ;
					msg.append("等");
					break;
				}
				msg.append(jobEntity.getName()).append(",");
				i = i + 1 ;
			}

			if (   msg.lastIndexOf(",") == msg.length() - 1   ) {
				msg.deleteCharAt( msg.length() - 1 ) ;
			}

			return AjaxResult.error("仓库已被\"" + msg.toString() + "\"引用,不能删除!") ;
		} else {
			return AjaxResult.success() ;
		}

	}


	/**
	 * 检查git仓库是否被转换任务使用，如被使用，则返回错误
	 * @param idList      仓库ID
	 * @param operatorId  操作员ID
	 * @return
	 */
	private ResponseBean checkTransHasUsed(List<String> idList,String operatorId){
		ResponseBean result = new ResponseBean() ;
		StringBuffer msg = new StringBuffer();

		//检查是否已被转换引用
		QueryWrapper<TransEntity> tranWrapper = new QueryWrapper<>();
		tranWrapper.in("git_id",idList);
		tranWrapper.eq("operator_id", operatorId);
		List<TransEntity> tranlist = transService.list(tranWrapper);
		if ( tranlist != null && tranlist.size() > 0 )	{
			Set<String> tranSet = new HashSet<String>() ;
			List<String> usedList = new ArrayList<String>();
			for (TransEntity transEntity : tranlist) {
				tranSet.add(String.valueOf(transEntity.getId())) ;
			}

			for( String tranID :tranSet ){
				usedList.add(tranID) ;
			}

			List<TransEntity> useTranlist = transService.findByIds( usedList );
			int i = 0 ;
			for (TransEntity tranEntity : useTranlist) {
				//避免提示信息太长，只取前3个任务的名称
				if ( i == 3 ) {
					msg =  msg.deleteCharAt( msg.length() - 1 ) ;
					msg.append("等");
					break;
				}
				msg.append(tranEntity.getName()).append(",");
				i = i + 1 ;
			}
			if (   msg.lastIndexOf(",") == msg.length() - 1   ) {
				msg.deleteCharAt( msg.length() - 1 ) ;
			}

			result.setCode(ResultCodeEnum.FAIL);
			result.setMessage("仓库已被\"" + msg.toString() + "\"引用,不能删除!") ;

		} else {
			result.setCode(ResultCodeEnum.SUCCESS);
		}

		return  result ;

	}


}
