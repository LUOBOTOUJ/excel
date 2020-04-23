package com.vitality.material.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author Jack J
 * @since 2020-04-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tbl_imp_tran")
public class Tran extends Model<Tran> {

    private static final long serialVersionUID = 1L;

    /**
     * 确认书编码
     */
    @TableField("confirmNumbers")
    private String confirmNumbers;

    /**
     * 入仓仓位
     */
    @TableField("inventory")
    private String inventory;

    /**
     * 入仓库位
     */
    @TableField("subInventory")
    private String subInventory;

    /**
     * 库存类型（物料，产品
     */
    @TableField("inventoryElementType")
    private String inventoryElementType;

    /**
     * 物料编码
     */
    @TableField("materialCode")
    private String materialCode;

    /**
     * 米数
     */
    @TableField("meterQuantity")
    private String meterQuantity;

    /**
     * 出仓仓位
     */
    @TableField("outInventory")
    private String outInventory;

    /**
     * 出仓库位
     */
    @TableField("outSubInventory")
    private String outSubInventory;

    /**
     * 追溯数据uuid
     */
    @TableField("recordUuid")
    private String recordUuid;

    /**
     * 生效日期
     */
    @TableField("validDate")
    private String validDate;

    /**
     * 逻辑删除标识
     */
    @TableField("deleteStatus")
    private Integer deleteStatus;

    /**
     * 创建日期
     */
    @TableField("createDate")
    private Date createDate;

}
