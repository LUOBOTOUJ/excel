package com.vitality.material.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vitality.material.entity.Cut;
import com.vitality.material.entity.Po;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Jack J
 * @since 2020-04-21
 */
@Repository(value = "CutMapper")
public interface CutMapper extends BaseMapper<Cut> {
    void updateStatus(Cut cut);
}
