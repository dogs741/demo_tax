package com.demo.app.controller;

import com.demo.app.dto.request.ShoppingInfo;
import com.demo.app.service.TaxCalculateService;
import com.demo.app.vo.ReceiptVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;

@RestController
@RequestMapping("/tax")
public class TaxController {

    @Autowired
    TaxCalculateService taxCalculateService;

    @GetMapping("/use1")
    public ReceiptVO calculateTax1() {
        return calculateTax("useCase1.yaml");
    }

    @GetMapping("/use2")
    public ReceiptVO calculateTax2() {
        return calculateTax("useCase2.yaml");
    }

    @GetMapping("/use3")
    public ReceiptVO calculateTax3() {
        return calculateTax("useCase3.yaml");
    }

    private ReceiptVO calculateTax(String fileName) {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(fileName);
        if (is == null) {
            System.err.println("找不到 config.yaml");
            return null;
        }

        Constructor constructor = new Constructor(ShoppingInfo.class, new LoaderOptions());
        Yaml yaml = new Yaml(constructor);

        ShoppingInfo info = yaml.load(is);
        return taxCalculateService.generateReceipt(info);
    }
}
