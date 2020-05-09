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
import java.util.ArrayList;

/**
 * <p>
 * 销售单表 接收类
 * </p>
 *
 * @author Jack J
 * @since 2020-04-22
 */


@Data
@ApiModel("销售单表 主信息接受类")
public class SoRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "", dataType="String")
    //@NotBlank(message = "请输入", groups = {Insert.class})
    private String salesOrderID;

    @ApiModelProperty(value = "", dataType="String")
    //@NotBlank(message = "请输入", groups = {Insert.class})
    private String saleType;

    @ApiModelProperty(value = "", dataType="String")
    //@NotBlank(message = "请输入", groups = {Insert.class})
    private String businessType;

    @ApiModelProperty(value = "", dataType="String")
    //@NotBlank(message = "请输入", groups = {Insert.class})
    private String saleChanceCode;

    @ApiModelProperty(value = "", dataType="String")
    //@NotBlank(message = "请输入", groups = {Insert.class})
    private String customerCode;

    @ApiModelProperty(value = "", dataType="String")
    //@NotBlank(message = "请输入", groups = {Insert.class})
    private String customerName;

    @ApiModelProperty(value = "", dataType="String")
    //@NotBlank(message = "请输入", groups = {Insert.class})
    private String customerContactName;

    @ApiModelProperty(value = "", dataType="String")
    //@NotBlank(message = "请输入", groups = {Insert.class})
    private String customerContactTitle;

    @ApiModelProperty(value = "", dataType="String")
    //@NotBlank(message = "请输入", groups = {Insert.class})
    private String customerContactPhone;

    @ApiModelProperty(value = "", dataType="String")
    //@NotBlank(message = "请输入", groups = {Insert.class})
    private String customerContactOfficeTel;

    @ApiModelProperty(value = "", dataType="String")
    private String customerContactFixedTel;

    @ApiModelProperty(value = "", dataType="String")
    private String customerContactFax;

    @ApiModelProperty(value = "", dataType="String")
    private String customerContactEmail;

    @ApiModelProperty(value = "", dataType="String")
    private String customerAddressCountry;

    @ApiModelProperty(value = "", dataType="String")
    private String customerAddressProvince;

    @ApiModelProperty(value = "", dataType="String")
    private String customerAddressCity;

    @ApiModelProperty(value = "", dataType="String")
    private String customerAddressDistrict;

    @ApiModelProperty(value = "", dataType="String")
    private String customerAddressDetails;

    @ApiModelProperty(value = "", dataType="String")
    private String customerAddressPostCode;

    @ApiModelProperty(value = "", dataType="String")
    private String customerAddressRemarks;

    @ApiModelProperty(value = "", dataType="String")
    private String orderStatus;

    @ApiModelProperty(value = "", dataType="String")
    private String orderTime;

    @ApiModelProperty(value = "", dataType="String")
    private String lastDeliveryDate;

    @ApiModelProperty(value = "", dataType="String")
    private String payType;

    @ApiModelProperty(value = "", dataType="String")
    private String currency;

    @ApiModelProperty(value = "", dataType="String")
    private String exchangeRate;

    @ApiModelProperty(value = "", dataType="Integer")
    private Integer needInvoice;

    @ApiModelProperty(value = "", dataType="String")
    private String invoiceTitle;

    @ApiModelProperty(value = "", dataType="String")
    private String invoiceContactName;

    @ApiModelProperty(value = "", dataType="String")
    private String invoiceContactTitle;

    @ApiModelProperty(value = "", dataType="String")
    private String invoiceContactPhone;

    @ApiModelProperty(value = "", dataType="String")
    private String invoiceContactOfficeTel;

    @ApiModelProperty(value = "", dataType="String")
    private String invoiceContactFixedTel;

    @ApiModelProperty(value = "", dataType="String")
    private String invoiceContactFax;

    @ApiModelProperty(value = "", dataType="String")
    private String invoiceContactEmail;

    @ApiModelProperty(value = "", dataType="String")
    private String invoiceAddressCountry;

    @ApiModelProperty(value = "", dataType="String")
    private String invoiceAddressProvince;

    @ApiModelProperty(value = "", dataType="String")
    private String invoiceAddressCity;

    @ApiModelProperty(value = "", dataType="String")
    private String invoiceAddressDistrict;

    @ApiModelProperty(value = "", dataType="String")
    private String invoiceAddressDetails;

    @ApiModelProperty(value = "", dataType="String")
    private String invoiceAddressPostCode;

    @ApiModelProperty(value = "", dataType="String")
    private String invoiceAddressRemarks;

    @ApiModelProperty(value = "", dataType="String")
    private String channel;

    @ApiModelProperty(value = "", dataType="BigDecimal")
    private BigDecimal totalAmountForeign;

    @ApiModelProperty(value = "", dataType="String")
    private String remarks;

    @ApiModelProperty(value = "", dataType="BigDecimal")
    private BigDecimal totalAmountRmb;

    @ApiModelProperty(value = "", dataType="Integer")
    private Integer deleteStatus;

    @ApiModelProperty(value = "", dataType="Date")
    private Date createDate;

    @ApiModelProperty(value = "销售单明细",dataType = "List")
    @NotNull(message = "请选择销售单明细",groups = {Insert.class})
    private ArrayList<SoDetailRequest> soDetailRequests;

}
