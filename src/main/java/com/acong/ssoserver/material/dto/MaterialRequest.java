package com.acong.ssoserver.material.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel("物料管理")
public class MaterialRequest {
    @ApiModelProperty("id")
    private int id;

    @ApiModelProperty("当前页,起始值1")
    private Integer currentPage;

    @ApiModelProperty("每页显示数量")
    private Integer num;

    @ApiModelProperty("上架日期")
    private String appearDate;

    @ApiModelProperty("物料品牌")
    private String brand;

    @ApiModelProperty("品类")
    private String category;

    @ApiModelProperty("颜色")
    private String color;

    @ApiModelProperty("删除状态，0未删除，1删除")
    private Integer deleteStatus;

    @ApiModelProperty("是否批次管理")
    private Integer isBatchManagement;

    @ApiModelProperty("物料编码")
    private String materialCode;

    @ApiModelProperty("物料条形码")
    private String materialBarcode;

    @ApiModelProperty("物料名称")
    private String materialName;

    @ApiModelProperty("系列")
    private String materialSeries;

    @ApiModelProperty("物料类型")
    private String materialType;

    @ApiModelProperty("是否新品")
    private String newProduct;

    @ApiModelProperty("花型")
    private String pattern;

    @ApiModelProperty("产地")
    private String placeOfMaterialion;

    @ApiModelProperty("制造商编码")
    private String producerCode;

    @ApiModelProperty("产品季")
    private String productSeason;

    @ApiModelProperty("采购计量单位")
    private String purchaseMeasurementUnit;

    @ApiModelProperty("替代品")
    private String substitute;

    @ApiModelProperty("供应商编码")
    private String supplierId;

    @ApiModelProperty("供应商名称")
    private String supplierName;

    @ApiModelProperty("延米克重")
    private String weightPerMeter;

    @ApiModelProperty("每平米克重")
    private String weightPerSquareMeter;

    @ApiModelProperty("幅宽")
    private String width;

    @ApiModelProperty("纱支")
    private String yarnCount;

    @ApiModelProperty("创建时间")
    private String  createTime;

    @ApiModelProperty("更新时间")
    private String  updateTime;

}
