package com.demo.app.service.impl;

import com.demo.app.dto.request.ShoppingItem;
import com.demo.app.service.TaxFactory;
import com.demo.app.utils.TaxCalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CATaxFactory extends TaxFactory {
    @Autowired
    private FoodCategory foodCategory;

    public CATaxFactory() {
        this.rate = new BigDecimal("9.75").multiply(new BigDecimal("0.01"));
    }

    @Override
    public BigDecimal process(ShoppingItem item) {
        return foodCategory.isExempt(item.getItemName()) ? BigDecimal.ZERO : TaxCalUtil.process(item, this.rate);
    }
}
