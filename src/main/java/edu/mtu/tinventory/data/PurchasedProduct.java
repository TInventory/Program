package edu.mtu.tinventory.data;

import java.math.BigDecimal;

/**
 * Wrapper around Product to specify quantity purchased and the unit price.
 * This allows us to apply a one-time discount to an item.
 */
public class PurchasedProduct {
	private Product product;
	private int quantity;
	private BigDecimal unitPrice;
	private BigDecimal totalPrice;

	public PurchasedProduct(Product product, int quantity, BigDecimal unitPrice) {
		this.product = product;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
		this.totalPrice = unitPrice.multiply(new BigDecimal(quantity));
	}

	public String getProductID() {
		return product.getID();
	}

	public String getProductName() {
		return product.getName();
	}

	public int getQuantity() {
		return quantity;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public String getDisplayUnitPrice() {
		return Product.PRICE_FORMAT.format(unitPrice);
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public String getDisplayTotalPrice() {
		return Product.PRICE_FORMAT.format(totalPrice);
	}
}
