package edu.mtu.tinventory.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import edu.mtu.tinventory.database.query.ExecuteQuery;
import edu.mtu.tinventory.database.query.Query;

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
	 * Task used as a pool of seperate threads to handle queued quarries
	 */
	private final ScheduledExecutorService task;

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
			System.out.println("Error ---- ");
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
	public Consumer(Database db) {
		// Link to class variable
		this.db = db;
		// assign this instance of the class as the local instance
		instance = this;

		// Creates the Linked Queue to hold Statements
		this.queries = new LinkedBlockingQueue<Query>();

		// Creates a new pool of threads to handle query system
		this.task = Executors.newScheduledThreadPool(1);
		
				//TODO: Currently hard coded, need to be added as a config via config SQL table
		final long initialDelay = 10;
				//TODO: Currently hard coded, need to be added as a config via config SQL table
		final long period = 5 * 60; // convert from minutes to seconds
		
		// Runs task at run() , starting after initial Delay of config file and repeats this action for every period
// TODO: Needs to be outside of the class.
		//task.scheduleAtFixedRate(run(), initialDelay, period, TimeUnit.SECONDS);
	}

	/**
	 * Stops the scheduler from running, stopping queues from being send
	 * automatically
	 */
	public void stop() {
		
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
						e.printStackTrace();
					}
				}
			}
		}
	}
}
