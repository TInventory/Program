package edu.mtu.tinventory.data;

public class Employee {
	//NOTE: Password should NOT be stored locally.
	private String id; // Generally a conglomeration of their name, like how your mtu emails are. Arbitrarily picking 8 as the length limit.
	private String firstName;
	private String lastName;
	private Access access;

	public Employee(String id, String firstName, String lastName, Access access) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.access = access;
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

	public String getFullName() {
		return firstName + " " + lastName;
	}

	public Access getAccess() {
		return access;
	}
}
