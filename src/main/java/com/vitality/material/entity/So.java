package com.vitality.material.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 销售单表
 * </p>
 *
 * @author Jack J
 * @since 2020-04-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tbl_imp_so")
public class So extends Model<So> {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    @TableField("salesOrderID")
    private String salesOrderID;

    @TableField("saleType")
    private String saleType;

    @TableField("businessType")
    private String businessType;

    @TableField("saleChanceCode")
    private String saleChanceCode;

    @TableField("customerCode")
    private String customerCode;

    @TableField("customerName")
    private String customerName;

    @TableField("customerContactName")
    private String customerContactName;

    @TableField("customerContactTitle")
    private String customerContactTitle;

    @TableField("customerContactPhone")
    private String customerContactPhone;

    @TableField("customerContactOfficeTel")
    private String customerContactOfficeTel;

    @TableField("customerContactFixedTel")
    private String customerContactFixedTel;

    @TableField("customerContactFax")
    private String customerContactFax;

    @TableField("customerContactEmail")
    private String customerContactEmail;

    @TableField("customerAddressCountry")
    private String customerAddressCountry;

    @TableField("customerAddressProvince")
    private String customerAddressProvince;

    @TableField("customerAddressCity")
    private String customerAddressCity;

    @TableField("customerAddressDistrict")
    private String customerAddressDistrict;

    @TableField("customerAddressDetails")
    private String customerAddressDetails;

    @TableField("customerAddressPostCode")
    private String customerAddressPostCode;

    @TableField("customerAddressRemarks")
    private String customerAddressRemarks;

    @TableField("orderStatus")
    private String orderStatus;

    @TableField("orderTime")
    private String orderTime;

    @TableField("lastDeliveryDate")
    private String lastDeliveryDate;

    @TableField("payType")
    private String payType;

    @TableField("currency")
    private String currency;

    @TableField("exchangeRate")
    private String exchangeRate;

    @TableField("needInvoice")
    private Integer needInvoice;

    @TableField("invoiceTitle")
    private String invoiceTitle;

    @TableField("invoiceContactName")
    private String invoiceContactName;

    @TableField("invoiceContactTitle")
    private String invoiceContactTitle;

    @TableField("invoiceContactPhone")
    private String invoiceContactPhone;

    @TableField("invoiceContactOfficeTel")
    private String invoiceContactOfficeTel;

    @TableField("invoiceContactFixedTel")
    private String invoiceContactFixedTel;

    @TableField("invoiceContactFax")
    private String invoiceContactFax;

    @TableField("invoiceContactEmail")
    private String invoiceContactEmail;

    @TableField("invoiceAddressCountry")
    private String invoiceAddressCountry;

    @TableField("invoiceAddressProvince")
    private String invoiceAddressProvince;

    @TableField("invoiceAddressCity")
    private String invoiceAddressCity;

    @TableField("invoiceAddressDistrict")
    private String invoiceAddressDistrict;

    @TableField("invoiceAddressDetails")
    private String invoiceAddressDetails;

    @TableField("invoiceAddressPostCode")
    private String invoiceAddressPostCode;

    @TableField("invoiceAddressRemarks")
    private String invoiceAddressRemarks;

    @TableField("channel")
    private String channel;

    @TableField("totalAmountForeign")
    private BigDecimal totalAmountForeign;

    @TableField("remarks")
    private String remarks;

    @TableField("totalAmountRmb")
    private BigDecimal totalAmountRmb;

    @TableField("deleteStatus")
    private Integer deleteStatus;

    @TableField("create_date")
    private Date createDate;


}
