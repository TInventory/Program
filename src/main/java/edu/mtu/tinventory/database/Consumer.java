package edu.mtu.tinventory.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import edu.mtu.tinventory.database.query.ExecuteQuery;
import edu.mtu.tinventory.database.query.Query;

/**
 * 
 * @author James Helm
 * @since 10/12/2017
 * 
 *        Used to establish regular queue commands
 *        Likely will be used to auto update inventory
 */
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
     * Will need to be replaced with scheduler
     */
    // TODO: Replace with scheduler
    // Remove suppression later, just used for now because it annoys me
    @SuppressWarnings("unused")
    private Object task;

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
     * 
     * @param db
     */
    // TODO: docs
    public Consumer(Database db) {
        this.db = db;
        instance = this;

        this.queries = new LinkedBlockingQueue<Query>();

        // TODO: Need to replace with scheduler
        this.task = null;
    }

    /**
     * Stops the scheduler from running, stopping queues from being send
     * automatically
     */
    public void stop() {
        // TODO: Cancel the scheduler
    }

    /**
     * 
     */
    // TODO: docs
    @Override
    public void run() {
        if (!queries.isEmpty()) {
            if (db.isConnected()) {
                Query query;
                while ((query = queries.poll()) != null) {
                    // debug message
                    // System.out.println(query.getQuery());
                    try {
                        if (query instanceof ExecuteQuery) {
                            ExecuteQuery executeQuery = (ExecuteQuery) query;

                            Statement statement = db.createStatement();
                            ResultSet set = statement.executeQuery(query.getQuery());

                            executeQuery.execute(set);

                            set.close();
                            statement.close();

                        } else {
                            String q = query.getQuery();
                            if (q != null) {
                                Statement statement = db.createStatement();
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
