package edu.mtu.tinventory;

import edu.mtu.tinventory.data.Employee;
import edu.mtu.tinventory.database.DatabaseInterface;
import edu.mtu.tinventory.database.utils.DatabaseUtils;
import edu.mtu.tinventory.gui.IconLoader;
import edu.mtu.tinventory.gui.MainController;
import edu.mtu.tinventory.logging.LocalLog;
import java.util.logging.Level;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TInventory extends Application {
    private DatabaseInterface database;
    private MainController mainController;
    private Stage primaryStage;
	private Employee loggedIn;
    public static boolean databaseFrozen = false;
    
    @Override
    public void start(Stage stage) throws Exception {
        setupLog();
        database = DatabaseInterface.getInstance();
        DatabaseUtils.checkStatus();

		if ((loggedIn = showLoginDialog()) != null) {
			this.primaryStage = stage;
			FXMLLoader loader = new FXMLLoader(TInventory.class.getResource("fxml/main.fxml"));
			BorderPane root = loader.load();
			mainController = loader.getController();
			mainController.setMainApp(this);
			mainController.setStage(stage);
			Scene scene = new Scene(root);
			stage.setTitle("TInventory");
			stage.getIcons().add(new IconLoader().getIcon());
			stage.setMinWidth(root.getMinWidth());
			stage.setMinHeight(root.getMinHeight());
			stage.setScene(scene);
			stage.setMaximized(true);
			stage.setOnCloseRequest(e -> database.quit());
			stage.show();
		} else {
			//TODO
		}
    }

    public Stage getMainWindow() {
        return primaryStage;
    }

    public MainController getMainController() {
        return mainController;
    }

    public static void main(String[] args) {
        launch(args);
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

    private Employee showLoginDialog() {
		return null; //TODO
	}
}
