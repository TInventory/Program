package edu.mtu.tinventory.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledFuture;
import edu.mtu.tinventory.database.query.ExecuteQuery;
import edu.mtu.tinventory.database.query.Query;
import edu.mtu.tinventory.database.query.queries.ChangeConfigTable;
import edu.mtu.tinventory.database.query.queries.CheckConfigurations;
import edu.mtu.tinventory.logging.LocalLog;
import edu.mtu.tinventory.util.DatabaseUtils;

/**
 * 
 * @author James Helm
 * @since 10/12/2017
 * 
 *        Used to establish regular queue commands Likely will be used to auto
 *        update inventory
 */

// TODO: Not needed for sprint 1, avoiding for now, need to do as task scheduler
public class Consumer implements Runnable {

	/**
	 * Instance of the database being accessed
	 */
	private Database db;

	/**
	 * Instance of this class, non sync here would be catastrophic
	 */
	private static Consumer instance;

	/**
	 * task item
	 */
	ScheduledFuture<?> task;
	private Queue<Query> queries;

	/**
	 * Adds queries to a queue so that many can be done at once placing less
	 * stress on the database and increasing efficiency.
	 * 
	 * @param query
	 *            Query to add to the update queue
	 */
	public static synchronized void queue(Query query) {
		// check for instance existence
		if (getInstance() != null) {
			// add to query setup
			getInstance().queries.add(query);
		} else {
			// maybe make some form of custom exception for here?
			LocalLog.error("NO DATABASE CONNECTION ESTABLISHED");
		}
	}

	/**
	 * Getter for class instance
	 * 
	 * @return Instance of Consumer
	 */
	public static Consumer getInstance() {
		return instance;
	}

	/**
	 * Class Constructor:
	 * 
	 * Creates the queue statement panel for database db
	 * 
	 * @param db
	 *            Database to setup queue for
	 */
	public Consumer(Database db, ScheduledFuture<?> task) {
		// Link to class variable
		this.task = task;
		this.db = db;
		// assign this instance of the class as the local instance
		instance = this;

		// Creates the Linked Queue to hold Statements
		this.queries = new LinkedBlockingQueue<Query>();

	}

	/**
	 * Stops the scheduler from running, stopping queues from being send
	 * automatically
	 */
	public void stop() {
		task.cancel(false);
	}

	/**
	 * Takes all the Statements in the linked queue, and sends them to the
	 * database
	 */
	@Override
	public void run() {
		if (!queries.isEmpty()) {
			if (db.isConnected()) {
				Query query;
				while ((query = queries.poll()) != null) {
					// debug message
					// System.out.println(query.getQuery());
					try {

						if (DatabaseUtils.isDatabaseFrozen()
								&& (!(query instanceof CheckConfigurations) || !(query instanceof ChangeConfigTable))) {
							LocalLog.error(String.format("Database actions are frozen!  Request of %s has failed!",
									query.getClass().getName()));

							return;
						}

						// Ensure the query is in a proper form
						if (query instanceof ExecuteQuery) {

							ExecuteQuery executeQuery = (ExecuteQuery) query;

							// Creates the statement to send
							Statement statement = db.createStatement();
							// Prepares the resultant set
							ResultSet set = statement.executeQuery(query.getQuery());

							// Actually send out the statement
							executeQuery.execute(set);

							// Close streams, to stop memory leaks
							set.close();
							statement.close();

						} else {
							// Retrieve statement
							String q = query.getQuery();

							if (q != null) {
								Statement statement = db.createStatement();
								// Send out the statement
								statement.execute(q);
							}
						}
					}
					// catch the exceptions
					catch (SQLException e) {
						LocalLog.exception(String.format("Query %s threw an exception", query.getClass().getSimpleName()), e);
					}
				}
			}
		}
	}
}
