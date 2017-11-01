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
	private String id;
	private UUID uuid;
	
	/**
	 * Creates a new Customer from information and a previous invoice
	 * @param phoneNumber
	 * @param faxNumber
	 * @param companyName
	 * @param personName
	 * @param invoice - null if never had an invoice before
	 */
	public Customer(String phoneNumber, String faxNumber, String companyName, String personName) {
		this.phoneNumber = phoneNumber;
		this.faxNumber = faxNumber;
		this.companyName = companyName;
		this.personName = personName;
		this.uuid = UUID.randomUUID();
	}
	
	/**
	 * Creates a new Customer from information and a previous invoice
	 * @param phoneNumber
	 * @param faxNumber
	 * @param companyName
	 * @param personName
	 * @param invoice - null if never had an invoice before
	 */
	public Customer(String phoneNumber, String faxNumber, String companyName, String personName, UUID uuid) {
		this.phoneNumber = phoneNumber;
		this.faxNumber = faxNumber;
		this.companyName = companyName;
		this.personName = personName;
		this.uuid = uuid;
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
	public UUID getUUID() {
		return uuid;
	}
}
