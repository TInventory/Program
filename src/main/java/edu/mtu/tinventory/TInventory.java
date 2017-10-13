package edu.mtu.tinventory;

import edu.mtu.tinventory.database.DatabaseInterface;
import edu.mtu.tinventory.gui.MainController;
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
		database = DatabaseInterface.getInstance();
		
		FXMLLoader loader = new FXMLLoader(TInventory.class.getResource("fxml/main.fxml"));
		BorderPane root = loader.load();
		controller = loader.getController();
		Scene scene = new Scene(root);

		stage.setTitle("TInventory");
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
}
