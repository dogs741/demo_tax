package com.demo.app.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingItem implements Serializable {
    @NotNull
    private String itemName;
    @NotNull
    private Integer quantity;
    @NotNull
    private BigDecimal price;
}
