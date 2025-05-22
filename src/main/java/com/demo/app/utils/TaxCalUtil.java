package com.demo.app.utils;

import com.demo.app.dto.request.ShoppingItem;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TaxCalUtil {
    public static BigDecimal process(ShoppingItem item, BigDecimal rate) {
        Integer quantity = item.getQuantity();
        BigDecimal price = item.getPrices();
        BigDecimal originTax = price.multiply(new BigDecimal(quantity)).multiply(rate);
        return originTax
                .divide(new BigDecimal("0.05"), 0, RoundingMode.UP)
                .multiply(new BigDecimal("0.05"))
                .setScale(2, RoundingMode.UP);

    }
}
