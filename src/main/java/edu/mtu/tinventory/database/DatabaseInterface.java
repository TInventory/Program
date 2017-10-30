package edu.mtu.tinventory.database;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import edu.mtu.tinventory.data.Customer;
import edu.mtu.tinventory.data.Invoice;
import edu.mtu.tinventory.data.Product;
import edu.mtu.tinventory.database.query.Query;
import edu.mtu.tinventory.database.query.queries.GetProduct;
import edu.mtu.tinventory.database.query.queries.GrabAllItems;
import edu.mtu.tinventory.database.query.queries.RegisterNewItem;
import edu.mtu.tinventory.logging.LocalLog;

/**
 * 
 * @author James Helm
 * @since 10/12/2017
 * 
 *        Used as a simple interface between most of the inventory system and
 *        the SQL database
 */
public class DatabaseInterface {

	private List<Product> cache;

	/**
	 * Name of the table that the main data is stored in
	 */
	public String table = Tables.INVENTORY_TABLE_NAME.toString();

	/**
	 * Creates a single instance of the database interface
	 */
	private static DatabaseInterface instance = new DatabaseInterface();

	/**
	 * Instance of the setup class that should be run at start time
	 */
	private DatabaseSetup setup = new DatabaseSetup();

	/**
	 * Instance of the information needed to establish the connection to the
	 * MySQL Database
	 */
	private MySQL sqlConnection;

	/**
	 * Instance of consumer, class which handles the main interaction to and
	 * from the MySQL database as a runnable
	 */
	private static Consumer consumer;

	/**
	 * Creates a new pool of threads to handle query system
	 */
	// Maybe not hard code? could go either way
	ScheduledExecutorService executors = Executors.newScheduledThreadPool(20);

	/**
	 * Task used as a pool of seperate threads to handle queued quarries
	 */
	private ScheduledFuture<?> task;

