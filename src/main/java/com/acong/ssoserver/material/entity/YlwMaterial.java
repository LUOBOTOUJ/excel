package com.acong.ssoserver.material.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.awt.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
* <p>
    * 物料表
    * </p>
*
* @author Author
* @since 2020-04-14
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value = "ylw_material")
public class YlwMaterial implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.AUTO)
    private int id;

    private String appearDate;

    private String brand;

    private String category;

    private String color;

    private Integer deleteStatus;

    private Integer isBatchManagement;

    private String materialCode;

    private String materialBarcode;

    private String materialName;

    private String materialSeries;

    private String materialType;

    private String newProduct;

    private String pattern;

    private String placeOfMaterialion;

    private String producerCode;

    private String productSeason;

    private String purchaseMeasurementUnit;

    private String substitute;

    private String supplierId;

    private String supplierName;

    private String weightPerMeter;

    private String weightPerSquareMeter;

    private String width;

    private String yarnCount;

    private String createTime;

    private String updateTime;


}

