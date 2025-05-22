package com.demo.app.vo;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class ReceiptVO {
    private List<ReceiptItemVO> itemList;
    private BigDecimal subTotal;
    private BigDecimal tax;
    private BigDecimal total;
}
