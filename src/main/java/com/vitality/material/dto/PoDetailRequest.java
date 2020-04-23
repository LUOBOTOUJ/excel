package com.vitality.material.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.annotations.Insert;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

@Data
@ApiModel("预收货单明细信息接收类")
public class PoDetailRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "销售订单ID", dataType="String")
    //@NotBlank(message = "请输入销售订单ID", groups = {Insert.class})
    private String salesOrderId;

    @ApiModelProperty(value = "采购类型，需求来源", dataType="String")
    //@NotBlank(message = "请输入采购类型，需求来源", groups = {Insert.class})
    private String purchaseType;

    @ApiModelProperty(value = "物料编码", dataType="String")
    @NotBlank(message = "请输入物料编码", groups = {Insert.class})
    private String materialCode;

    @ApiModelProperty(value = "物料名称", dataType="String")
    //@NotBlank(message = "请输入物料名称", groups = {Insert.class})
    private String materialName;

    @ApiModelProperty(value = "产品编码", dataType="String")
    //@NotBlank(message = "请输入产品编码", groups = {Insert.class})
    private String productCode;

    @ApiModelProperty(value = "产品名称", dataType="String")
    //@NotBlank(message = "请输入产品名称", groups = {Insert.class})
    private String productName;

    @ApiModelProperty(value = "工厂调拨单创建日期", dataType="Date")
    //@NotNull(message = "请选择工厂调拨单创建日期", groups = {Insert.class})
    private Date factoryAllocationDate;

    @ApiModelProperty(value = "工厂调拨单编码", dataType="String")
    //@NotBlank(message = "请输入工厂调拨单编码", groups = {Insert.class})
    private String factoryAllocationCode;

    @ApiModelProperty(value = "包装规则（米/包）", dataType="String")
    //@NotBlank(message = "请输入包装规则（米/包）", groups = {Insert.class})
    private String packSpecification;

    @ApiModelProperty(value = "实际到货米数", dataType="BigDecimal")
    @NotNull(message = "请选择实际到货米数", groups = {Insert.class})
    private BigDecimal realityNumber;

    @ApiModelProperty(value = "确认书编号", dataType="String")
    //@NotBlank(message = "请输入确认书编号", groups = {Insert.class})
    private String confirmingCode;

    @ApiModelProperty(value = "仓位（逻辑仓）", dataType="String")
    //@NotBlank(message = "请输入仓位（逻辑仓）", groups = {Insert.class})
    private String inventory;

    @ApiModelProperty(value = "库位", dataType="String")
    //@NotBlank(message = "请输入库位", groups = {Insert.class})
    private String subInventory;

    @ApiModelProperty(value = "批次号", dataType="String")
    //@NotBlank(message = "请输入批次号", groups = {Insert.class})
    private String batchNumber;

    @ApiModelProperty(value = "批次发货数量", dataType="BigDecimal")
    // @NotNull(message = "请选择批次发货数量", groups = {Insert.class})
    private BigDecimal batchQuantity;
}
