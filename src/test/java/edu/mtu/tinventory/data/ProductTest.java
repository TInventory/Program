package edu.mtu.tinventory.data;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ProductTest {
	@Test
	public void testGets() {
		Product test = new Product("123", "Test", "12.23");
		assertEquals(test.getID(), "123");
		assertEquals(test.getName(), "Test");
		assertEquals(test.getPrice(), 12.23);
		test.setName("Test 2");
		assertEquals(test.getName(), "Test 2");
	}
	@Test
	public void testSetName() {
		Product test = new Product("123", "Test", "12.23");
		assertEquals(test.getName(), "Test");
		test.setName("Test 2");
		assertEquals(test.getName(), "Test 2");
	}
	@Test
	public void testGetDisplayPrice() {
		Product test = new Product("123", "Test", "12.23");
		assertEquals(test.getDisplayPrice(), "12.23");
	}
	@Test
	public void testToString() {
		Product test = new Product("123", "Test", "12.23");
		assertEquals(test.toString(), "Name:Test ID:123 Price:12.23");
	}
}
