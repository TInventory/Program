package edu.mtu.tinventory.database;

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
	 *            The product object to be inserted into the database
	 * 
	 * @return Returns true if the action is successful and the item is
	 *         registered properly into the database, otherwise returns false
	 */
	public boolean registerNewItem(Object product) {
		return false;
	}

	/**
	 * Completely removes an item type from the database
	 * 
	 * 
	 * @param product
	 *            Object: The product to be permanently removed form the database
	 * 
	 * @return Returns true if the action is successful and the item is
	 *         completely removed from the database, otherwise returns false
	 */
	public boolean deleteItem(Object product) {
		return false;
	}

	/**
	 * Increments the amount of items that are in stock in the inventory
	 * 
	 * @param product Object: The product to increase the amount in stock
	 * @param amountToAddToInventory Integer: The amount to increase the in stock by
	 * 
	 * @return Returns true if the action is successful and the item is
	 *         completely removed from the database, otherwise returns false
	 */
	public boolean addInventory(Object product, int amountToAddToInventory) {
		return true;
	}

	/**
	 * Increments the amount of items that are in stock in the inventory
	 * 
	 * @param product Object: The product to increase the amount in stock
	 * @param amountToAddToInventory Integer: The amount to increase the in stock by
	 * 
	 * @return Returns true if the action is successful and the item is
	 *         completely removed from the database, otherwise returns false
	 */
	public boolean removeInventory(Object product, int amountToRemoveFromInventory) {
		return true;
	}
	
}
