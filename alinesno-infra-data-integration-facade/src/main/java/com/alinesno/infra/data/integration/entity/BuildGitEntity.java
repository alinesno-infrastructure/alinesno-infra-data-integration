package com.alinesno.infra.data.integration.entity;


import com.alinesno.infra.common.facade.mapper.entity.InfraBaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * <p>
 * gitLab仓库地址信息
 * </p>
 *
 * @author paul
 * @date 2024年3月10日
 */
@ToString
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "build_git" , autoResultMap = true)
public class BuildGitEntity extends InfraBaseEntity {

	private static final long serialVersionUID = 1L;

	@ColumnComment("仓库名称")
	@ColumnType(length = 64)
	@TableField("git_name")
	private String gitName;

	@ColumnComment("仓库图标")
	@ColumnType(length = 64)
	@TableField("git_icon")
	private String gitIcon ;

	@ColumnComment("仓库地址")
	@ColumnType(length = 255)
	@TableField("git_host")
	private String gitHost;

	@ColumnComment("仓库地址")
	@ColumnType(length = 255)
	@TableField("git_url")
	private String gitUrl; // 如 http://192.168.204.130:19802/gitlab-instance-ddd728ad/alinesno-cloud-data-etl-databrain.git

	@ColumnComment("项目分支")
	@ColumnType(length = 255)
	@TableField("branch_name")
	private String branchName;

	@ColumnComment("gitlab账号")
	@ColumnType(length = 64)
	@TableField("git_user_name")
//	@TableField(typeHandler = AesTypeHandler.class)
	private String gitUserName; // 仓库账号

	@ColumnComment("gitlab账号密码")
	@ColumnType(length = 512)
	@TableField("password")
//	@TableField(typeHandler = AesTypeHandler.class)
	private String password;

	@ColumnComment("gitlab账号id")
	@ColumnType(length = 64)
	@TableField("git_user_id")
	private int gitUserId; // 仓库账号

	@ColumnComment("仓库空间")
	@ColumnType(length = 64)
	@TableField("git_namespace")
	private String gitNamespace ; // 仓库空间

	@ColumnComment("仓库类型")
	@ColumnType(length = 16)
	@TableField
	private String gitType; // 仓库类型

	@ColumnComment("内置gitlab")
	@ColumnType(length = 1)
	@TableField("inner_git")
	private int innerGit ;

	@ColumnComment("是否绑定")
	@ColumnType(length = 1)
	@TableField("has_bing")
	private int hasBing ; // 是否绑定

	@TableField("access_private_token")
	private String AccessPrivateToken;

	@ColumnComment("刷新token")
	@TableField("refresh_token")
	private String refreshToken ;

	@ColumnComment("超时时间")
	@TableField("expires_in")
	private int expiresIn ;

	@ColumnComment("绑定第三方git账号信息")
	@ColumnType(length = 1024)
	@TableField("bing_git_info")
	private String bingGitInfo ; // 绑定第三方git账号信息

}
