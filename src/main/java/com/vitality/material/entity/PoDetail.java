package com.vitality.material.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tbl_imp_po_detail")
public class PoDetail extends Model<PoDetail> {
    private static final long serialVersionUID = 1L;

    /**
     * 所属主信息id
     */
    @TableField("belong_id")
    private Integer belongId;
    /**
     * 销售订单ID
     */
    @TableField("sales_order_id")
    private String salesOrderId;

    /**
     * 采购类型，需求来源
     */
    @TableField("purchase_type")
    private String purchaseType;

    /**
     * 物料编码
     */
    @TableField("material_code")
    private String materialCode;

    /**
     * 物料名称
     */
    @TableField("material_name")
    private String materialName;

    /**
     * 产品编码
     */
    @TableField("product_code")
    private String productCode;

    /**
     * 产品名称
     */
    @TableField("product_name")
    private String productName;

    /**
     * 工厂调拨单创建日期
     */
    @TableField("factory_allocation_date")
    private Date factoryAllocationDate;

    /**
     * 工厂调拨单编码
     */
    @TableField("factory_allocation_code")
    private String factoryAllocationCode;

    /**
     * 包装规则（米/包）
     */
    @TableField("pack_specification")
    private String packSpecification;

    /**
     * 实际到货米数
     */
    @TableField("reality_number")
    private BigDecimal realityNumber;

    /**
     * 确认书编号
     */
    @TableField("confirming_code")
    private String confirmingCode;

    /**
     * 仓位（逻辑仓）
     */
    @TableField("inventory")
    private String inventory;

    /**
     * 库位
     */
    @TableField("sub_inventory")
    private String subInventory;

    /**
     * 批次号
     */
    @TableField("batch_number")
    private String batchNumber;

    /**
     * 批次发货数量
     */
    @TableField("batch_quantity")
    private BigDecimal batchQuantity;

    /**
     * 创建时间
     */
    @TableField("create_date")
    private Date createDate;
}