	/**
	 * Empty constructor for now
	 */
	private DatabaseInterface() {
		// TODO: Change to actual config, currently is harcodes
		sqlConnection = new MySQL(null, null, null, null, 0);

		connectTo();

		cache = null;
		forceUpdateCache();
		autoUpdateCache( 60);
		// TODO: Populate from config file
		// sqlConnection = new MySQL(username, password, database, host, port);
		// TODO: start auto refreshing method
		// TODO: Currently hard coded, need to be added as a config via config
		// SQL table
		// final long initialDelay = 10;
		// TODO: Currently hard coded, need to be added as a config via config
		// SQL table
		// final long period = 5 * 60; // convert from minutes to seconds

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
	 * @param dataTable
	 *            String: Name of the table to register the item into, inserts
	 *            into default table if null
	 * 
	 * @return Returns true if the action is successful and the item is
	 *         registered properly into the database, otherwise returns false
	 */
	public boolean registerNewItem(Product product) {
		try {
			Query query = new RegisterNewItem(table, product);
			sendSingleCommand(query);
			return true;
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
	}

	/**
	 * Creates a new table in the database
	 * 
	 * @param dataTable
	 *            String: Name of the inventory table to be created, if null is
	 *            set to default.
	 * 
	 * @return Returns true if the action is successful and the table is created
	 *         in the database, otherwise returns false
	 */
	public boolean setupDataTable() {
		try {
			sendSingleCommand(setup.setupDataTable(table));
			return true;
		} catch (Exception exception) {
			return false;
		}
	}

	/**
	 * Creates a new table in the database
	 * 
	 * @return Returns true if the action is successful and the table is created
	 *         in the database, otherwise returns false
	 */
	public boolean deleteDataTable(String table) {
		try {
			sendSingleCommand(setup.deleteTable(table));
			return true;
		} catch (Exception exception) {
			return false;
		}
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
	 * of Product. This method takes the ProductID from the passed-in product
	 * instance, and then compares the information on this object with the
	 * information in the database. Anything that's different, the database gets
	 * updated with the new information.
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
	 * @return Product in the database if it exists, null otherwise.
	 */
	public Product getProduct(String productID) {
		try {
			GetProduct query = new GetProduct(productID);
			sendSingleCommand(query);
			return query.getProduct();
		} catch (Exception exception) {
			LocalLog.exception(exception);
			return null;
		}
	}

	/**
	 * Fetches a list of all customers currently registered in the database
	 * 
	 * @return A List of all registered products
	 */
	public List<Customer> getCustomers(String table) {
		try {
			// TODO: Changed for testing
			GrabAllItems query = new GrabAllItems(table);
			sendSingleCommand(query);

			return null;

		} catch (Exception exception) {
			LocalLog.exception(exception);
			return null;
		}
	}

	/**
	 * Fetches a list of all products currently registered in the database
	 * 
	 * @return A List of all registered products
	 */
	public List<Product> getProducts() {
		return cache;
	}

	/**
	 * Updates the cache
	 */
	public void forceUpdateCache() {
		cache = fetchProducts();
	}

	/**
	 * Fetches a list of all products currently in the Cache
	 * 
	 * @return A List of all registered products
	 */
	private List<Product> fetchProducts() {

		try {
			// Create the new query
			GrabAllItems query = new GrabAllItems(table);
			// output the commands
			sendSingleCommand(query);

			// NOTE: may be a delay here due to waiting for the MySQL database
			// to respond and the threads to sync up.

			// Retrieve
			return query.getProducts();

		} catch (Exception exception) {
			LocalLog.exception(exception);
			return null;
		}

	}

	/**
	 * Saves Store a completed invoice in the database. --Should also be saved
	 * to relevant Customer in database
	 * 
	 * @param invoice
	 *            The invoice to store in the database.
	 * @return true if it was successfully saved, false otherwise.
	 */
	public boolean saveInvoice(Invoice invoice) {
		return false;
	}

	/**
	 * Launches a connection to the MySQL database
	 */
	private void connectTo() {
		if (!sqlConnection.connect()) {
			LocalLog.error("Couldn't connect to database!");
		} else {
			LocalLog.info("Connected to Database"); // May want a different log
													// level later...
			consumer = new Consumer(sqlConnection, task);

		}
	}

	/**
	 * Sends a single query into the queue of the Consumer handler Also sends
	 * this command on a separate thread from main.
	 * 
	 * @param query
	 *            Query command to be issued
	 * @return Instance of the scheduled future, a handler for the thread.
	 */
	private ScheduledFuture<?> sendSingleCommand(Query query) {

		Consumer.queue(query);
		task = executors.schedule(consumer, 1, TimeUnit.MILLISECONDS);
		return task;
	}

	/**
	 * Start auto updating contents of the cache
	 * 
	 * @param callEveryXSeconds
	 *            Seconds to call the system
	 */
	private void autoUpdateCache(int callEveryXSeconds) {
		sendReoccuringCommand(new GrabAllItems(table), callEveryXSeconds);
	}

	/**
	 * Sends a query that occurs every x seconds
	 * 
	 * @param query
	 *            Query that is to be sent out
	 * @param callEveryXSeconds
	 *            Time in seconds that the query should be called
	 * @return Returns the thread that the query should go
	 */
	private ScheduledFuture<?> sendReoccuringCommand(Query query, int callEveryXSeconds) {
		Consumer.queue(query);
		return executors.scheduleAtFixedRate(consumer, 0, callEveryXSeconds, TimeUnit.SECONDS);
	}

	/***
	 * Creates a database in the SQL server
	 * 
	 * @param string
	 *            String: Name of the database to be created
	 * 
	 * @return True is the database already existed or is now existing, false if
	 *         the database does not exist and will not exist
	 */
	public boolean setupInventoryDatabase() {
		try {
			sendSingleCommand(setup.createDatabase(Tables.DATABASE_NAME.toString()));
			// here database either exists or was created
			return true;
		} catch (Exception exception) {
			LocalLog.exception(exception);
			return false;
		}
	}

	/**
	 * Register a new customer into the database
	 * 
	 * @param customer
	 *            - to be registered
	 * @return if the customer were registered
	 */
	public boolean registerNewCustomer(Customer customer) {
		return false;
	}

	/**
	 * Get a list of every customer in database
	 * 
	 * @return list of customers
	 */
	public List<Customer> getCustomers() {
		return new ArrayList<Customer>();
	}

	/**
	 * SHOULD ONLY BE CALLED WHEN THE PROGRAM IS CLOSING. Shuts down the pool of
	 * threads used to execute the SQL queries. Needed for the program to fully
	 * close after the main stage is closed. Otherwise, app is still running in
	 * the background.
	 */
	public void quit() {
		executors.shutdown();
	}
}
