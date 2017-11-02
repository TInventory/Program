package edu.mtu.tinventory.state;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Set;

public class StateRegistryTest {

	@Before
	public void beforeEach() {
		StateRegistry.removeAllStates(); //Allows for a clean slate for each test.
	}

	@Test
	public void testRegisterState() {
		assertEquals(StateRegistry.registerState("Test"), true);
		assertEquals(StateRegistry.registerState("Test"), false);
		assertEquals(StateRegistry.registerState("123123"), false);
	}
	@Test
	public void testIsState() {
		StateRegistry.registerState("test");
		assertEquals(StateRegistry.isState("123"), true);
		assertEquals(StateRegistry.isState("Test"), false);
	}
	@Test
	public void testGetStates() {
		StateRegistry.registerState("test");
		StateRegistry.registerState("test2");
		Set<String> set = StateRegistry.getStates();
		assertEquals(set.contains("test"), true);
		assertEquals(set.contains("test2"), true);
	}
}
