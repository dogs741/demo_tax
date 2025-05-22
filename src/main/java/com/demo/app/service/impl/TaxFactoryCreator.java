package com.demo.app.service.impl;

import com.demo.app.enums.Location;
import com.demo.app.service.TaxFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaxFactoryCreator {
    @Autowired
    private CATaxFactory caTaxFactory;
    @Autowired
    private NYTaxFactory nyTaxFactory;

    public TaxFactory process(Location location) {
        return switch (location) {
            case NY -> nyTaxFactory;
            case CA -> caTaxFactory;
        };
    }
}
