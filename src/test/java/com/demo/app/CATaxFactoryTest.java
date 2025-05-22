package com.demo.app;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;

import com.demo.app.dto.request.ShoppingItem;
import com.demo.app.service.impl.CATaxFactory;
import com.demo.app.service.impl.FoodCategory;
import com.demo.app.utils.TaxCalUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CATaxFactoryTest {

	@InjectMocks
	private CATaxFactory caTaxFactory;

	@Mock
	private FoodCategory foodCategory;

	@Mock
	private ShoppingItem item;

	@BeforeEach
	public void setup() {
		caTaxFactory = new CATaxFactory(); // 构造函数会设置rate
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testProcess_ExemptItem_ReturnsZeroTax() {
		when(item.getItemName()).thenReturn("apple");
		when(foodCategory.isExempt("apple")).thenReturn(true);

		BigDecimal tax = caTaxFactory.process(item);

		assertEquals(BigDecimal.ZERO, tax);
	}

	@Test
	public void testProcess_NonExemptItem_ReturnsCalculatedTax() {
		when(item.getItemName()).thenReturn("laptop");
		when(foodCategory.isExempt("laptop")).thenReturn(false);

		// 假設 TaxCalUtil.process 正確運作，這裡用 mock static
		try (MockedStatic<TaxCalUtil> mocked = mockStatic(TaxCalUtil.class)) {
			BigDecimal expectedTax = new BigDecimal("9.75");
			mocked.when(() -> TaxCalUtil.process(item, caTaxFactory.getRate())).thenReturn(expectedTax);

			BigDecimal tax = caTaxFactory.process(item);

			assertEquals(expectedTax, tax);
			mocked.verify(() -> TaxCalUtil.process(item, caTaxFactory.getRate()), times(1));
		}
	}
}