package com.vitality.material.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

@Data
@TableName("inf_exp_inv_adjustment")
public class InventoryAdjust {
    private static final long serialVersionUID = 1L;
    @TableField("id")
    private Integer id;

    @TableField("adjustDate")
    private Date adjustDate;

    @TableField("Inventory")
    private String Inventory;

    @TableField("SubInventory")
    private String SubInv;

    @TableField("materialCode")
    private String materialCode;

    @TableField("batchNumber")
    private String batchNumber;

    @TableField("hjBatchNumber")
    private String hjBatchNumber;

    @TableField("adjustQuantity")
    private BigDecimal adjustQuantity;

    @TableField("adjustUser")
    private String adjustUser;

    @TableField("status")
    private String status;

    @TableField("updateDate")
    private java.util.Date updateDate;

    @TableField("updateUser")
    private String updateUser;
}
