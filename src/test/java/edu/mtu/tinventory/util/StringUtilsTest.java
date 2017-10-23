package edu.mtu.tinventory.util;
import org.junit.Test;
import static org.junit.Assert.*;

public class StringUtilsTest {

	@Test
	public void testIsNullOrEmpty() {
		assertEquals(StringUtils.isNullOrEmpty("test"), false);
		assertEquals(StringUtils.isNullOrEmpty(null), true);
		assertEquals(StringUtils.isNullOrEmpty(""), true);
	}
	@Test
	public void testIsNumber() {
		assertEquals(StringUtils.isNumber("test"), false);
		assertEquals(StringUtils.isNumber(""), false);
		assertEquals(StringUtils.isNumber(null), false);
		assertEquals(StringUtils.isNumber("5.123413"), true);
	}
}
