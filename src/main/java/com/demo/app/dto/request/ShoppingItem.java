package com.demo.app.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingItem implements Serializable {
    private String itemName;
    private Integer quantity;
    private BigDecimal prices;
}
