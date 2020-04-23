package com.vitality.material.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vitality.material.entity.So;

/**
 * <p>
 * 销售单表 Mapper 接口
 * </p>
 *
 * @author Jack J
 * @since 2020-04-22
 */
public interface SoMapper extends BaseMapper<So> {
    int insertReturnId(So so);

}
