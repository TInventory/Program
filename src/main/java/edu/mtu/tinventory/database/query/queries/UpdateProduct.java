package edu.mtu.tinventory.database.query.queries;

import edu.mtu.tinventory.data.Product;
import edu.mtu.tinventory.database.Tables;
import edu.mtu.tinventory.database.query.Query;

public class UpdateProduct implements Query {
	private Product product;

	public UpdateProduct(Product product) {
		this.product = product;
	}

	@Override
	public String getQuery() {
		return String.format("UPDATE %s SET name = '%s', price = '%s', quantity = '%s' WHERE id = '%s'",
				Tables.INVENTORY_TABLE_NAME.nameToString(), product.getName(), product.getPrice().toPlainString(),
				product.getQuanity(), product.getID());
	}
}
