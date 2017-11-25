package edu.mtu.tinventory.data;

public class Employee {
	//NOTE: Password should NOT be stored locally.
	private String id; // FLLLLLLL is the format.
	private String firstName;
	private String lastName;
	private Access access;

	/**
	 * Constructor for creating a new employee
	 * @param firstName The first name of the employee
	 * @param lastName The last name of the employee
	 * @param level The starting access level for the employee
	 */
	public Employee(String firstName, String lastName, Access.Level level) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.access = new Access(level);
	}

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

	public Employee updateAccess(Access newAccess) {
		this.access = newAccess;
		return this;
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof Employee && id.equalsIgnoreCase(((Employee)o).getID());
	}
}
