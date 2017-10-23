package edu.mtu.tinventory.database;

import static org.junit.Assert.assertEquals;

import java.util.Random;
import java.util.UUID;

import org.junit.Test;

import edu.mtu.tinventory.database.utils.DatabaseUtils;

public class DatabaseUtilsTest {
    //TODO: Add more testing parameters for entire DatabaseUtils class
    static DatabaseUtils utils = new DatabaseUtils();
    
    @Test
    public void stripUUID() {
        Random ran = new Random();
        UUID uuid = new UUID(ran.nextLong(), ran.nextLong());
        String conv = uuid.toString().replace("-", "");
        assertEquals(conv ,utils.strip(uuid));
    }
    
    @Test
    public void getTimeStamp() {
     // 2/17/2017 @ 11:02:52am (UTC)
      long time = 1508572800;
      String timestamp = utils.getTimeString(time, true);
      assertEquals("17 d 11 h 2 m 52 s", timestamp);
    }
    
   
   // @Test
    public void getTime() {
      // 2/17/2017 @ 11:02:52am (UTC)
      long timestamp = utils.getTime("17 d 11 h 2 m 52 s");
      assertEquals(1508572800, timestamp);
    }
}
