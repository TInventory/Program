package edu.mtu.tinventory.state;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Collection;

public class StateRegistryTest {

	@Before
	public void beforeEach() {
		StateRegistry.removeAllStates(); //Allows for a clean slate for each test.
	}

	@Test
	public void testRegisterState() {
		assertEquals(StateRegistry.registerState(new State("Test", "test")), true);
		assertEquals(StateRegistry.registerState(new State("Test", "test")), false);
		assertEquals(StateRegistry.registerState(new State("Test", "123123")), false);
		assertEquals(StateRegistry.registerState(new State("123123", "test")), true);
	}
	@Test
	public void testGetState() {
		State test = new State("123", "Test");
		StateRegistry.registerState(test);
		assertEquals(StateRegistry.getState("123"), test);
		assertEquals(StateRegistry.getState("Test"), null);
	}
	@Test
	public void testGetStates() {
		State test = new State("123", "Test1");
		State test2 = new State("1232", "Test2");
		StateRegistry.registerState(test);
		StateRegistry.registerState(test2);
		assertEquals(StateRegistry.getState(test.getID()), test);
		assertEquals(StateRegistry.getState(test2.getID()), test2);
	}
}
