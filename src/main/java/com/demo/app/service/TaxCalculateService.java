package com.demo.app.service;

import com.demo.app.vo.ReceiptVO;
import com.demo.app.dto.request.ShoppingInfo;

public interface TaxCalculateService {
    ReceiptVO generateReceipt(ShoppingInfo info);
}
