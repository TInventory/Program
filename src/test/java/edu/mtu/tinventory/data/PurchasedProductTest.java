package edu.mtu.tinventory.data;
import org.junit.Test;

import static org.junit.Assert.*;

import java.math.BigDecimal;

public class PurchasedProductTest {

	@Test
	public void testDisplayPrices() {
		Product testProduct = new Product("123", "Test Product", "123.45");
		PurchasedProduct test = new PurchasedProduct(testProduct, 12, new BigDecimal("122.2"));
		assertEquals(test.getDisplayUnitPrice(), "$122.20");
		assertEquals(test.getDisplayTotalPrice(), "$1,466.40");
	}
	@Test
	public void testGets() {
		Product testProduct = new Product("123", "Test Product", "123.45");
		PurchasedProduct test = new PurchasedProduct(testProduct, 12, new BigDecimal("122.2"));
		assertEquals(test.getProduct(), testProduct);
		assertEquals(test.getProductID(), "123");
		assertEquals(test.getProductName(), "Test Product");
		assertEquals(test.getQuantity(), 12);
		assertEquals(test.getTotalPrice(), new BigDecimal("1466.4"));
		assertEquals(test.getUnitPrice(), new BigDecimal("122.2"));
	}
}
