package com.acong.ssoserver.material.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MaterialResponse {

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
