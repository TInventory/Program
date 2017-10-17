package edu.mtu.tinventory.data;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Represent a invoiced sale, where the customer has been billed.
 * For most purposes, this is essentially the completed sale, as they will pay on the spot.
 * A receipt, if you will.
 */
public class Invoice {
	// Unique Invoice Number
	private int id;
	// Date the invoice was generated
	private Date date;
	// List of items on this invoice
	private List<PurchasedProduct> products;
	// The total amount
	private BigDecimal total;

	public static Invoice createNewInvoice(PurchasedProduct... products) {
		//TODO: Get the next unique Invoice # from a user-specified format. Probably from the database?
		//		Date should always be today. Maybe include a initializer for other dates, but would need a use case for it.
		//		Will also need to sync to the database.
		return new Invoice((int)(Math.random() * 1000), new Date(), Arrays.asList(products));
	}

	// This constructor should only be used for building an obj from the database.
	private Invoice(int id, Date date, List<PurchasedProduct> products) {
		this.id = id;
		this.date = date;
		this.products = products;
		this.total = BigDecimal.ZERO;
		for(PurchasedProduct p : products) {
			total = total.add(p.getTotalPrice());
		}
	}

	public int getId() {
		return id;
	}

	public Date getDate() {
		return date;
	}

	public List<PurchasedProduct> getProducts() {
		return products;
	}

	public BigDecimal getTotal() {
		return total;
	}
}
