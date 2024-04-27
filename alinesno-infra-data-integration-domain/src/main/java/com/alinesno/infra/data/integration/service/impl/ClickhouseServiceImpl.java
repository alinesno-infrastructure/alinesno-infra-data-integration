package com.alinesno.infra.data.integration.service.impl;

import com.alinesno.infra.data.integration.config.ClickHouseConfig;
import com.alinesno.infra.data.integration.service.IClickhouseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

/**
 * 【连接clickhouse数据库测试】
 *
 * @author paul
 * @date 2024年3月10日
 */
@Service
public class ClickhouseServiceImpl implements IClickhouseService {


    //日志记录
    private static final Logger log = LoggerFactory.getLogger(ClickhouseServiceImpl.class);

    @Override
    public void TestClickhouse(){

        log.info("===========测试开始============");
        String sql="select * from alinesno_data_prod_alert_integer limit 1";
        List<Map<String,String>> result= ClickHouseConfig.exeSql(sql);
        log.info("===========查询结果============");
        log.info("clickhouse查询结果为：{}",result);

    }
}
