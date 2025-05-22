package com.demo.app.vo;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ReceiptItemVO {
    private String item;
    private BigDecimal price;
    private Integer qty;
}
