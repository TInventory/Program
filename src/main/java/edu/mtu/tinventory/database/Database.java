package edu.mtu.tinventory.database;

import edu.mtu.tinventory.data.Product;

public class Database {

	// Creates a single instance of the database interface
	private Database instance = new Database();

	// Empty constructor for now
	private Database() {

	}

	/**
	 * Retrieves a singleton instances of the Database class to be used to
	 * retrieve information and make changes to the database
	 * 
	 * @return an Instance of the databaseInterface
	 */
	public Database getInstance() {
		return instance;
	}

	/**
	 * Creates a new item in the database
	 * 
	 * @param product
	 *            Product: The product object to be inserted into the database
	 * 
	 * @return Returns true if the action is successful and the item is
	 *         registered properly into the database, otherwise returns false
	 */
	public boolean registerNewItem(Product product) {
		return false;
	}

	/**
	 * Completely removes an item type from the database
	 * 
	 * 
	 * @param product
	 *            Product: The product to be permanently removed form the database
	 * 
	 * @return Returns true if the action is successful and the item is
	 *         completely removed from the database, otherwise returns false
	 */
	public boolean deleteItem(Product product) {
		return false;
	}

	/**
	 * Increments the amount of items that are in stock in the inventory
	 * 
	 * @param product Product: The product to increase the amount in stock
	 * @param amountToAddToInventory Integer: The amount to increase the in stock by
	 * 
	 * @return Returns true if the action is successful and the item is
	 *         completely removed from the database, otherwise returns false
	 */
	public boolean addInventory(Product product, int amountToAddToInventory) {
		return true;
	}

	/**
	 * Increments the amount of items that are in stock in the inventory
	 * 
	 * @param product Product: The product to increase the amount in stock
	 * @param amountToAddToInventory Integer: The amount to increase the in stock by
	 * 
	 * @return Returns true if the action is successful and the item is
	 *         completely removed from the database, otherwise returns false
	 */
	public boolean removeInventory(Product product, int amountToRemoveFromInventory) {
		return true;
	}
	
}
