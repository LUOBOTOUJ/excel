package com.vitality.material.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("tbl_inf_exp_cut")
public class Cut {
    private static final long serialVersionUID = 1L;

    @TableField("id")
    private Integer id;

    @TableField("wh_id")
    private String whId;

    @TableField("item_number")
    private String itemNumber;

    @TableField("from_inventory")
    private String fromInventory;

    @TableField("from_sub_inventory")
    private String fromSubInventory;

    @TableField("source_lot_number")
    private String sourceLotNumber;

    @TableField("source_qty")
    private BigDecimal sourceQty;

    @TableField("new_lot_number")
    private String newLotNumber;

    @TableField("new_qty")
    private BigDecimal newQty;

    @TableField("new_inventory")
    private String newInventory;

    @TableField("new_sub_inventory")
    private String newSubInventory;

    @TableField("status")
    private String status;

    @TableField("create_date")
    private Date createDate;

    @TableField("create_user")
    private String createUser;

    @TableField("update_date")
    private Date updateDate;

    @TableField("update_by")
    private String updateBy;
}
