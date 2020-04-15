package com.acong.ssoserver.material.mapper;

import com.acong.ssoserver.material.entity.YlwMaterial;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* <p>
    * 物料表 Mapper 接口
    * </p>
*
* @author Author
* @since 2020-04-14
*/
@Repository(value = "YlwMaterialMapper")
public interface YlwMaterialMapper extends BaseMapper<YlwMaterial> {

    }

