package com.alinesno.infra.common.web.adapter.plugins;

import com.alinesno.infra.data.integration.entity.TransEntity;
import com.alinesno.infra.data.integration.enums.RunResultEnum;
import com.alinesno.infra.data.integration.service.ITransRunHisService;
import com.alinesno.infra.data.integration.service.ITransService;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 转换的转换插件
 *
 * @author paul
 * @version 1.0.0
 */
@Component("transRunHisPlugin")
public class TransRunHisPlugin implements TranslatePlugin {
    @Autowired
    private ITransRunHisService transRunHisService;

    @Autowired
    private ITransService transService;

    private final String MONITORTRANSID = "monitorTransId";
    private final String ID = "id";
    private final String RUNSTATUS = "runStatus";
    private final String STARTTIME = "startTime";
    private final String ENDTIME = "endTime";
    private final String SC = "sc";

    @Override
    public void translate(ArrayNode node, TranslateCode convertCode) {

        //获取任务列表
        List<String> ids = this.extractIds(node, MONITORTRANSID);
        if(ids == null || ids.size() == 0) {
        	return ;
        }
        
        List<TransEntity> list = this.transService.findByIds(ids);
        Map<String, TransEntity> transEntityMap = this.toEntityMap(list);

        //转换逻辑
        node.forEach(jsonObject -> {
            ObjectNode rootNode = (ObjectNode) jsonObject;
            //从返回的列表中获取任务id
            String monitorTransId = jsonObject.get(MONITORTRANSID).asText();

            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Date  aStarTime = null;
            Date  aEndTime = null;
            try {
                aStarTime = sdf.parse(jsonObject.get(STARTTIME).asText());
                aEndTime = sdf.parse(jsonObject.get(ENDTIME).asText());
            } catch (ParseException e) {
                throw new RuntimeException( e);
            }
            long l = aEndTime.getTime() - aStarTime.getTime();
            long day = l / (24 * 60 * 60 * 1000);
            long hour = (l / (60 * 60 * 1000) - day * 24);
            long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
            long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
            rootNode.put(SC + LABEL_SUFFER,  String.valueOf(s));
            TransEntity entity = transEntityMap.get(monitorTransId);
            if (entity != null) {
                //设置返回值
                rootNode.put(MONITORTRANSID + LABEL_SUFFER,  entity.getName());
                rootNode.put(RUNSTATUS + LABEL_SUFFER, RunResultEnum.getEnumDesc(Integer.valueOf(jsonObject.get(RUNSTATUS).asText())));

            }
        });
    }
}
