package edu.mtu.tinventory.data;

import edu.mtu.tinventory.database.DatabaseInterface;
import edu.mtu.tinventory.logging.LocalLog;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
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
	private LocalDate date;
	// List of items on this invoice
	private List<PurchasedProduct> products;
	// The total amount
	private BigDecimal total;
	
	private Customer customer;

	/**
	 * Creates a new Invoice and stores it in the database.
	 * It is assumed that the Invoice is issued on the date that this method is called.
	 * @param products The list of products to be on this Invoice
	 * @param customer The customer that this Invoice is issued to
	 * @return The Invoice object created from this information, or null if the Database failed to create the Invoice.
	 */
	public static Invoice createNewInvoice(List<PurchasedProduct> products, Customer customer) {
		//TODO: Get the next unique Invoice # from a user-specified format. Probably from the database?
		//		Date should always be today. Maybe include a initializer for other dates, but would need a use case for it.
		Invoice i = new Invoice((int)(Math.random() * 100000), LocalDate.now(), products, customer);
		if(DatabaseInterface.getInstance().saveInvoice(i)) {
			return i;
		} else {
			return null;
		}
	}

	/**
	 * Creates and returns an Invoice object from a previously created Invoice that has been stored in the database.
	 * @param id The Invoice ID
	 * @param date The date the Invoice was issued
	 * @param customerID The ID for the customer that this Invoice was issued to (as it is stored in the database)
	 * @param products The list of products on this invoice (as it is stored in the database)
	 * @return The Invoice object to be used locally
	 */
	public static Invoice createFromDatabase(int id, String date, String customerID, String products) {
		return new Invoice(id, LocalDate.parse(date), parseProducts(products), DatabaseInterface.getInstance().getCustomer(customerID));
	}

	// This constructor should only be used for building an obj from the database.
	private Invoice(int id, LocalDate date, List<PurchasedProduct> products, Customer customer) {
		this.id = id;
		this.date = date;
		this.products = products;
		this.total = BigDecimal.ZERO;
		for(PurchasedProduct p : products) {
			total = total.add(p.getTotalPrice());
		}
		this.customer = customer;
	}
	public Customer getCustomer() {
		return customer;
	}
	public int getId() {
		return id;
	}

	public LocalDate getDate() {
		return date;
	}

	public List<PurchasedProduct> getProducts() {
		return products;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public String getProductsString() {
		StringBuilder sb = new StringBuilder();
		for(PurchasedProduct pp : products) {
			sb.append(pp).append(";");
		}
		return sb.deleteCharAt(sb.length() - 1).toString(); // Removes the trailing semicolon
	}

	private static List<PurchasedProduct> parseProducts(String serialized) {
		ArrayList<PurchasedProduct> ret = new ArrayList<>();
		int colon, colon2;
		for(String s : serialized.split(";")) {
			colon = s.indexOf(':');
			colon2 = s.lastIndexOf(':');
			Product p = DatabaseInterface.getInstance().getProduct(s.substring(0, colon));
			if(p == null) {
				LocalLog.warning("Invalid product found in invoice. It will be omitted from the local copy.");
			} else {
				ret.add(new PurchasedProduct(p, Integer.parseInt(s.substring(colon + 1, colon2)), new BigDecimal(s.substring(colon2 + 1))));
			}
		}

		return ret;
	}
}
