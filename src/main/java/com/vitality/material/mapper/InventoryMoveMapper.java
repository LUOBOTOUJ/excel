package com.vitality.material.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vitality.material.entity.InventoryMove;

/**
 * <p>
 * 库存转移Mapper 接口
 * </p>
 *
 * @author Jack J
 * @since 2020-04-22
 */
public interface InventoryMoveMapper extends BaseMapper<InventoryMove> {
    void updateStatus(InventoryMove inventoryMove);
}
