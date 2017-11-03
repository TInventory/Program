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
	 * Creates a new Customer from information and a previous invoice
	 * @param personName
	 * @param companyName
	 * @param phoneNumber
	 * @param faxNumber
	 * @param address
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
	 * Recreates a previous customer from database
	 * @param id
	 * @param personName
	 * @param companyName
	 * @param phoneNumber
	 * @param faxNumber
	 * @param address
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
	public List<Invoice> getInvoices() {
		return DatabaseInterface.getInstance().getCustomerInvoices(this);
	}
	public String getPastSales() {
		List<Invoice> pastSales = DatabaseInterface.getInstance().getCustomerInvoices(this);
		StringBuilder result = new StringBuilder(); // Saves a little memory doing it this way.
		for (Invoice i : pastSales)
			result.append(i.getId()).append(" ").append(i.getDate()).append(" ").append(i.getTotal().toPlainString());
		return result.toString();
	}
	public UUID getID() {
		return id;
	}
	public String getIDString() {
		return id.toString().replace("-", "");
	}
}
