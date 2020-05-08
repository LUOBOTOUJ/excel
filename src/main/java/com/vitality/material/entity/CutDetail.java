package com.vitality.material.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CutDetail {
    private String batchNo;

    private BigDecimal quantity;

    private String inventory;

    private String subInventory;

    private String uuid;
}
