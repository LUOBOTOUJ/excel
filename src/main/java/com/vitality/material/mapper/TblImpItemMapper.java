package com.vitality.material.mapper;

import com.vitality.material.entity.YlwMaterial;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;


/**
* <p>
    * 物料表 Mapper 接口
    * </p>
*
* @author Author
* @since 2020-04-14
*/
@Repository(value = "TblImpItemMapper")
public interface TblImpItemMapper extends BaseMapper<YlwMaterial> {
    //int insertReturnId(YlwMaterial material);
    }

