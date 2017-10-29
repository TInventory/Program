package edu.mtu.tinventory.database.query.queries;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import edu.mtu.tinventory.data.Product;
import edu.mtu.tinventory.database.query.ExecuteQuery;
import edu.mtu.tinventory.database.utils.DatabaseUtils;
import edu.mtu.tinventory.state.State;
import edu.mtu.tinventory.state.StateQtyMap;

public class GrabAllItems implements ExecuteQuery {

    private String table;
    private ArrayList<HashMap<String, Object>> data;
    private boolean waiting;

    public GrabAllItems(String table) {
        this.table = table;
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
            exception.printStackTrace();
        }
    }

    public List<Product> getProducts() {
        while (waiting) {
            // TODO: There's almost definitely a better way to wait on a thread,
            // but can't think of it atm.
            // Breaks without a method here.... I do not know
            System.out.print("");
        }

        return parseToProduct();
    }

    private List<Product> parseToProduct() {
        List<Product> products = new ArrayList<>();
        if (data != null) {
            for (HashMap<String, Object> map : data) {

                Product item = new Product(null, null, null);
                item.setID(map.get("id").toString());
                item.setName(map.get("name").toString());
                item.setPrice(map.get("price").toString());
                StateQtyMap newQuantity = StateQtyMap.createFromString(map.get("quantity").toString());
                item.setQuanity(newQuantity);

                products.add(item);

            }
        }
        return products;
    }
}
