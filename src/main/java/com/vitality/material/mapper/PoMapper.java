package com.vitality.material.mapper;

import com.vitality.material.entity.Po;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

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
    //插入预收货单，返回id，给预收货明细用
    int insertReturnId(Po po);

    //收货信息回传，获取数据
    HashMap<String,Object> selectReceiptInfo();

    void updateReceiptStatus(Integer id);
}
