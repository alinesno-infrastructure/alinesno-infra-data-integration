package com.alinesno.infra.data.integration.config;

import com.alinesno.infra.common.facade.enable.EnableActable;
import com.alinesno.infra.common.web.adapter.sso.enable.EnableInfraSsoApi;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * 项目配置管理
 */
@Configuration
@EnableActable
@EnableInfraSsoApi
@MapperScan("com.alinesno.infra.data.integration.mapper")
public class AppConfiguration {

}
