package com.demo.app;

import com.demo.app.dto.request.ShoppingItem;
import com.demo.app.service.impl.ClotheCategory;
import com.demo.app.service.impl.FoodCategory;
import com.demo.app.service.impl.NYTaxFactory;
import com.demo.app.utils.TaxCalUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NYTaxFactoryTest {

    @InjectMocks
    private NYTaxFactory nyTaxFactory;

    @Mock
    private FoodCategory foodCategory;

    @Mock
    private ClotheCategory clotheCategory;

    @Mock
    private ShoppingItem item;

    @BeforeEach
    public void setUp() {
        nyTaxFactory = new NYTaxFactory(); // 確保 constructor 設定 rate
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcess_FoodExempt_ReturnsZero() {
        when(item.getItemName()).thenReturn("apple");
        when(foodCategory.isExempt("apple")).thenReturn(true);

        BigDecimal tax = nyTaxFactory.process(item);

        assertEquals(BigDecimal.ZERO, tax);
    }

    @Test
    public void testProcess_ClothingExempt_ReturnsZero() {
        when(item.getItemName()).thenReturn("shirt");
        when(foodCategory.isExempt("shirt")).thenReturn(false);
        when(clotheCategory.isExempt("shirt")).thenReturn(true);

        BigDecimal tax = nyTaxFactory.process(item);

        assertEquals(BigDecimal.ZERO, tax);
    }

    @Test
    public void testProcess_NonExemptItem_ReturnsCalculatedTax() throws Exception {
        when(item.getItemName()).thenReturn("laptop");
        when(foodCategory.isExempt("laptop")).thenReturn(false);
        when(clotheCategory.isExempt("laptop")).thenReturn(false);

        try (MockedStatic<TaxCalUtil> mocked = mockStatic(TaxCalUtil.class)) {
            BigDecimal expectedTax = new BigDecimal("10.00");
            mocked.when(() -> TaxCalUtil.process(item, nyTaxFactory.getRate())).thenReturn(expectedTax);

            BigDecimal tax = nyTaxFactory.process(item);

            assertEquals(expectedTax, tax);
            mocked.verify(() -> TaxCalUtil.process(item, nyTaxFactory.getRate()), times(1));
        }
    }
}
