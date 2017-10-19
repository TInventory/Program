package edu.mtu.tinventory.data;

import edu.mtu.tinventory.state.StateQtyMap;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

public class Product {
	// We only need one instance of this for all Products. May want to move to some other class...
	public static final NumberFormat PRICE_FORMAT = NumberFormat.getCurrencyInstance(Locale.US); //TODO: Maybe localization?
	// The Model # / SKU / Unique Identifier
	private String id;
	// The human-friendly name
	private String name;
	// The quantity of items in each state
	// TODO: Create proxy methods in this class? Something to get qtys.
	private final StateQtyMap quantities;
	// The cost of this item. Using BigDecimal to avoid imprecision of floating point.
	private BigDecimal price;

	public Product(String id, String name, String price) {
		this.id = id;
		this.name = name;
		this.quantities = new StateQtyMap();
		// setScale limits to two decimal places, and uses Banker's Rounding, which is apparently standard for US currency. The more you know.
		this.price = new BigDecimal(price).setScale(2, RoundingMode.HALF_EVEN);
	}

	public String getID() {
		return id;
	}

	public String getName() {
		return name;
	}

	//NOTE: Will need to update database after this call.
	public void setName(String name) {
		this.name = name;
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
}
