package com.alinesno.infra.common.web.adapter.plugins;

import com.alinesno.infra.data.integration.entity.CategoryEntity;
import com.alinesno.infra.data.integration.enums.LogLevelEnum;
import com.alinesno.infra.data.integration.enums.RunStatusEnum;
import com.alinesno.infra.data.integration.enums.RunTypeEnum;
import com.alinesno.infra.data.integration.service.ICategoryService;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


/**
 * 转换的转换插件
 *
 * @author paul
 * @version 1.0.0
 */
@Component("transPlugin")
public class TransPlugin implements TranslatePlugin {

    @Autowired
    private ICategoryService categoryService;

    private final String CATEGORY_ID = "categoryId";
    private final String CATEGORY_NAME = "categoryName";
    private final String LOG_LEVEL = "logLevel";
    private final String TYPE = "type";
    private final String STATUS = "status";

    @Override
    public void translate(ArrayNode node, TranslateCode convertCode) {
        if ( !node.isEmpty() ) {
            List<String> categoryIds = this.extractIds(node, CATEGORY_ID);
            if( categoryIds == null || categoryIds.size() == 0 ) {
                return ;
            }

            List<CategoryEntity> categoryList = categoryService.findByIds(categoryIds);
            Map<String, CategoryEntity> categoryMap = this.toEntityMap(categoryList);

            node.forEach(jsonObject -> {
                ObjectNode rootNode = (ObjectNode) jsonObject;
                String categoryId = jsonObject.get(CATEGORY_ID).asText();
                String logLevel = jsonObject.get(LOG_LEVEL).asText();
                String type = jsonObject.get(TYPE).asText();
                Integer status = Integer.valueOf(jsonObject.get(STATUS).asText());

                rootNode.put(LOG_LEVEL, LogLevelEnum.getEnumDesc(logLevel));
                rootNode.put(TYPE, RunTypeEnum.getEnumDesc(type));
                rootNode.put(STATUS+ LABEL_SUFFER, RunStatusEnum.getEnumDesc(status));

                //获取分类信息
                CategoryEntity categoryEntity = categoryMap.get(categoryId);
                if ( categoryEntity != null ) {
                    rootNode.put(CATEGORY_NAME, categoryEntity.getCategoryName());
                }
            });

        }

    }
}
