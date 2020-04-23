package com.vitality.material.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author Jack J
 * @since 2020-04-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tbl_imp_po")
public class Po extends Model<Po> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    /**
     * 预收货清单编码
     */
    @TableField("prepareReceiptCode")
    private String prepareReceiptCode;

    /**
     * 接受交期
     */
    @TableField("offeredDate")
    private String offeredDate;

    /**
     * 供应商编码
     */
    @TableField("supplierCode")
    private String supplierCode;

    /**
     * 供应商名称
     */
    @TableField("supplierName")
    private String supplierName;

    /**
     * 工厂发票
     */
    @TableField("factoryInvoice")
    private String factoryInvoice;

    /**
     * 删除状态
     */
    @TableField("deleteStatus")
    private Integer deleteStatus;

    /**
     * 创建时间
     */
    @TableField("create_date")
    private Date createDate;

}
