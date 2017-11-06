package edu.mtu.tinventory.database.query.queries;

import edu.mtu.tinventory.data.Invoice;
import edu.mtu.tinventory.database.Tables;
import edu.mtu.tinventory.database.query.Query;

public class SaveInvoice implements Query {
	private Invoice invoice;

	public SaveInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	@Override
	public String getQuery() {
		return String.format("INSERT INTO %s VALUES (%d, '%s', '%s', '%s')", Tables.INVOICE_TABLE_NAME.nameToString(),
				invoice.getId(), invoice.getDate().toString(), invoice.getCustomer().getIDString(), invoice.getProductsString());
	}
}
