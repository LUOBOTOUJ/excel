package com.vitality.material.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vitality.material.entity.InventoryMove;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 库存转移Mapper 接口
 * </p>
 *
 * @author Jack J
 * @since 2020-04-22
 */
public interface InventoryMoveMapper extends BaseMapper<InventoryMove> {
    /**
     * 
     * 
     * @author 江越天
     * @date 2020-05-13 17:58
     * @param 通过uuid更新状态，由于数据库表主键未定义成id，无法调用updateById()
     * @return boolean
     */
    boolean updateStatus(InventoryMove inventoryMove);
    HashMap<String,Object> selectReceiptPrint();

}
