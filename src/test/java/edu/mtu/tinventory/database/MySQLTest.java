package edu.mtu.tinventory.database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.sql.Connection;

import org.junit.Before;
import org.junit.Test;

public class MySQLTest {

    MySQL sql;

    @Before
    public void setup() {
        sql = new MySQL("tinventory", "taco", "tinventory", "kiro47.ddns.net", 9998);
    }

    //@Test
    public void retrieveConnection() {

        if (!(sql.getConnection() instanceof Connection)) {
            fail("SQL Connection is not an instance of Connection...Something has gone terribly wrong and no idea how this happened.  Burn the codebase");
        }

    }

    @Test
    public void testConnection() {
        // Must have network connection
        assertEquals("SQL Connection to server failed, check to ensure internet connection is online.", true,
                sql.connect());
    }

    @Test
    public void isConnectedConnection() {
        // Must have network connection
        try {
            sql.connect();
        } catch (Exception exception) {
            //
        }
        assertEquals(
                "SQL Connection to server failed, check to ensure internet connection is online. Check test : 'testConnection'",
                true, sql.isConnected());
    }
}
