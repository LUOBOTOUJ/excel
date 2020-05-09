package com.vitality.material.dto;

import java.sql.Date;
import java.io.Serializable;
import java.util.ArrayList;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Data;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 *  admin 接收类
 * </p>
 *
 * @author Jack J
 * @since 2020-04-21
 */


@Data
@ApiModel(" 预收货单主信息接收类")
public class PoRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "预收货清单编码", dataType="String")
    @NotBlank(message = "请输入预收货清单编码", groups = {Insert.class,Update.class, Delete.class})
    private String prepareReceiptCode;


    @ApiModelProperty(value = "供应商编码", dataType="String")
    //@NotBlank(message = "请输入供应商编码", groups = {Insert.class})
    private String supplierCode;

    @ApiModelProperty(value = "供应商名称", dataType="String")
    //@NotBlank(message = "请输入供应商名称", groups = {Insert.class})
    private String supplierName;

    @ApiModelProperty(value = "工厂发票", dataType="String")
    //@NotBlank(message = "请输入工厂发票", groups = {Insert.class})
    private String factoryInvoice;

    @ApiModelProperty(value = "删除状态",dataType = "Integer")
    @NotNull(message = "请选择删除状态",groups = {Insert.class})
    private Integer deleteStatus;

    @ApiModelProperty(value = "创建时间", dataType="Date")
    private Date createDate;

    @ApiModelProperty(value = "预收货单明细",dataType = "List")
    @NotNull(message = "请选择预收货单明细",groups = {Insert.class})
    private ArrayList<PoDetailRequest> poDetailRequests;
}
