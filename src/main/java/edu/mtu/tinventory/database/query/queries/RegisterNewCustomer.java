package edu.mtu.tinventory.database.query.queries;

import edu.mtu.tinventory.data.Customer;
import edu.mtu.tinventory.database.Tables;
import edu.mtu.tinventory.database.query.Query;

public class RegisterNewCustomer implements Query {
	private Customer customer;

	public RegisterNewCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public String getQuery() {
		return String.format("INSERT INTO %s VALUES ('%s', '%s', '%s', '%s', '%s', '%s')",
				Tables.CUSTOMER_TABLE_NAME.nameToString(), customer.getIDString(),
				customer.getPersonName(), customer.getCompanyName(), customer.getPhoneNumber(), customer.getFaxNumber(),
				customer.getAddress());
	}
}
