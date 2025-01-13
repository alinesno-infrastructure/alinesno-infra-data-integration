package com.alinesno.infra.data.integration.mapper;

import com.alinesno.infra.common.facade.mapper.repository.IBaseMapper;
import com.alinesno.infra.data.integration.dto.JobDto;
import com.alinesno.infra.data.integration.entity.JobEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 【请填写功能名称】Mapper接口
 *
 * @author paul
 * @version 1.0.0
 */
@Repository
public interface JobMapper extends IBaseMapper<JobEntity>{
    /**
     * 返回任务类型,JobDto
     * @return
     */
    public List<JobDto> getCategoryName();
}
