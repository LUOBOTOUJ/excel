package com.vitality.material.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

@Data
@TableName("tbl_inf_exp_inv_movement")
public class InventoryMove extends Model<InventoryMove> {
    private static final long serialVersionUID = 1L;


    @TableField("uuID")
    private Integer uuid;

    @TableField("moveDate")
    private Date moveDate;

    @TableField("from_inventory")
    private String fromInventory;

    @TableField("from_sub_inventory")
    private String fromSubInv;

    @TableField("to_inventory")
    private String toInventory;

    @TableField("to_sub_inventory")
    private String toSubInv;

    @TableField("material_code")
    private String materialCode;

    @TableField("batch_number")
    private String batchNumber;

    @TableField("move_quantity")
    private BigDecimal moveQuantity;

    @TableField("move_user")
    private String moveUser;

    @TableField("status")
    private String status;

    @TableField("updateDate")
    private java.util.Date updateDate;

    @TableField("updateUser")
    private String updateUser;



}
