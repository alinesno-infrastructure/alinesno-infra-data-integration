package com.alinesno.infra.common.web.adapter.plugins;

import com.alinesno.infra.data.integration.entity.JobEntity;
import com.alinesno.infra.data.integration.enums.RunStatusEnum;
import com.alinesno.infra.data.integration.enums.RunTypeEnum;
import com.alinesno.infra.data.integration.service.IJobService;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;


/**
 * 任务监控转换插件
 *
 * @author paul
 * @version 1.0.0
 */
@Component("jobMonitorPlugin")
public class JobMonitorPlugin implements TranslatePlugin {

    @Autowired
    private IJobService jobService;

    private final String MONITOR_JOB_ID = "monitorJobId";
    private final String JOB_NAME = "name";
    private final String JOB_TYPE = "type";

    private final String RUN_STATUS = "runStatus";
    private final String MONITOR_STATUS = "monitorStatus";

    @Override
    public void translate(ArrayNode node, TranslateCode convertCode) {

        if ( !node.isEmpty() ) {

            //获取任务列表
            List<String> ids = this.extractIds(node, MONITOR_JOB_ID);
            if(ids == null || ids.size() == 0) {
                return ;
            }

            List<JobEntity> list = this.jobService.findByIds(ids);
            Map<String, JobEntity> jobMap = this.toEntityMap(list);

            //转换逻辑
            node.forEach(jsonObject -> {
                //从返回的列表中获取任务id
                String monitorJobId = jsonObject.get(MONITOR_JOB_ID).asText();
                ObjectNode rootNode = (ObjectNode) jsonObject;

                //从查找的任务列表里拿到对应的任务
                JobEntity entity = jobMap.get(monitorJobId);
                if ( entity != null ) {
                    //设置返回值
                    rootNode.put(JOB_NAME + LABEL_SUFFER, entity.getName());
                    rootNode.put(JOB_TYPE + LABEL_SUFFER, RunTypeEnum.getEnumDesc(entity.getType()));
                }

                SimpleDateFormat runStatusTmp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                rootNode.put(RUN_STATUS + LABEL_SUFFER, runStatusTmp.format(Long.valueOf(jsonObject.get(RUN_STATUS).asText())));
                rootNode.put(MONITOR_STATUS + LABEL_SUFFER, RunStatusEnum.getEnumDesc(Integer.valueOf(jsonObject.get(MONITOR_STATUS).asText())));

            });

        }



    }
}
