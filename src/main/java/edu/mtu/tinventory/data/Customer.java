package edu.mtu.tinventory.data;

import edu.mtu.tinventory.database.DatabaseInterface;
import java.util.List;
import java.util.UUID;

public class Customer {
	private UUID id;
	private String personName;
	private String companyName;
	private String phoneNumber;
	private String faxNumber;
	private String address;

	/**
	 * Creates a new Customer
	 * @param personName The name of the customer
	 * @param companyName The name of the business the customer represents, can be null.
	 * @param phoneNumber The phone number for the customer, can be null.
	 * @param faxNumber The fax number for the customer/business, can be null.
	 * @param address The address for the customer, can be null.
	 */
	public Customer(String personName, String companyName, String phoneNumber, String faxNumber, String address) {
		this.id = UUID.randomUUID();
		this.personName = personName;
		this.companyName = companyName;
		this.phoneNumber = phoneNumber;
		this.faxNumber = faxNumber;
		this.address = address;
	}
	
	/**
	 * Recreates a customer from the database
	 * @param id The unique ID for the customer
	 * @param personName The name of the customer
	 * @param companyName The name of the business the customer represents, can be null.
	 * @param phoneNumber The phone number for the customer, can be null.
	 * @param faxNumber The fax number for the customer/business, can be null.
	 * @param address The address for the customer, can be null.
	 */
	public Customer(UUID id, String personName, String companyName, String phoneNumber, String faxNumber, String address) {
		this.id = id;
		this.personName = personName;
		this.companyName = companyName;
		this.phoneNumber = phoneNumber;
		this.faxNumber = faxNumber;
		this.address = address;
	}
	
	public String getFaxNumber() {
		return faxNumber;
	}
	public String getAddress() {
		return address;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public String getCompanyName() {
		return companyName;
	}
	public String getPersonName() {
		return personName;
	}
	/**
	 * Returns the preferred name
	 * @return name
	 */
	public String getName() {
		String result = companyName;
		if (result == null || result.equals(""))
			result = personName;
		return result;
	}
	public List<Invoice> getInvoices() {
		return DatabaseInterface.getInstance().getCustomerInvoices(this);
	}
	public String getPastSales() {
		List<Invoice> pastSales = DatabaseInterface.getInstance().getCustomerInvoices(this);
		StringBuilder result = new StringBuilder(); // Saves a little memory doing it this way.
		for (Invoice i : pastSales)
			result.append("ID:" + i.getId()).append(" Date:").append(i.getDate()).append(" Total:").append(i.getTotal().toPlainString());
		return result.toString();
	}
	public UUID getID() {
		return id;
	}

	/**
	 * Returns the UUID for this customer as a string with the hyphens removed.
	 * @return The UUID string without hyphens
	 */
	public String getIDString() {
		return id.toString().replace("-", "");
	}
}
