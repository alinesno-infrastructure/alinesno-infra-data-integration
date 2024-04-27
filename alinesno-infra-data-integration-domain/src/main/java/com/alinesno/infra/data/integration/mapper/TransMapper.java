package com.alinesno.infra.data.integration.mapper;

import com.alinesno.infra.common.facade.mapper.repository.IBaseMapper;
import com.alinesno.infra.data.integration.dto.TransDto;
import com.alinesno.infra.data.integration.entity.TransEntity;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * 【请填写功能名称】Mapper接口
 *
 * @author paul
 * @date 2024年3月10日
 */
@Repository
public interface TransMapper extends IBaseMapper<TransEntity> {
    /**
     * 获取trans以及任务分类的数据，用TransDto接收
     * @return
     */
    public List<TransDto> getManagerList();
}
