package edu.mtu.tinventory.data;
/**
 *
 * @author bjpaupor
 *
 */
public class Product {
	private String name;
	private State state;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	
	public Product(String name, State state) {
		this.name = name;
		this.state = state;
	}
}
