package edu.mtu.tinventory;

import edu.mtu.tinventory.data.Product;
import edu.mtu.tinventory.database.DatabaseInterface;
import edu.mtu.tinventory.gui.Dialogs;
import edu.mtu.tinventory.logging.LocalLog;
import java.util.List;
import java.util.logging.Level;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TInventory extends Application {
    private DatabaseInterface database;

    @Override
    public void start(Stage stage) throws Exception {
        setupLog();
        database = DatabaseInterface.getInstance();
        initialDatabaseSetup(); 

        FXMLLoader loader = new FXMLLoader(TInventory.class.getResource("fxml/main.fxml"));
        BorderPane root = loader.load();
        Scene scene = new Scene(root);
        stage.setTitle("TInventory");
        stage.setMinWidth(root.getMinWidth());
        stage.setMinHeight(root.getMinHeight());
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setOnCloseRequest(e -> database.quit());
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void initialDatabaseSetup() {
        if(!database.setupDatabase()) {
            Dialogs.showDialogWithException("Database setup failed", "Failed to setup necessary tables for operation. Check below for exact error.", LocalLog.getLastLoggedException());
            database.quit();
            Platform.exit();
        }

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
         
         //This was commented out
         /*
       List<Product> list =  database.getProducts();
       System.out.println(list);
        for (Product pro : list) {
             System.out.println(pro.getName() + ", "  + pro.getID() + ", " + pro.getDisplayPrice() + ", " + pro.getQuanity().getMap().toString() ); 
         }
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
