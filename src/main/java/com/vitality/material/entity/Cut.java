package com.vitality.material.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("tbl_inf_exp_cut")
public class Cut {
    private static final long serialVersionUID = 1L;

    private Integer expCutTransId;

    private String whId;

    private String itemNumber;

    private String fromInventory;

    private String fromSubInventory;

    private String sourceLotNumber;

    private BigDecimal sourceQty;

    private String newLotNumber;

    private BigDecimal newQty;

    private String newInventory;

    private String newSubInventory;

    private String status;

    private Date createDate;

    private String createUser;

    private Date updateDate;

    private String updateBy;
}
