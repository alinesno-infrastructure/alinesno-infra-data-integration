package com.alinesno.infra.common.web.adapter.plugins;


import com.alinesno.infra.data.integration.entity.TransEntity;
import com.alinesno.infra.data.integration.enums.RunStatusEnum;
import com.alinesno.infra.data.integration.enums.RunTypeEnum;
import com.alinesno.infra.data.integration.service.ITransService;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;


/**
 * 转换监控转换插件
 *
 * @author paul
 * @date 2024年3月10日
 */
@Component("transMonitorPlugin")
public class TransMonitorPlugin implements TranslatePlugin {

    @Autowired
    private ITransService transService;

    private final String MONITOR_TRANS_ID = "monitorTransid";
    private final String TRANS_NAME = "name";
    private final String TRANS_TYPE = "type";

    private final String RUN_STATUS = "runStatus";
    private final String MONITOR_STATUS = "monitorStatus";

    @Override
    public void translate(ArrayNode node, TranslateCode convertCode) {
        if ( !node.isEmpty() ) {
            //获取任务列表
            List<String> ids = this.extractIds(node, MONITOR_TRANS_ID);
            if(ids == null || ids.size() == 0) {
                return ;
            }

            List<TransEntity> list = this.transService.findByIds(ids);
            Map<String, TransEntity> transMap = this.toEntityMap(list);

            //转换逻辑
            node.forEach(jsonObject -> {
                ObjectNode rootNode = (ObjectNode) jsonObject;
                //从返回的列表中获取转换id
                String monitorTransId = jsonObject.get(MONITOR_TRANS_ID).asText();
                //从查找的转换列表里拿到对应的转换
                TransEntity entity = transMap.get(monitorTransId);
                if (entity != null) {
                    //设置返回值
                    rootNode.put(TRANS_NAME + LABEL_SUFFER, entity.getName());
                    rootNode.put(TRANS_TYPE + LABEL_SUFFER, RunTypeEnum.getEnumDesc(entity.getType()));
                }

                SimpleDateFormat runStatusTmp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                rootNode.put(RUN_STATUS + LABEL_SUFFER, runStatusTmp.format(Long.valueOf(jsonObject.get(RUN_STATUS).asText())));
                rootNode.put(MONITOR_STATUS + LABEL_SUFFER, RunStatusEnum.getEnumDesc(Integer.valueOf(jsonObject.get(MONITOR_STATUS).asText())));
            });
        }


    }
}
