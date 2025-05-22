package com.demo.app.service;

import com.demo.app.dto.request.ShoppingItem;

import java.math.BigDecimal;

public abstract class TaxFactory {
    protected BigDecimal rate = null;
    public abstract BigDecimal process(ShoppingItem item);
    public BigDecimal getRate() {
        return rate;
    }
}
