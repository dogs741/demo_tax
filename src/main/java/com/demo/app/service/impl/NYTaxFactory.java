package com.demo.app.service.impl;

import com.demo.app.dto.request.ShoppingItem;
import com.demo.app.service.TaxFactory;
import com.demo.app.utils.TaxCalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class NYTaxFactory extends TaxFactory {
    @Autowired
    private FoodCategory foodCategory;
    @Autowired
    private ClotheCategory clotheCategory;

    public NYTaxFactory() {
        this.rate = new BigDecimal("8.875").multiply(new BigDecimal("0.01"));
    }

    @Override
    public BigDecimal process(ShoppingItem item) {
        return (foodCategory.isExempt(item.getItemName()) || clotheCategory.isExempt(item.getItemName())) ?
                BigDecimal.ZERO : TaxCalUtil.process(item, this.rate);
    }
}
