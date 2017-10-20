package edu.mtu.tinventory.database;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import edu.mtu.tinventory.data.Invoice;
import edu.mtu.tinventory.data.Product;
import edu.mtu.tinventory.database.query.Query;
import edu.mtu.tinventory.database.query.queries.RegisterNewItem;

/**
 * 
 * @author James Helm
 * @since 10/12/2017
 * 
 *        Used as a simple interface between most of the inventory system and
 *        the SQL database
 */
public class DatabaseInterface {

    /**
     * Name of the table that the main data is stored in
     */
    public String dataTable = "inventory";
    
    
    /**
     * Creates a single instance of the database interface
     */
    private static DatabaseInterface instance = new DatabaseInterface();

    /**
     * 
     */
  private  DatabaseSetup setup = new DatabaseSetup();
    /**
     * 
     */
    private MySQL sqlConnection;

    /**
     * 
     */

    private static Consumer consumer;

    /**
     * Creates a new pool of threads to handle query system
     */
    // Maybe not hard code? could go either way
    ScheduledExecutorService executors = Executors.newScheduledThreadPool(5);

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

        // TODO: Populate from config file
        // sqlConnection = new MySQL(username, password, database, host, port);
        //TODO: start auto refreshing method
        // TODO: Currently hard coded, need to be added as a config via config
        // SQL table
       // final long initialDelay = 10;
        // TODO: Currently hard coded, need to be added as a config via config
        // SQL table
        //final long period = 5 * 60; // convert from minutes to seconds

        // Runs task at run() , starting after initial Delay of config file and
        // repeats this action for every period
        // TODO: Needs to be outside of the class.
        // task.scheduleAtFixedRate(run(), initialDelay, period,
        // TimeUnit.SECONDS);
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
    public boolean registerNewItem(Product product, String dataTable) {
        try {
            // Send command through query 
        sendSingleCommand(new RegisterNewItem(dataTable, product));
            return true;
        }
        catch (Exception exception) {
            return false;
        }
    }

    /**
     * Creates a new table in the database
     * 
     * @return Returns true if the action is successful and the table is created
     *         in the database, otherwise returns false
     */
    public boolean setupDataTable() {
        try {
        sendSingleCommand(setup.setupDataTable(dataTable));
            return true;
        }
        catch (Exception exception) {
            return false;
        }
    }
    
    /**
     * Creates a new table in the database
     * 
     * @return Returns true if the action is successful and the table is created
     *         in the database, otherwise returns false
     */
    private boolean deleteDataTable() {
        try {
        sendSingleCommand(setup.deleteTable(dataTable));
            return true;
        }
        catch (Exception exception) {
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
     * @return Product in the database if it exists, null otherwise.
     */
    public Product getProduct(String productID) {
        return null;
    }

    /**
     * Fetches a list of all products currently registered in the database
     * 
     * @return A List of all registered products
     */
    public List<Product> getProducts() {

        return null;
    }

    /**
     * Saves Store a completed invoice in the database.
     * 
     * @param invoice
     *            The invoice to store in the database.
     * @return true if it was successfully saved, false otherwise.
     */
    public boolean saveInvoice(Invoice invoice) {
        return false;
    }

    private void connectTo() {
        if (!sqlConnection.connect()) {
            System.out.println("Couldn't connect to database!");
        } else {
            System.out.println("Connected to Database");
            consumer = new Consumer(sqlConnection);

        }
    }

    private ScheduledFuture<?> sendSingleCommand(Query query) {
        Consumer.queue(query);
        task = executors.schedule(consumer, 1, TimeUnit.MILLISECONDS);
        return task;
    }
}
