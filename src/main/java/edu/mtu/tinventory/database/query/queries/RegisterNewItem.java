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
    private String currency = "$";

    /**
     * Query for inserting product into database
     * 
     * @param product
     *            Product: product to be inserted into the database
     */
    public RegisterNewItem(String table, Product product) {
        name = product.getName();
        displayPrice = product.getDisplayPrice();
        iD = product.getID();
        price = product.getPrice().toPlainString();
        quantity = product.getQuanity();

        this.table = table;
    }

    @Override
    public String getQuery() {
        // SQL Statement
       /*
        return String.format(
                "INSERT INTO '%s' SET id='%s', name='%s', price='%s', displayprice='%s', currency='%s', quantity='%s' ;",
                table, iD, name, price, displayPrice, currency, quantityToString());
        */
        //INSERT INTO inventory VALUES ('id', 'name', 'price', 'display', 'currency', 'quantity');
        //INSERT INTO inventory VALUES ('id', 'name', 'price', 'display', 'currency', 'quant');
        return String.format("INSERT INTO inventory VALUES ('id', 'name', 'price', 'display', 'currency', 'quantity');"
                , iD,name,price,displayPrice,currency,quantityToString());
    }

    /**
     * 
     * Stores quantities string in following format:
     * State:Integer;State:Integer; etc.
     * Semicolon separates two entry sets
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
             * Semicolon separates two entry sets
             * Colon separates the entry set into the State and Integer
             */
            quantityString = quantityString + entry.getKey().toString() + ":" + entry.getValue() + ";";
        }

        // Removes the extra semicolon
        return quantityString.substring(0, quantityString.length() - 1);
    }
}
