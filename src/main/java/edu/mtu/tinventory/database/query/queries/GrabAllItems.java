package edu.mtu.tinventory.database.query.queries;

import edu.mtu.tinventory.logging.LocalLog;
import edu.mtu.tinventory.util.DatabaseUtils;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import edu.mtu.tinventory.data.Product;
import edu.mtu.tinventory.database.Tables;
import edu.mtu.tinventory.database.query.ExecuteQuery;

/**
 * 
 * @author JC Helm
 * Query to retrieve all items from the table: table
 *
 */
public class GrabAllItems implements ExecuteQuery {

	// Table name to retrieve products from
    private String table;
	// Raw dataset of product information to be parsed
    private ArrayList<HashMap<String, Object>> data;
	// SQL Execution waiting boolean
	private boolean waiting;

    /**
     * Constructor
     * Grabs all products from the table
     * 
     * @param table Name of the table to grab from
     */
    public GrabAllItems(Tables table) {
        this.table = table.toString();
        data = null;
        waiting = true;
    }

    @Override
    public String getQuery() {
        return String.format("SELECT * FROM %s;", table);
    }

    @Override
    public void execute(ResultSet resultSet) {
        try {
            // TODO: Read on parsing data
            ArrayList<HashMap<String, Object>> data = DatabaseUtils.getData(resultSet);
            // System.out.println(data);
            this.data = data;
            waiting = false;

        } catch (Exception exception) {
            LocalLog.exception(exception);
        }
    }

    /**
     * Retrieves the products from the alterneritive thread
     * @return The List of products from the query
     */
    public List<Product> getProducts() {
        while (waiting) {
            // TODO: There's almost definitely a better way to wait on a thread,
            // but can't think of it atm.
            // Breaks without a method here.... I do not know
            System.out.print("");
        }

        return parseToProduct();
    }

    /**
     * Parses HashMap of knowns to List of Products
     * @return Array List of Products
     */
    private List<Product> parseToProduct() {
	// new array list to store Product objects in
        List<Product> products = new ArrayList<>();
	// ensure there are products in the database
        if (data != null) {
		// Iterate through raw data and parse into a list of Product objects
            for (HashMap<String, Object> map : data) {
                products.add(Product.createFromDatabse(map.get("id").toString(), map.get("name").toString(), map.get("price").toString(), map.get("quantity").toString()));
            }
        }
	// Return list of products (Or an empty list).
        return products;
    }
}
