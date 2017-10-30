package edu.mtu.tinventory.database;

/**
 * 
 * @author JC Helm
 *
 */
public enum Tables {

    INVENTORY_TABLE_NAME("inventory"),
    DATABASE_NAME("tinventory"),
    CONFIGURATION_TABLE_NAME("configurations"),
    CUSTOMER_TABLE_NAME("customers"),
    INVOICE_TABLE_NAME("invoices"),

    TESTING_DATABASE("test_tinventory"),
    TESTING_INVENTORY_TABLE("test_inventory"),
    TESTING_CUSTOMER_TABLE("test_customers"),
    TESTING_CONFIGURATION_TABLE("test_configurations"),
    TESTING_INVOICES_TABLE("test_invoices");

    private String nameToString;

    Tables(String nameToString) {
        this.nameToString = nameToString;
    }

    public String nameToString() {
        return nameToString;
    }

    @Override
    public String toString() {
        return nameToString;
    }
}
