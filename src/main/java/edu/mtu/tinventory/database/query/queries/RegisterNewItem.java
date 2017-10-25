package edu.mtu.tinventory.database.query.queries;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import edu.mtu.tinventory.data.Product;
import edu.mtu.tinventory.database.query.Query;
import edu.mtu.tinventory.state.State;
import edu.mtu.tinventory.state.StateQtyMap;

public class RegisterNewItem implements Query {
	private String name;
	private String displayPrice;
	private String iD;
	private String price;
	private StateQtyMap quantity;

	private String table;
	// TODO : Replace from configurations
	private String currency = "'$'";

	/**
	 * Query for inserting product into database
	 * 
	 * @param product
	 *            Product: product to be inserted into the database
	 */
	public RegisterNewItem(String table, Product product) {
		name = "'" + product.getName() + "'";
		displayPrice = "'" + product.getDisplayPrice() + "'";
		iD = "'" + product.getID() + "'";
		price = "'" + product.getPrice().toPlainString() + "'";
		quantity = product.getQuanity();

		this.table = table;
	}

	@Override
	public String getQuery() {
		// SQL Statement
		String string = String.format("INSERT INTO %s VALUES (%s , %s , %s , %s);", table, iD, name, price,
				"'" + quantityToString() + "'");
		return string;
	}

	/**
	 * 
	 * Stores quantities string in following format:
	 * State:Integer;State:Integer; etc. Semicolon separates two entry sets
	 * Colon separates the entry set into the State and Integer
	 * 
	 * @return String of quantities into a single string
	 */
	private String quantityToString() {
		String quantityString = "";
		HashMap<State, Integer> map = this.quantity.getMap();
		Iterator<Entry<State, Integer>> iterator = map.entrySet().iterator();

		// Holds the current iterations value set
		Entry<State, Integer> entry;

		while (iterator.hasNext()) {
			entry = iterator.next();

			/*
			 * Semicolon separates two entry sets Colon separates the entry set
			 * into the State and Integer
			 */
			// "rented:5;sold:10"
			quantityString = quantityString + entry.getKey().getID() + ":" + entry.getValue() + ";";
		}

		// exception for empty quantities
		if (quantityString.equals("")) {
			return quantityString;
		}
		// Removes the extra semicolon
		return quantityString.substring(0, quantityString.length() - 1);
	}
}
