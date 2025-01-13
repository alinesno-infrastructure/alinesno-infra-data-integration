package com.alinesno.infra.data.integration.config;

import com.alinesno.infra.common.extend.datasource.enable.EnableInfraDataScope;
import com.alinesno.infra.common.facade.enable.EnableActable;
import com.alinesno.infra.common.web.adapter.sso.enable.EnableInfraSsoApi;
import com.alinesno.infra.common.web.log.aspect.LogAspect;
import com.dtflys.forest.springboot.annotation.ForestScan;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 项目配置管理
 */
@Slf4j
@EnableInfraDataScope
@Configuration
@EnableActable
@EnableInfraSsoApi
@ForestScan({
        "com.alinesno.infra.common.web.adapter.base.consumer"
})
@MapperScan("com.alinesno.infra.data.integration.mapper")
public class AppConfiguration implements CommandLineRunner {

    @Bean
    public LogAspect getLogAspect(){
        return new LogAspect() ;
    }

    @Override
    public void run(String... args) throws Exception {
        log.debug("AppConfiguration.run");
    }
}

