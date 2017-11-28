package edu.mtu.tinventory.gui;

import java.io.IOException;

import edu.mtu.tinventory.TInventory;
import edu.mtu.tinventory.database.Configurations;
import edu.mtu.tinventory.database.MySQL;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class DatabaseLoginController extends Controller {
    
    /** User name for SQL Login */
    @FXML private TextField username;
    /** Password for SQL Login */
    @FXML private PasswordField password;
    /** Name of the database to connect to */
    @FXML private TextField database;
    /** URL of host to connect to */
    @FXML private TextField host;
    /** Port to connect to for SQL */
    @FXML private TextField port;

    @FXML
    private void attemptConnection() {
        MySQL sql = new MySQL(username.getText(), password.getText(), database.getText(), host.getText(), Integer.parseInt(port.getText()));
        if (sql.connect()) {
            stage.close();
            Configurations.mainThread.notify();
        }
        else {
            Dialogs.showDialog(Dialogs.Type.ERROR, null, "Invalid connection information!");
        }
    }

    @FXML
    public void exit() {
        stage.close();
        System.gc();
        // shutdown the program
        System.exit(0);
    }
    
    
    public void showDatabaseLogin() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/databaseLogin.fxml"));
        GridPane root;
        //TODO: fails here with java.lang.IllegalStateException: Location is not set.
        root = loader.load();
        Stage stage = new Stage();
        Controller c = loader.getController();
        c.setStage(stage);
        stage.setTitle("Login - TInventory");
        stage.setScene(new Scene(root));
        stage.getIcons().add(TInventory.icon);
        stage.showAndWait();
   
    }
}
