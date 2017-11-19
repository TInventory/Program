package edu.mtu.tinventory.database;

import edu.mtu.tinventory.data.Customer;
import edu.mtu.tinventory.data.Employee;
import edu.mtu.tinventory.data.Invoice;
import edu.mtu.tinventory.data.Product;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 *
 * @author James Helm
 * @since 10/12/2017
 *
 *        Used as a simple interface between most of the inventory system and
 *        the SQL database
 */
public class DatabaseInterface {

    private DatabaseAPI api;

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
     * Creates a new pool of threads to handle query system
     */
    // Maybe not hard code? could go either way
    ScheduledExecutorService executors = Executors.newScheduledThreadPool(20);

    /**
     * If false do no try to access database implemented in almost all SQL
     * methods
     */
    public boolean accessible = true;

    /**
     * Constructor
     */
    private DatabaseInterface() {
        // TODO: Change to actual config, currently is hard coded
        sqlConnection = new MySQL("cs3141", "taco", "tinventory", "kiro47.ddns.net", 9999);
         //sqlConnection = DatabaseUtils.getMySQL();
        this.api = new DatabaseAPI(sqlConnection, false);
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
        return api.registerNewItem(product, Tables.INVENTORY_TABLE_NAME);
    }

    /**
     * Creates the required tables for the program to work.
     *
     * @return Returns true if the action is successful and the tables were
     *         created in the database, otherwise returns false.
     */
    public boolean setupDatabase() {
        return api.setupDatabase(false);
    }

    /**
     * Creates a new table in the database
     *
     * @return Returns true if the action is successful and the table is created
     *         in the database, otherwise returns false
     */
    public boolean deleteDataTable(Tables table) {
        return api.deleteDataTable(table);
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
        return api.deleteItem(product, Tables.INVENTORY_TABLE_NAME);
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
        return api.updateItem(product, Tables.INVENTORY_TABLE_NAME);
    }

    /**
     * Gets a particular product from the database
     *
     * @param productID
     *            the unique product ID to find product in database
     * @return Product in the database if it exists, null otherwise.
     */
    public Product getProduct(String productID) {
        return api.getProduct(productID, Tables.INVENTORY_TABLE_NAME);
    }

    /**
     * Fetches a list of all customers currently registered in the database
     *
     * @return A List of all registered products
     */
    public List<Customer> getCustomers() {
        return api.getCustomers(Tables.CUSTOMER_TABLE_NAME);
    }

    /**
     * Fetches a list of all products currently registered in the database
     *
     * @return A List of all registered products
     */
    public List<Product> getProducts() {
        return api.getCache();
    }

    /**
     * Updates the cache
     */
    public void forceUpdateCache() {
        api.forceUpdateCache(Tables.INVENTORY_TABLE_NAME);
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
        return api.saveInvoice(invoice, Tables.INVOICE_TABLE_NAME);
    }

    /***
     * Creates a database in the SQL server
     *
     * @return True is the database already existed or is now existing, false if
     *         the database does not exist and will not exist
     */
    public boolean setupInventoryDatabase() {
        return api.setupInventoryDatabase(Tables.INVENTORY_TABLE_NAME);
       }

    /**
     * Register a new customer into the database
     *
     * @param customer
     *            - to be registered
     * @return if the customer were registered
     */
    public boolean registerNewCustomer(Customer customer) {
        return api.registerNewCustomer(customer, Tables.CUSTOMER_TABLE_NAME);
    }

    /**
     *
     * @param customer
     * @return
     */
    public List<Invoice> getCustomerInvoices(Customer customer) {
        return api.getCustomerInvoices(customer, Tables.INVOICE_TABLE_NAME);
    }

    /**
     * Returns a list of all invoices in database
     *
     * @return
     */
    public List<Invoice> getInvoices() {
        return api.getInvoices(Tables.INVOICE_TABLE_NAME);
    }

    /**
     * Removes a single invoice from the database
     *
     * @param invoice
     *            to be removed
     */
    public void deleteInvoice(Invoice invoice) {
        //TODO: Might make this return stuff
         api.deleteInvoice(invoice, Tables.INVOICE_TABLE_NAME);
    }

    /**
     * Gets the Customer object associated with the specified customer ID. The
     * ID should be the UUID string without the hyphens (so length = 32).
     *
     * @param customerID
     *            The UUID for the customer in string form (without the hyphens)
     * @return The Customer object with the specified UUID, or null if such a
     *         customer did not exist or there was an error querying the
     *         database.
     */
    public Customer getCustomer(String customerID) {
        return api.getCustomer(customerID, Tables.CUSTOMER_TABLE_NAME);
       }

    /**
     * Creates the configuration table
     *
     * @return True if the table is created successfully
     */
    public boolean createConfigTable() {
        return api.createConfigTable(Tables.CONFIGURATION_TABLE_NAME);
    }

    /**
     *
     * @param freezeDatabase
     *            Boolean: if true freeze the database, if false unfreeze
     * @return
     */
    public boolean freezeDatabase(boolean freezeDatabase) {
        return api.freezeDatabase(freezeDatabase, Tables.CONFIGURATION_TABLE_NAME);
    }

    /**
     * Launches a re-occurring task to see if the database is frozen Should only
     * ever be launched once on startup Value is stored in main class TInventory
     *
     * @param secondsToCheck
     *            How often it should check status
     */
    public void checkIfFrozen(int secondsToCheck) {
        api.checkIfFrozen(secondsToCheck, Tables.CONFIGURATION_TABLE_NAME);
     }

    public String getStatesString() {
        return api.getStatesString(Tables.CONFIGURATION_TABLE_NAME);
    }

    public Employee getEmployee(String employeeID, String password) {
        return api.getEmployee(employeeID, password, Tables.EMPLOYEES_TABLE_NAME);
        }

    public boolean doesEmployeeIDExist(String employeeID) {
        return api.doesEmployeeIDExist(employeeID, Tables.EMPLOYEES_TABLE_NAME);
    }

    public String registerNewEmployee(Employee employee) {
        return api.registerNewEmployee(employee, Tables.EMPLOYEES_TABLE_NAME);
    }

    public boolean changePassword(Employee employee, String newPassword) {
        return api.changePassword(employee, newPassword, Tables.EMPLOYEES_TABLE_NAME);
    }

    public List<Employee> getEmployees() {
        return api.getEmployees(Tables.EMPLOYEES_TABLE_NAME);
    }

    /**
     * SHOULD ONLY BE CALLED WHEN THE PROGRAM IS CLOSING. Shuts down the pool of
     * threads used to execute the SQL queries. Needed for the program to fully
     * close after the main stage is closed. Otherwise, app is still running in
     * the background.
     */
    public void quit() {
        api.quit();
    }
}
