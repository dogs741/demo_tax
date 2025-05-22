package com.demo.app.service.impl;

import com.demo.app.dto.request.ShoppingInfo;
import com.demo.app.dto.request.ShoppingItem;
import com.demo.app.enums.Location;
import com.demo.app.service.TaxCalculateService;
import com.demo.app.service.TaxFactory;
import com.demo.app.vo.ReceiptItemVO;
import com.demo.app.vo.ReceiptVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaxCalculateServiceImpl implements TaxCalculateService {

    private TaxFactoryCreator taxFactoryCreator;

    @Autowired
    public TaxCalculateServiceImpl(TaxFactoryCreator taxFactoryCreator) {
        this.taxFactoryCreator = taxFactoryCreator;
    }

    public ReceiptVO generateReceipt(ShoppingInfo info) {
        Location location = info.getLocation();
        TaxFactory taxFactory = taxFactoryCreator.process(location);

        BigDecimal subTotal = BigDecimal.ZERO;
        BigDecimal tax = BigDecimal.ZERO;
        BigDecimal total = BigDecimal.ZERO;
        List<ShoppingItem> itemList = info.getItemList();
        List<ReceiptItemVO> receiptItemList = new ArrayList<>();

        for (ShoppingItem shoppingItem : itemList) {
            receiptItemList.add(ReceiptItemVO.builder().item(shoppingItem.getItemName()).qty(shoppingItem.getQuantity()).price(shoppingItem.getPrice()).build());
            subTotal = subTotal.add(new BigDecimal(shoppingItem.getQuantity()).multiply(shoppingItem.getPrice()));
            tax = tax.add(taxFactory.process(shoppingItem));
        }

        return ReceiptVO.builder().itemList(receiptItemList).subTotal(subTotal).tax(tax).total(subTotal.add(tax)).build();
    }
}
