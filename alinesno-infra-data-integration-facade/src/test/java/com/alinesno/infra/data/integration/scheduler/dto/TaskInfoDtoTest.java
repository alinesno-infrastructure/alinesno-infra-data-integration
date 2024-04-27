//package com.alinesno.infra.data.etl.scheduler.dto;
//
//
//import com.alibaba.fastjson.JSONObject;
//import com.alinesno.infra.data.etl.datasource.MappingBean;
//import com.alinesno.infra.data.etl.scheduler.enums.SinkReaderEnums;
//import com.alinesno.infra.data.etl.scheduler.enums.SourceReaderEnums;
//
//import java.util.ArrayList;
//import java.util.List;
//
//class TaskInfoDtoTest {
//
//    public static void main(String[] args) {
//        testToString();
//    }
//
//    static void testToString(){
//        TaskInfoDto taskInfoDto = new TaskInfoDto() ;
//        taskInfoDto.setName("data_etl_mysql_to_clickhouse");
//        taskInfoDto.setDescribe("存储服务数据迁移到数据仓库中.");
//
//        // 上下文环境
//        TaskContext taskContext = new TaskContext() ;
//
//        taskInfoDto.setContext(taskContext);
//
//        // 配置
//        Settings settings = new Settings() ;
//        settings.setCron("*/3 * * * * ?");
//
//        taskInfoDto.setSettings(settings);
//
//        // 读取端
//        SourceReader reader = new SourceReader();
//        reader.setName("mysql");
//        reader.setType(SourceReaderEnums.MYSQL.getCode());
//        reader.setDriverClass("com.mysql.cj.jdbc.Driver");
//        reader.setJdbcUrl("jdbc:mysql://localhost:3306/dev_alinesno_infra_data_etl_v100?serverTimezone=GMT%2B8&zeroDateTimeBehavior=CONVERT_TO_NULL");
//        reader.setPassword("adminer");
//        reader.setUsername("root");
//        reader.setQuerySql("select * from kfinfo");
//
//        taskInfoDto.setReader(reader);
//
//        // 写入端
//        SinkWriter writer = new SinkWriter();
//        writer.setName("clickhouse");
//        writer.setType(SinkReaderEnums.CLICKHOUSE.getCode());
//        writer.setDriverClass("com.clickhouse.jdbc.ClickHouseDriver");
//        writer.setJdbcUrl("jdbc:clickhouse://127.0.0.1:8123/default?rewriteBatchedStatements=true") ;
//        writer.setUsername("default");
//        writer.setPassword("");
//        writer.setWriteModel("append");
//
//        taskInfoDto.setWriter(writer);
//
//        // 数据过滤插件
//        List<FilterPlugins> plugins = new ArrayList<>();
//
//        FilterPlugins securityPlugin = new FilterPlugins() ;
//        securityPlugin.setCode("security");
//        securityPlugin.setName("安全过滤应用");
//
//        FilterPlugins nullPlugin = new FilterPlugins() ;
//        nullPlugin.setCode("null_filter");
//        nullPlugin.setName("去掉为空数据");
//
//        plugins.add(securityPlugin) ;
//        plugins.add(nullPlugin) ;
//
//        taskInfoDto.setPlugins(plugins);
//
//        // 字段映射关系
//        List<MappingBean> mappingBeans = new ArrayList<>() ;
//        // 添加字段映射关系
//        mappingBeans.add(new MappingBean("Name", "Name", DataMappingEnums.COPY.getCode()));
//        mappingBeans.add(new MappingBean("CardNo", "CardNo", DataMappingEnums.COPY.getCode()));
//        mappingBeans.add(new MappingBean("Descriot", "Descriot", DataMappingEnums.COPY.getCode()));
//        mappingBeans.add(new MappingBean("CtfTp", "CtfTp", DataMappingEnums.COPY.getCode()));
//        mappingBeans.add(new MappingBean("CtfId", "CtfId", DataMappingEnums.COPY.getCode()));
//        mappingBeans.add(new MappingBean("Gender", "Gender", DataMappingEnums.COPY.getCode()));
//        mappingBeans.add(new MappingBean("Birthday", "Birthday", DataMappingEnums.COPY.getCode()));
//        mappingBeans.add(new MappingBean("Address", "Address", DataMappingEnums.COPY.getCode()));
//        mappingBeans.add(new MappingBean("Zip", "Zip", DataMappingEnums.COPY.getCode()));
//        mappingBeans.add(new MappingBean("Dirty", "Dirty", DataMappingEnums.COPY.getCode()));
//        mappingBeans.add(new MappingBean("District1", "District1", DataMappingEnums.COPY.getCode()));
//        mappingBeans.add(new MappingBean("District2", "District2", DataMappingEnums.COPY.getCode()));
//        mappingBeans.add(new MappingBean("District3", "District3", DataMappingEnums.COPY.getCode()));
//        mappingBeans.add(new MappingBean("District4", "District4", DataMappingEnums.COPY.getCode()));
//        mappingBeans.add(new MappingBean("District5", "District5", DataMappingEnums.COPY.getCode()));
//        mappingBeans.add(new MappingBean("District6", "District6", DataMappingEnums.COPY.getCode()));
//        mappingBeans.add(new MappingBean("FirstNm", "FirstNm", DataMappingEnums.COPY.getCode()));
//        mappingBeans.add(new MappingBean("LastNm", "LastNm", DataMappingEnums.COPY.getCode()));
//        mappingBeans.add(new MappingBean("Duty", "Duty", DataMappingEnums.COPY.getCode()));
//        mappingBeans.add(new MappingBean("Mobile", "Mobile", DataMappingEnums.COPY.getCode()));
//        mappingBeans.add(new MappingBean("Tel", "Tel", DataMappingEnums.COPY.getCode()));
//        mappingBeans.add(new MappingBean("Fax", "Fax", DataMappingEnums.COPY.getCode()));
//        mappingBeans.add(new MappingBean("EMail", "EMail", DataMappingEnums.COPY.getCode()));
//        mappingBeans.add(new MappingBean("Nation", "Nation", DataMappingEnums.COPY.getCode()));
//        mappingBeans.add(new MappingBean("Taste", "Taste", DataMappingEnums.COPY.getCode()));
//        mappingBeans.add(new MappingBean("Education", "Education", DataMappingEnums.COPY.getCode()));
//        mappingBeans.add(new MappingBean("Company", "Company", DataMappingEnums.COPY.getCode()));
//        mappingBeans.add(new MappingBean("CTel", "CTel", DataMappingEnums.COPY.getCode()));
//        mappingBeans.add(new MappingBean("CAddress", "CAddress", DataMappingEnums.COPY.getCode()));
//        mappingBeans.add(new MappingBean("CZip", "CZip", DataMappingEnums.COPY.getCode()));
//        mappingBeans.add(new MappingBean("Family", "Family", DataMappingEnums.COPY.getCode()));
//        mappingBeans.add(new MappingBean("Version", "Version", DataMappingEnums.COPY.getCode()));
//        mappingBeans.add(new MappingBean("id", "id", DataMappingEnums.COPY.getCode()));
//
//        taskInfoDto.setFileMap(mappingBeans);
//
//        System.out.println("taskInfoDto = \r\n " + JSONObject.toJSONString(taskInfoDto));
//    }
//
//}