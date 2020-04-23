package com.vitality.material.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author Jack J
 * @since 2020-04-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tbl_imp_so_detail")
public class SoDetail extends Model<SoDetail> {

    private static final long serialVersionUID = 1L;

    @TableField("belong_id")
    private Integer belongId;

    @TableField("materialCode")
    private String materialCode;

    @TableField("productCode")
    private String productCode;

    @TableField("inventory")
    private String inventory;

    @TableField("subInventory")
    private String subInventory;

    @TableField("batchNumber")
    private String batchNumber;

    @TableField("quantity")
    private BigDecimal quantity;

    @TableField("remarks")
    private String remarks;

    @TableField("create_date")
    private Date createDate;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
