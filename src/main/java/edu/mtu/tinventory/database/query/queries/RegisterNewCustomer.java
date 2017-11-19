package edu.mtu.tinventory.database.query.queries;

import edu.mtu.tinventory.data.Customer;
import edu.mtu.tinventory.database.Tables;
import edu.mtu.tinventory.database.query.Query;

public class RegisterNewCustomer implements Query {
	private Customer customer;
	private String tableName;

	public RegisterNewCustomer(Customer customer, Tables table) {
		this.customer = customer;
		this.tableName = table.toString();
	}

	@Override
	public String getQuery() {
		return String.format("INSERT INTO %s VALUES ('%s', '%s', '%s', '%s', '%s', '%s')",
				tableName, customer.getIDString(),
				customer.getPersonName(), customer.getCompanyName(), customer.getPhoneNumber(), customer.getFaxNumber(),
				customer.getAddress());
	}
}
