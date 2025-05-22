package com.demo.app;

import com.demo.app.dto.request.ShoppingInfo;
import com.demo.app.dto.request.ShoppingItem;
import com.demo.app.enums.Location;
import com.demo.app.service.TaxFactory;
import com.demo.app.service.impl.TaxCalculateServiceImpl;
import com.demo.app.service.impl.TaxFactoryCreator;
import com.demo.app.utils.TaxCalUtil;
import com.demo.app.vo.ReceiptVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TaxCalculateServiceImplTest {

	@Mock
	private TaxFactoryCreator taxFactoryCreator;

	@Mock
	private TaxFactory taxFactory;

	@InjectMocks
	private TaxCalculateServiceImpl taxCalculateService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testGenerateReceipt() {
		Location location = Location.NY; // 假設 Location 為普通類別
		ShoppingItem item1 = new ShoppingItem("Book", 2, new BigDecimal("12.49"));
		ShoppingItem item2 = new ShoppingItem("Music CD", 1, new BigDecimal("14.99"));
		ShoppingInfo shoppingInfo = new ShoppingInfo(location, Arrays.asList(item1, item2));

		BigDecimal rateNY = new BigDecimal("8.875").multiply(new BigDecimal("0.01"));
		BigDecimal item1Tax = TaxCalUtil.process(item1, rateNY);
		BigDecimal item2Tax = TaxCalUtil.process(item2, rateNY);

		when(taxFactoryCreator.process(location)).thenReturn(taxFactory);
		when(taxFactory.process(item1)).thenReturn(item1Tax);
		when(taxFactory.process(item2)).thenReturn(item2Tax);
		ReceiptVO receipt = taxCalculateService.generateReceipt(shoppingInfo);

		BigDecimal expectedSubTotal = new BigDecimal("39.97"); // (2*12.49 + 1*14.99)
		BigDecimal expectedTax = item1Tax.add(item2Tax);
		BigDecimal expectedTotal = expectedSubTotal.add(expectedTax);

		assertNotNull(receipt);
		assertEquals(2, receipt.getItemList().size());
		assertEquals(0, expectedSubTotal.compareTo(receipt.getSubTotal()));
		assertEquals(0, expectedTax.compareTo(receipt.getTax()));
		assertEquals(0, expectedTotal.compareTo(receipt.getTotal()));
	}
}
