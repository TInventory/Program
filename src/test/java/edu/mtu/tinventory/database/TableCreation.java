package edu.mtu.tinventory.database;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class TableCreation {

    DatabaseAPI api;

    @Before
    public void before() {
        MySQL sql = new MySQL("tinventory", "taco", "tinventory", "kiro47.ddns.net", 9998);
        
        try {
            this.api = new DatabaseAPI(sql, true);
        } catch (Exception exception) {
            Assert.fail("Method: startupTesting() Failed: Failed from " + exception.getCause());
        }
        // Passes by default

    }

    @Test 
    public void setupDatabase() {
        Assert.assertTrue("Testing database failed to be created",api.setupInventoryDatabase(Tables.TESTING_DATABASE));
    }
    
    @Test
    public void setupDatabaseTables() {
        Assert.assertTrue("Test: setupDatabase() Line 27. Database failed to be created", api.setupDatabase(true));
    }
    
    @Test
    public void setupConfigTable() {
       Assert.assertTrue("Testing Config Table failed to be created", api.createConfigTable(Tables.TESTING_CONFIGURATION_TABLE));
    }
    
    @Test
    public void commons() {
        Assert.assertTrue("Will always pass as long as it is reached", true);
    }
    
    @After
    public void after() {
        // shutdown threads
        api.quit();
        // erase from memory
        api = null;
        System.exit(0);
    }
}
