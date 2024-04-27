package com.alinesno.infra.data.integration;

import com.alinesno.infra.common.core.context.SpringContext;
import com.alinesno.infra.common.facade.enable.EnableActable;
import com.alinesno.infra.common.web.adapter.sso.enable.EnableInfraSsoApi;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * 集成一个Java开发示例工具
 * @author paul
 * @date 2024年3月10日
 */
@EnableActable
@EnableInfraSsoApi
@MapperScan("com.alinesno.infra.data.integration.mapper")
@SpringBootApplication
public class DataIntegrationApplication {

	public static <T> void main(String[] args) {
		SpringApplication.run(DataIntegrationApplication.class, args);
	}

	@Bean
	public SpringContext getSpringContext(){
		return new SpringContext() ;
	}
}