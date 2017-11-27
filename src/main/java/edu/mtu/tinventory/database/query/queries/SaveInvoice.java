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
	
	private String tableName;

	/**
	* Constructor
	* Executable instance of SaveInvoice
	*
	* @param invoice Invoice: The invoice to be saved from the database
	*/
	public SaveInvoice(Invoice invoice, Tables table) {
		this.invoice = invoice;
		this.tableName = table.toString();
	}

	@Override
	public String getQuery() {
		return String.format("INSERT INTO %s VALUES (%d, '%s', '%s', '%s')", tableName,
				invoice.getId(), invoice.getDate().toString(), invoice.getCustomer().getIDString(), invoice.getProductsString());
	}
}
