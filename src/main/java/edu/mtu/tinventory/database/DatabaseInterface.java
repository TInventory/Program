package edu.mtu.tinventory.database;

import edu.mtu.tinventory.data.Product;

/**
 * 
 * @author James Helm
 * @since 10/12/2017
 * 
 *        Used as a simple interface between most of the inventory system and the SQL database
 */
public class DatabaseInterface {

    /**
     * Creates a single instance of the database interface
     */
    private static DatabaseInterface instance = new DatabaseInterface();

    /**
     * 
     */
    private MySQL sql;
    /**
     * 
     */
    private Consumer consumer;
    /**
     * Empty constructor for now
     */
    private DatabaseInterface() {

    	//TODO: Populate from config file
    	//sql = new MySQL(username, password, database, host, port);
    	connectTo();
    	
    }
    

    /**
     * Retrieves a singleton instances of the Database class to be used to
     * retrieve information and make changes to the database
     * 
     * @return an Instance of the databaseInterface
     */
    public static DatabaseInterface getInstance() {
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
     *            Product: The product to be permanently removed form the
     *            database
     * 
     * @return Returns true if the action is successful and the item is
     *         completely removed from the database, otherwise returns false
     */
    public boolean deleteItem(Product product) {
        return false;
    }

    /**
     * Updates the Product in the database with the information in this instance
     * of Product.
     * This method takes the ProductID from the passed-in product instance, and
     * then compares the
     * information on this object with the information in the database. Anything
     * that's different,
     * the database gets updated with the new information.
     * 
     * @param product
     *            The product to have its information updated in the server
     *            database
     * @return true if the update was successful, false otherwise.
     */
    public boolean updateItem(Product product) {
        return false;
    }

    /**
     * Gets a particular product from the database
     * 
     * @param productID
     *            the unique product ID to find product in database
     * @return Product in the database
     */
    public Product getProduct(String productID) {
        return null;
    }
    
    private void connectTo() {
    	if (!sql.connect()) {
    		System.out.println("Couldn't connect to database!");
    	}
    	else {
    		System.out.println("Connected to Database");
    		consumer = new Consumer(sql);
    		
    	}
    }
}
