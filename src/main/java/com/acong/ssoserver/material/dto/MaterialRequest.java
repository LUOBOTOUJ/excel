package com.acong.ssoserver.material.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel("物料管理")
public class MaterialRequest {

    @ApiModelProperty("当前页")
    private Integer currentPage;

    @ApiModelProperty("每页显示数量")
    private Integer num;

    @ApiModelProperty("测试")
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

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
