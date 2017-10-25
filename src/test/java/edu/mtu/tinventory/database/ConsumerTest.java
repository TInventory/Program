package edu.mtu.tinventory.database;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ConsumerTest {

    //TODO: Can't be used until DatabaseInterface is finished
    Consumer consumer;
    
    @Before
    public void setup() {
       MySQL sql = new MySQL("tinventory", "taco", "tinventory", "kiro47.ddns.net", 9998);
       DatabaseSetup setup = new DatabaseSetup();
       setup.createDatabase("JunitTesting");
       setup.setupDataTable("JUnit");
        consumer = new Consumer(sql, null);
    }
    
    @Test
    public void queue() {
        
    }
    
    @After
    public void closingTime() {
        DatabaseInterface data = DatabaseInterface.getInstance();
        data.setupDatabase("JUnitTesting");
        data.deleteDataTable("JUnit");
    }
}
