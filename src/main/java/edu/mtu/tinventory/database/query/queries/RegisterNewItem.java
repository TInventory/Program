package edu.mtu.tinventory.database.query.queries;

import edu.mtu.tinventory.data.Product;
import edu.mtu.tinventory.database.Tables;
import edu.mtu.tinventory.database.query.Query;

public class RegisterNewItem implements Query {
	private String name;
	private String iD;
	private String price;
	private String quantity;

	private String table;

	/**
	 * Query for inserting product into database
	 * 
	 * @param product
	 *            Product: product to be inserted into the database
	 */
	public RegisterNewItem(Tables table, Product product) {
		name = "'" + product.getName() + "'";
		iD = "'" + product.getID() + "'";
		price = "'" + product.getPrice().toPlainString() + "'";
		quantity = "'" + product.getQuanity() + "'";

		this.table = table.toString();
	}

	@Override
	public String getQuery() {
		// SQL Statement
		return String.format("INSERT INTO %s VALUES (%s , %s , %s , %s);", table, iD, name, price, quantity);
	}
}
