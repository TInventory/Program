package edu.mtu.tinventory.data;

import edu.mtu.tinventory.state.StateQtyMap;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class Product {
    // We only need one instance of this for all Products. May want to move to
    // some other class...
    public static final NumberFormat PRICE_FORMAT = NumberFormat.getCurrencyInstance(Locale.US);
    // The Model # / SKU / Unique Identifier
    private String id;
    // The human-friendly name
    private String name;
    // The quantity of items in each state
    private StateQtyMap quantities;
    // The cost of this item. Using BigDecimal to avoid imprecision of floating
    // point.
    private BigDecimal price;
    private ArrayList<String> tagList;

    /**
     * Used by the database to create a local object from the remote database.
     * When creating a new product locally, use the constructor.
     * 
     * @param id
     *            The unique ID for the product.
     * @param name
     *            The human-friendly name for the product.
     * @param price
     *            The unit-price for the product.
     * @param quantities
     *            The quantities in each state for the products. Format is
     *            specified in RegisterNewItem query.
     * @return a Product object with the specified information from the
     *         database.
     */
    public static Product createFromDatabse(String id, String name, String price, String quantities) {
        Product ret = new Product(id, name, price);
        ret.quantities = StateQtyMap.createFromString(quantities);
        return ret;
    }

    public Product(String id, String name, String price) {
        this.id = id;
        this.name = name;
        this.quantities = new StateQtyMap();
        // setScale limits to two decimal places, and uses Banker's
        // Rounding, which is apparently standard for US currency. The more
        // you know.
        this.price = new BigDecimal(price).setScale(2, RoundingMode.HALF_EVEN);
    }
    
    public Product(String id, String name, String price, ArrayList<String> tags) {
        this.id = id;
        this.name = name;
        this.quantities = new StateQtyMap();
        this.tagList = tags;
        // setScale limits to two decimal places, and uses Banker's
        // Rounding, which is apparently standard for US currency. The more
        // you know.
        this.price = new BigDecimal(price).setScale(2, RoundingMode.HALF_EVEN);
    }

    public String getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    // NOTE: Will need to update database after this call.
    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = new BigDecimal(price).setScale(2, RoundingMode.HALF_EVEN);
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getDisplayPrice() {
        return PRICE_FORMAT.format(price);
    }

    @Override
    public String toString() {
        return "Name:" + name + " ID:" + id + " Price:" + price;
    }

    public StateQtyMap getQuanity() {
        return quantities;
    }
    
    public ArrayList<String> getTags() {
    	return tagList;
    }
    
    public void setTags(ArrayList<String> tags) {
    	tagList = tags;
    }
}
