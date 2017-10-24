package edu.mtu.tinventory.data;

public class Employee {
	private String id;
	private String firstName;
	private String lastName;
	//TODO: Add AccessLevel or Role or something.

	public Employee(String id, String firstName, String lastName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getID() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}
}
