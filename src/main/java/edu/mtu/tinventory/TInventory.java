package edu.mtu.tinventory;

import java.util.logging.Level;

import edu.mtu.tinventory.data.Product;
import edu.mtu.tinventory.database.DatabaseInterface;
import edu.mtu.tinventory.gui.MainController;
import edu.mtu.tinventory.logging.LocalLog;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TInventory extends Application {
    private MainController controller;
    private DatabaseInterface database;

    @Override
    public void start(Stage stage) throws Exception {
        setupLog();
        
        database = DatabaseInterface.getInstance();
        initialDatabaseSetup(); 

        FXMLLoader loader = new FXMLLoader(TInventory.class.getResource("fxml/main.fxml"));
        BorderPane root = loader.load();
        controller = loader.getController();
        Scene scene = new Scene(root);
        stage.setTitle("TInventory");
        stage.setMinWidth(root.getMinWidth());
        stage.setMinHeight(root.getMinHeight());
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public MainController getController() {
        return controller;
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void initialDatabaseSetup() {
        // TODO: Perhaps if one of these turns up false make error popup window
         database.setupDataTable(null);

         /*
        // TODO: Remove, is quick testing method
        Product product = new Product("Aviator", "Sunglasses", "60.00");
        product.getQuanity().changeQty("Sold", 10);
        Product product1 = new Product("Studio M200", "AKG Headphones", "70.00");
        product1.getQuanity().changeQty("Rented", 3);
        Product product2 = new Product("Waddles", "Rubber Duck", "100000000");
        
        database.registerNewItem(product, null);
        database.registerNewItem(product1, null);
        database.registerNewItem(product2, null);
        */
        //database.deleteDataTable("inventory");
    }

    private void setupLog() {
        if (getParameters().getRaw().contains("-trace")) {
            LocalLog.setupLog(Level.FINER);
        } else if (getParameters().getRaw().contains("-debug")) {
            LocalLog.setupLog(Level.FINE);
        } else {
            LocalLog.setupLog(Level.INFO);
        }
    }
}
