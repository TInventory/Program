package edu.mtu.tinventory.database;

import edu.mtu.tinventory.data.Customer;
import edu.mtu.tinventory.data.Invoice;
import edu.mtu.tinventory.data.Product;
import edu.mtu.tinventory.database.query.Query;
import edu.mtu.tinventory.database.query.queries.ChangeConfigTable;
import edu.mtu.tinventory.database.query.queries.CheckConfigurations;
import edu.mtu.tinventory.database.query.queries.ConfigPopulated;
import edu.mtu.tinventory.database.query.queries.CreateConfigTable;
import edu.mtu.tinventory.database.query.queries.CreateConfigurations;
import edu.mtu.tinventory.database.query.queries.CreateCustomersTable;
import edu.mtu.tinventory.database.query.queries.CreateDataTable;
import edu.mtu.tinventory.database.query.queries.CreateDatabase;
import edu.mtu.tinventory.database.query.queries.CreateEmployeesTable;
import edu.mtu.tinventory.database.query.queries.CreateInvoicesTable;
import edu.mtu.tinventory.database.query.queries.DropTable;
import edu.mtu.tinventory.database.query.queries.GetAllCustomers;
import edu.mtu.tinventory.database.query.queries.GetCustomer;
import edu.mtu.tinventory.database.query.queries.GetInvoicesForCustomer;
import edu.mtu.tinventory.database.query.queries.GetProduct;
import edu.mtu.tinventory.database.query.queries.GrabAllItems;
import edu.mtu.tinventory.database.query.queries.RegisterNewCustomer;
import edu.mtu.tinventory.database.query.queries.RegisterNewItem;
import edu.mtu.tinventory.database.query.queries.SaveInvoice;
import edu.mtu.tinventory.database.query.queries.UpdateProduct;
import edu.mtu.tinventory.database.utils.DatabaseUtils;
import edu.mtu.tinventory.gui.Dialogs;
import edu.mtu.tinventory.logging.LocalLog;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;

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
	 * If false do no try to access database
	 * implemented in almost all SQL methods
	 */
	public boolean accessible = true;
	
	/**
	 * Empty constructor for now
	 */
	private DatabaseInterface() {
		// TODO: Change to actual config, currently is hard coded
		sqlConnection = new MySQL(null, null, null, null, 0);

		connectTo();

		if(!setupDatabase()) {
			Dialogs.showDialogWithException("Database setup failed", "Failed to setup necessary tables for operation. Check below for exact error.", LocalLog.getLastLoggedException());
			quit();
			Platform.exit();
		}

		cache = null;
		forceUpdateCache();
		autoUpdateCache(60);

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
		try {
			Query query = new RegisterNewItem(table, product);
			sendSingleCommand(query);
			return true;
		} catch (Exception exception) {
			LocalLog.exception(exception);
			return false;
		}
	}

	/**
	 * Creates the required tables for the program to work.
	 *
	 * @return Returns true if the action is successful and the tables were
	 *         created in the database, otherwise returns false.
	 */
	public boolean setupDatabase() {
		try {
		    sendSingleCommand(new CreateDataTable(Tables.INVENTORY_TABLE_NAME.toString()));
			sendSingleCommand(new CreateEmployeesTable());
			sendSingleCommand(new CreateInvoicesTable());
			sendSingleCommand(new CreateCustomersTable());
		    createConfigTable();
			//checkIfFrozen(5);
			return true;
		} catch (Exception e) {
			LocalLog.exception(e);
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
		    //TODO: probably should just make this an drop inventory
		    sendSingleCommand(new DropTable(table));
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
	 * instance, and then updates the database with any new information found on
	 * it, such as the name or price.
	 * 
	 * @param product
	 *            The product to have its information updated in the server
	 *            database
	 * @return true if the update was successful, false otherwise.
	 */
	public boolean updateItem(Product product) {
		try {
			sendSingleCommand(new UpdateProduct(product));
			return true;
		} catch (Exception e) {
			LocalLog.exception(e);
			return false;
		}
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
	public List<Customer> getCustomers() {
		try {
			GetAllCustomers query = new GetAllCustomers();
			sendSingleCommand(query);
			return query.getCustomers();
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
		try {
			sendSingleCommand(new SaveInvoice(invoice));
			return true;
		} catch (Exception e) {
			LocalLog.exception(e);
			return false;
		}
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
	 * @return True is the database already existed or is now existing, false if
	 *         the database does not exist and will not exist
	 */
	public boolean setupInventoryDatabase() {
		try {
		    sendSingleCommand(new CreateDatabase(Tables.DATABASE_NAME.toString()));
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
		try {
			sendSingleCommand(new RegisterNewCustomer(customer));
			return true;
		} catch (Exception e) {
			LocalLog.exception(e);
			return false;
		}
	}

	/**
	 * 
	 * @param customer
	 * @return
	 */
	public List<Invoice> getCustomerInvoices(Customer customer) {
		try {
			GetInvoicesForCustomer query = new GetInvoicesForCustomer(customer);
			sendSingleCommand(query);
			return query.getInvoices();
		} catch (Exception e) {
			LocalLog.exception(e);
			return null;
		}
	}

	/**
	 * Gets the Customer object associated with the specified customer ID.
	 * The ID should be the UUID string without the hyphens (so length = 32).
	 * @param customerID The UUID for the customer in string form (without the hyphens)
	 * @return The Customer object with the specified UUID,
	 * 		   or null if such a customer did not exist or there was an error querying the database.
	 */
	public Customer getCustomer(String customerID) {
		try {
			GetCustomer query = new GetCustomer(customerID);
			sendSingleCommand(query);
			return query.getCustomer();
		} catch (Exception e) {
			LocalLog.exception(e);
			return null;
		}
	}

	/**
	 * Creates the configuration table
	 * @return True if the table is created successfully 
	 */
	public boolean createConfigTable() {
		try {
			sendSingleCommand(new CreateConfigTable(Tables.CONFIGURATION_TABLE_NAME.nameToString()));
			if (!configIsPopulated()) {
			populateConfigTable();
			}
			return true;
		} catch (Exception exception) {
			LocalLog.exception(exception);
			return false;
		}
	}
	
	/**
	 * Check if the configuration table has any information
	 * @return true if configurations exist, false if not
	 */
	private boolean configIsPopulated() {
	    try {
	    ConfigPopulated populated = new ConfigPopulated();
	    sendSingleCommand(populated);
	    if (populated.isPopulated()) {
	        return true;
	    }
	    else {
	        return false;
	    }
	    }
	    catch (Exception exception) {
	        LocalLog.exception(exception);
	        return false;
	    }
	}

    /**
	 * Creates the basic configurations of the configuration table
	 */
	private void populateConfigTable() {
	    try {
	    	// Hardcode these two to avoid circular dependencies on StateRegistry and DatabaseInterface
	        sendSingleCommand(new CreateConfigurations("states", "AVAILABLE:SOLD"));
	        sendSingleCommand(new CreateConfigurations("frozen", "false"));
	    }
	    catch (Exception exception) {
	        LocalLog.exception(exception);
	    }
	}

    /**
	 * 
	 * @param freezeDatabase Boolean: if true freeze the database, if false unfreeze
	 * @return
	 */
	public boolean freezeDatabase(boolean freezeDatabase) {
	   // TODO: check for admin perms?
	    try {
	           sendSingleCommand(new ChangeConfigTable("frozen", String.valueOf(freezeDatabase)));
	        return true;
	    }
	    catch(Exception exception) {
	        LocalLog.exception(exception);
	        return false;
	    }
	}

	/**
	 * Launches a re-occurring task to see if the database is frozen
	 * Should only ever be launched once on startup
	 * Value is stored in main class TInventory
	 * @param secondsToCheck How often it should check status
	 */
	public void checkIfFrozen(int secondsToCheck) {
	    try {
	        CheckConfigurations froze = new CheckConfigurations("frozen");
	        sendReoccuringCommand(froze, secondsToCheck);
	        // set frozen value as easily grabbed variable
	        DatabaseUtils.setDatabaseFrozen(Boolean.getBoolean(froze.getValue()));
	    }
	    catch (Exception exception) {
	        LocalLog.exception(exception);
	    }
	}

	public String getStatesString() {
		/*try {
			CheckConfigurations states = new CheckConfigurations("states");
			sendSingleCommand(states);
			return states.getValue();
		} catch (Exception e) {
			LocalLog.exception(e);
			return null;
		}*/
		//TODO: FIX after presentation
		return "AVAILABLE:SOLD";
	}
	
	/**
	 * SHOULD ONLY BE CALLED WHEN THE PROGRAM IS CLOSING. Shuts down the pool of
	 * threads used to execute the SQL queries. Needed for the program to fully
	 * close after the main stage is closed. Otherwise, app is still running in
	 * the background.
	 */
	public void quit() {
		executors.shutdown();
		DatabaseUtils.shutdownStatusThread();
	}
}
