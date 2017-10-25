package edu.mtu.tinventory.data;

import java.util.ArrayList;
import java.util.List;

public class Customer {
	private String phoneNumber;
	private String faxNumber;
	private String companyName;
	private String personName;
	private String address;
	private List<Invoice> pastSales;
	
	/**
	 * Creates a new Customer from information and a previous invoice
	 * @param phoneNumber
	 * @param faxNumber
	 * @param companyName
	 * @param personName
	 * @param invoice - null if never had an invoice before
	 */
	public Customer(String phoneNumber, String faxNumber, String companyName, String personName, Invoice invoice) {
		this.phoneNumber = phoneNumber;
		this.faxNumber = faxNumber;
		this.companyName = companyName;
		this.personName = personName;
		if (invoice != null)
			logSale(invoice);
	}
	public void logSale(Invoice invoice) {
		if (pastSales == null)
			pastSales = new ArrayList<Invoice>();
		pastSales.add(invoice);
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
	public List<Invoice> getPastSales() {
		return pastSales;
	}
}
