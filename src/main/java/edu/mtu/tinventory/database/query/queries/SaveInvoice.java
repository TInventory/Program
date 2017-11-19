package edu.mtu.tinventory.database.query.queries;

import edu.mtu.tinventory.data.Invoice;
import edu.mtu.tinventory.database.Tables;
import edu.mtu.tinventory.database.query.Query;

/**
* Saves an invoice to the Database
*
* @author 
*
* @since 
*/
public class SaveInvoice implements Query {
	// Invoice to save to the database
	private Invoice invoice;

	/**
	* Constructor
	* Executable instance of SaveInvoice
	*
	* @param invoice Invoice: The invoice to be saved from the database
	*/
	//TODO: Add table specification
	public SaveInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	@Override
	public String getQuery() {
		return String.format("INSERT INTO %s VALUES (%d, '%s', '%s', '%s')", Tables.INVOICE_TABLE_NAME.nameToString(),
				invoice.getId(), invoice.getDate().toString(), invoice.getCustomer().getIDString(), invoice.getProductsString());
	}
}
