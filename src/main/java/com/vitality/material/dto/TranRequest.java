package com.vitality.material.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.annotations.Insert;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;

/**
 * <p>
 *  admin 接收类
 * </p>
 *
 * @author Jack J
 * @since 2020-04-23
 */


@Data
@ApiModel(value="TranRequest", description=" 后台接受类")
public class TranRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("确认书编码")
    //@NotBlank(message = "请输入确认书编码", groups = {Insert.class})
    private String confirmNumbers;

    @ApiModelProperty("入仓仓位")
    //@NotBlank(message = "请输入入仓仓位", groups = {Insert.class})
    private String inventory;

    @ApiModelProperty("入仓库位")
    //@NotBlank(message = "请输入入仓库位", groups = {Insert.class})
    private String subInventory;

    @ApiModelProperty("库存类型（物料，产品）")
    private String inventoryElementType;

    @ApiModelProperty("物料编码")
    //@NotBlank(message = "请输入物料编码", groups = {Insert.class})
    private String materialCode;

    @ApiModelProperty("米数")
    //@NotBlank(message = "请输入米数", groups = {Insert.class})
    private String meterQuantity;

    @ApiModelProperty("出仓仓位")
    //@NotBlank(message = "请输入出仓仓位", groups = {Insert.class})
    private String outInventory;

    @ApiModelProperty("出仓库位")
    //@NotBlank(message = "请输入出仓库位", groups = {Insert.class})
    private String outSubInventory;

    @ApiModelProperty("追溯数据uuid")
    private String recordUuid;

    @ApiModelProperty(value = "生效日期", dataType="String")
    @NotBlank(message = "请输入生效日期", groups = {Insert.class})
    private String validDate;

    @ApiModelProperty(value = "逻辑删除标识", dataType="Integer")
    @NotNull(message = "请选择逻辑删除标识", groups = {Insert.class})
    private Integer deleteStatus;

    @ApiModelProperty(value = "创建日期", dataType="Date")
    private Date createDate;
}
