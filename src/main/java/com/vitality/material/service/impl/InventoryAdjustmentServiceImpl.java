package com.vitality.material.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vitality.material.entity.InventoryAdjust;
import com.vitality.material.entity.InventoryMove;
import com.vitality.material.mapper.InventoryAdjustmentMapper;
import com.vitality.material.mapper.InventoryMoveMapper;
import com.vitality.material.service.IInventoryAdjustmentService;
import com.vitality.material.service.IInventoryMoveService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jack J
 * @since 2020-04-21
 */
@Service
public class InventoryAdjustmentServiceImpl extends ServiceImpl<InventoryAdjustmentMapper, InventoryAdjust> implements IInventoryAdjustmentService {

}
