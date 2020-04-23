package com.vitality.material.mapper;

import com.vitality.material.entity.Po;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Jack J
 * @since 2020-04-21
 */
@Repository(value = "PoMapper")
public interface PoMapper extends BaseMapper<Po> {
    int insertReturnId(Po po);
}
