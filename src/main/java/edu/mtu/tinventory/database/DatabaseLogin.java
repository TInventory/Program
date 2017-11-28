package edu.mtu.tinventory.database;

import java.io.IOException;

import edu.mtu.tinventory.TInventory;
import edu.mtu.tinventory.gui.Controller;
import edu.mtu.tinventory.gui.DatabaseLoginController;
import edu.mtu.tinventory.logging.LocalLog;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * 
 * @author JC Helm
 * @since 11-28-17
 *
 * Runnable to allow the main thread to wait until 
 */
public class DatabaseLogin implements Runnable {

    @Override
    public void run()  {
      // Shows the database login box
           try {
               new DatabaseLoginController().showDatabaseLogin();
        } catch (IOException exception) {
          LocalLog.exception(exception);
        }
    }
}
