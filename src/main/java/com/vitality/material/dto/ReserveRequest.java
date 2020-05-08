package com.vitality.material.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.annotations.Insert;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

/**
 * <p>
 * 库存预留表 admin 接收类
 * </p>
 *
 * @author Jack J
 * @since 2020-04-23
 */


@Data
@ApiModel(value="ReserveRequest", description="库存预留表 后台接受类")
public class ReserveRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("预留单编码")
    @NotBlank(message = "请输入预留单编码", groups = {Insert.class})
    private String reserveId;

    @ApiModelProperty("预留状态Reserve – 预留")
    private String reserveStatus;

    @ApiModelProperty("销售订单编号")
    //@NotNull(message = "请选择销售订单编号", groups = {Insert.class})
    private String saleOrderId;

    @ApiModelProperty("预留开始时间")
    //@NotBlank(message = "请输入预留开始时间", groups = {Insert.class})
    private String reserveBeginTimreserveBeginTime;

    @ApiModelProperty("预留结束时间")
    //@NotBlank(message = "请输入预留结束时间", groups = {Insert.class})
    private String reserveEndTime;

    @ApiModelProperty("预留仓")
    //@NotBlank(message = "请输入预留仓", groups = {Insert.class})
    private String reserveInventory;

    @ApiModelProperty("预留仓位")
    //@NotBlank(message = "请输入预留仓位", groups = {Insert.class})
    private String subInventory;

    @ApiModelProperty("预留数量")
    //@NotNull(message = "请选择预留数量", groups = {Insert.class})
    private BigDecimal reserveQuantity;

    @ApiModelProperty("物料编码")
    //@NotBlank(message = "请输入物料编码", groups = {Insert.class})
    private String materialCode;

    @ApiModelProperty("产品编码")
    //@NotBlank(message = "请输入产品编码", groups = {Insert.class})
    private String productCode;

    @ApiModelProperty( "预留批次信息")
    //@NotBlank(message = "请输入预留批次信息", groups = {Insert.class})
    private String reserveBatch;

    @ApiModelProperty("备注")
    //@NotBlank(message = "请输入备注", groups = {Insert.class})
    private String remark;

    @ApiModelProperty("删除状态")
    //@NotNull(message = "请选择删除状态", groups = {Insert.class})
    private Integer deleteStatus;

    @ApiModelProperty("创建时间")
    //@NotNull(message = "请选择创建时间", groups = {Insert.class})
    private Date createDate;


}
