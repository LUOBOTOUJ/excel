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
 * 库存预留表
 * </p>
 *
 * @author Jack J
 * @since 2020-04-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tbl_imp_reserve")
public class Reserve extends Model<Reserve> {

    private static final long serialVersionUID = 1L;

    /**
     * 预留单编码
     */
    @TableField("reserveId")
    private String reserveId;

    /**
     * 预留状态Reserve – 预留
Release – 解除
     */
    @TableField("reserveStatus")
    private String reserveStatus;

    /**
     * 销售订单编号
     */
    @TableField("saleOrderId")
    private String saleOrderId;

    /**
     * 预留开始时间
     */
    @TableField("reserveBeginTimreserveBeginTime")
    private String reserveBeginTimreserveBeginTime;

    /**
     * 预留结束时间
     */
    @TableField("reserveEndTime")
    private String reserveEndTime;

    /**
     * 预留仓
     */
    @TableField("reserveInventory")
    private String reserveInventory;

    /**
     * 预留仓位
     */
    @TableField("subInventory")
    private String subInventory;

    /**
     * 预留数量
     */
    @TableField("reserveQuantity")
    private BigDecimal reserveQuantity;

    /**
     * 物料编码
     */
    @TableField("materialCode")
    private String materialCode;

    /**
     * 产品编码
     */
    @TableField("productCode")
    private String productCode;

    /**
     * 预留批次信息
     */
    @TableField("reserveBatch")
    private String reserveBatch;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 删除状态
     */
    @TableField("deleteStatus")
    private Integer deleteStatus;

    /**
     * 创建时间
     */
    @TableField("createDate")
    private Date createDate;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
