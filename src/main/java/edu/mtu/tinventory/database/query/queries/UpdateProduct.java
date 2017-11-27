package edu.mtu.tinventory.database.query.queries;

import edu.mtu.tinventory.data.Product;
import edu.mtu.tinventory.database.Tables;
import edu.mtu.tinventory.database.query.Query;

/**
* Executable SQL Query to update product
* @author 
*
* @since 
*
*/
public class UpdateProduct implements Query {
	// Instance of product to update
	private Product product;

	private String tableName;
	/**
	* Constructor
	* Executable instance of UpdateProduct
	*
	* @param product Product: product to be updated
	*
	*/
	public UpdateProduct(Product product, Tables table) {
		this.product = product;
		this.tableName = table.toString();
	}

	@Override
	public String getQuery() {
		return String.format("UPDATE %s SET name = '%s', price = '%s', quantity = '%s' WHERE id = '%s'",
				tableName, product.getName(), product.getPrice().toPlainString(),
				product.getQuanity(), product.getID());
	}
}
