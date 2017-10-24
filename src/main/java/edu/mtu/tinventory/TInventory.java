package edu.mtu.tinventory;

import edu.mtu.tinventory.database.DatabaseInterface;
import edu.mtu.tinventory.database.utils.DatabaseUtils;
import edu.mtu.tinventory.gui.MainController;
import edu.mtu.tinventory.logging.LocalLog;
import java.util.logging.Level;
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
	    DatabaseUtils utils = new DatabaseUtils();
	    
		database = DatabaseInterface.getInstance();
		//initialDatabaseSetup(); //TODO: REINSTATE after Presentation
		
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
	    //TODO: Perhaps if one of these turns up false make error popup window
	    database.setupDataTable();
	    
//	    // TODO: Remove, is quick testing method
//	    Product product = new Product("K240", "AKG Studios", "70.00");
//	   // product.getQuanity().changeQty("Sold", 10);
//	    database.registerNewItem(product, database.dataTable);
	}

	private void setupLog() {
		if(getParameters().getRaw().contains("-trace")) {
			LocalLog.setupLog(Level.FINER);
		} else if(getParameters().getRaw().contains("-debug")) {
			LocalLog.setupLog(Level.FINE);
		} else {
			LocalLog.setupLog(Level.INFO);
		}
	}
}
