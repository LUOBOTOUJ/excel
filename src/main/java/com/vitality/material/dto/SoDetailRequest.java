package com.vitality.material.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

/**
 * <p>
 *  admin 接收类
 * </p>
 *
 * @author Jack J
 * @since 2020-04-22
 */


@Data
@ApiModel(" 销售单明细信息接受类")
public class SoDetailRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "物料编码", dataType="String")
    private String materialCode;

    @ApiModelProperty(value = "产品编码", dataType="String")
    private String productCode;

    @ApiModelProperty(value = "仓位（逻辑仓）", dataType="String")
    private String inventory;

    @ApiModelProperty(value = "库位", dataType="String")
    private String subInventory;

    @ApiModelProperty(value = "批次号", dataType="String")
    private String batchNumber;

    @ApiModelProperty(value = "数量", dataType="BigDecimal")
    private BigDecimal quantity;

    @ApiModelProperty(value = "明细备注信息", dataType="String")
    private String remarks;

    @ApiModelProperty(value = "", dataType="Date")
    private Date createDate;

}
