package edu.mtu.tinventory.data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.mtu.tinventory.database.DatabaseInterface;

public class Customer {
	private String phoneNumber;
	private String faxNumber;
	private String companyName;
	private String personName;
	private String address;
	private UUID id;
	
	/**
	 * Creates a new Customer from information and a previous invoice
	 * @param phoneNumber
	 * @param faxNumber
	 * @param companyName
	 * @param personName
	 */
	public Customer(String personName, String companyName, String phoneNumber, String faxNumber) {
		this.id = UUID.randomUUID();
		this.personName = personName;
		this.companyName = companyName;
		this.phoneNumber = phoneNumber;
		this.faxNumber = faxNumber;
	}
	
	/**
	 * Creates a new Customer from information and a previous invoice
	 * @param phoneNumber
	 * @param faxNumber
	 * @param companyName
	 * @param personName
	 * @param id
	 */
	public Customer(UUID id, String phoneNumber, String faxNumber, String companyName, String personName) {
		this.id = id;
		this.personName = personName;
		this.companyName = companyName;
		this.phoneNumber = phoneNumber;
		this.faxNumber = faxNumber;
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
		String result = "";
		for (Invoice i : pastSales)
			result += i.getId() + " " + i.getDate().toString() + " " + i.getTotal();
		return result;
	}
	public UUID getID() {
		return id;
	}
}
