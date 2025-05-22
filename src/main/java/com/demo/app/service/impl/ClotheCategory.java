package com.demo.app.service.impl;

import com.demo.app.service.Category;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Component
public class ClotheCategory implements Category {
    private final Set<String> clotheCategory = Set.of(
            "BLOUSE",
            "SHIRT",
            "JUMPER",
            "SUIT",
            "BUSINESS SUIT",
            "ONE PIECE SUIT",
            "UNIFORMS",
            "APPAREL",
            "GARMENT",
            "HOODIE",
            "VEST",
            "CAMISOLE",
            "TANK TOP",
            "POLO SHIRT",
            "SWEATSHIRT",
            "SWEATER",
            "CARDIGAN",
            "PULLOVER",
            "JERSEY",
            "HOSIERY",
            "JACKET",
            "LEATHER JACKET",
            "TRENCH COAT",
            "COAT");

    @Override
    public boolean isExempt(String name) {
        return clotheCategory.contains(name.toUpperCase());
    }

}
