package com.alinesno.infra.data.integration.service.impl;

import com.alinesno.infra.common.core.service.impl.IBaseServiceImpl;
import com.alinesno.infra.data.integration.entity.CategoryEntity;
import com.alinesno.infra.data.integration.mapper.CategoryMapper;
import com.alinesno.infra.data.integration.service.ICategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author paul
 * @version 1.0.0
 */
@Service
public class CategoryServiceImpl extends IBaseServiceImpl<CategoryEntity, CategoryMapper> implements ICategoryService {
    //日志记录
    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);
}
