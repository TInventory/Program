package edu.mtu.tinventory.database.query.queries;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import edu.mtu.tinventory.data.Invoice;
import edu.mtu.tinventory.database.Tables;
import edu.mtu.tinventory.database.query.ExecuteQuery;
import edu.mtu.tinventory.logging.LocalLog;
import edu.mtu.tinventory.util.DatabaseUtils;

/**
*
* @author JC Helm <jchelm@mtu.edu>
*
* @since 11/15/17
*
* Query executable to retrieve all invoices 
*/
public class GetAllInvoices implements ExecuteQuery {

	// Table to grab data from
	String table;
	// dataset of invoices
	private List<Invoice> invoices;
	// Waiting variable for SQL execution
	private boolean waiting;

	/**
	* Instance of GetAllInvoices that retrieves from table 
	*
	* @param table Table: Table enum of the table to select the data from
	*/
	public GetAllInvoices(Tables table) {
		this.table = table.toString();
		this.waiting = true;
	}

	@Override
	public String getQuery() {
		return String.format("SELECT * FROM %s", table);
	}

	@Override
	public void execute(ResultSet resultSet) {

		try {
			// Array of raw invoice data to be parsed
			ArrayList<HashMap<String, Object>> data = DatabaseUtils.getData(resultSet);
			if (data != null) {
				// Each hashmap is a seperate invoice , parse through the data		
				invoices = new ArrayList<>();
				for (HashMap<String, Object> e : data) {
					invoices.add(Invoice.createFromDatabase(Integer.parseInt(e.get("id").toString()),
							e.get("date").toString(), e.get("customer").toString(), e.get("items").toString()));
				}
			}
			// SQL execution finished, enable retrieval  of data
			waiting = false;
		} catch (SQLException e) {
			LocalLog.exception(e);
		}

	}
	
	/**
	* Retrieves the a list of all Invoives() from the database.
	*
	*@return List<Invoices> from the database
	*/
	public List<Invoice> getInvoices() {
		// Holds waiting status until SQL Query is finished
		while (waiting) {
			System.out.print("");
		}
		// Getter
		return invoices;
	}

}
