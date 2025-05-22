package com.demo.app.controller;

import com.demo.app.dto.request.ShoppingInfo;
import com.demo.app.service.TaxCalculateService;
import com.demo.app.vo.ReceiptItemVO;
import com.demo.app.vo.ReceiptVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping
    public ReceiptVO calculateTaxByRequest(@RequestBody ShoppingInfo shoppingInfo) {
        return process(shoppingInfo);
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
        return process(info);
    }

    private ReceiptVO process(ShoppingInfo info) {
        ReceiptVO receiptVO = taxCalculateService.generateReceipt(info);
        System.out.printf("%-20s %7s %4s%n", "item", "price", "qty");
        for(ReceiptItemVO item : receiptVO.getItemList()) {
            System.out.printf(
                    "%-20s $%6.2f %4d%n",
                    item.getItem(),
                    item.getPrice(),
                    item.getQty()
            );
        }

        String formattedSubtotal = String.format("$%7.2f", receiptVO.getSubTotal());
        String formattedTax = String.format("$%7.2f", receiptVO.getTax());
        String formattedTotal = String.format("$%7.2f", receiptVO.getTotal());

        System.out.printf("%-24s %s%n", "subtotal:", formattedSubtotal);
        System.out.printf("%-24s %s%n", "tax:", formattedTax);
        System.out.printf("%-24s %s%n", "total:", formattedTotal);

        return receiptVO;
    }
}
