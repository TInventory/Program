package edu.mtu.tinventory.database.query;

import edu.mtu.tinventory.database.DatabaseInterface;
import edu.mtu.tinventory.util.DatabaseUtils;

/**
 * 
 * Used as a runnable to check if the database is frozen or not
 * 
 * @author James Helm
 * @since 11/11/17
 *
 */
public class StatusUpdater implements Runnable{

    DatabaseInterface dInterface = DatabaseInterface.getInstance();
    @Override
    public void run() {
        if (DatabaseUtils.isDatabaseFrozen()) {
            dInterface.accessible = false;
        }
        else {
            dInterface.accessible = true;
        }
        
    }

}
