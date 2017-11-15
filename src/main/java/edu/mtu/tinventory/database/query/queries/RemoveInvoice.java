package edu.mtu.tinventory.database.query.queries;

import edu.mtu.tinventory.data.Invoice;
import edu.mtu.tinventory.database.Tables;
import edu.mtu.tinventory.database.query.Query;

/**
 * 
 * @author JC Helm <jchelm@mtu.edu>
 * @since 11/15/17
 *
 *        Query to remove a single invoice from the database.
 */
public class RemoveInvoice implements Query {

    private Invoice invoice;
    private Tables table;

    public RemoveInvoice(Invoice invoice) {
        this.invoice = invoice;
        this.table = Tables.INVOICE_TABLE_NAME;

    }

    @Override
    public String getQuery() {
        return String.format("DELETE FROM %s WHERE id=%s;", table.toString(), "'" + invoice.getId() + "'");
    }

}
