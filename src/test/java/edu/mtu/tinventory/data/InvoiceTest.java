package edu.mtu.tinventory.data;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class InvoiceTest {

	@Test
	public void testOneProduct() {
		Product testProduct = new Product("123", "Test Product", "123.45");
		PurchasedProduct testPurchasedProduct = new PurchasedProduct(testProduct, 12, new BigDecimal("122.2"));
		List<PurchasedProduct> products = new ArrayList<PurchasedProduct>();
		products.add(testPurchasedProduct);
		Customer customer = new Customer(null, null, null, null, null);
		Invoice test = Invoice.createNewInvoice(products, customer, true);
		assertEquals(test.getTotal(), new BigDecimal("1466.4"));
	}
	@Test
	public void testMultipleProducts() {
		Product testProduct = new Product("123", "Test Product", "123.45");
		Product testProduct2 = new Product("1234", "Test Product 2", "42.1");
		PurchasedProduct testPurchasedProduct = new PurchasedProduct(testProduct, 12, new BigDecimal("122.2"));
		PurchasedProduct testPurchasedProduct2 = new PurchasedProduct(testProduct2, 20, new BigDecimal("39.8"));
		List<PurchasedProduct> products = new ArrayList<PurchasedProduct>();
		products.add(testPurchasedProduct);
		products.add(testPurchasedProduct2);
		Customer customer = new Customer(null, null, null, null, null);
		Invoice test = Invoice.createNewInvoice(products, customer, true);
		assertEquals(test.getTotal(), new BigDecimal("2262.4"));
	}
}
