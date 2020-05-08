package com.vitality.material.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("inf_exp_inv_adjustment")
public class InventoryAdjust {
    private static final long serialVersionUID = 1L;
    @TableField("uuID")
    private Integer uuid;

    @TableField("adjustDate")
    private Date adjustDate;

    @TableField("Inventory")
    private String Inventory;

    @TableField("SubInventory")
    private String SubInv;

    @TableField("materialCode")
    private String materialCode;

    @TableField("batchNumber")
    private String uselessNumber;

    @TableField("hjBatchNumber")
    private String batchNumber;

    @TableField("adjustQuantity")
    private BigDecimal moveQuantity;

    @TableField("adjustUser")
    private String moveUser;

    @TableField("status")
    private String status;

    @TableField("updateDate")
    private Date updateDate;

    @TableField("updateUser")
    private String updateUser;
}
