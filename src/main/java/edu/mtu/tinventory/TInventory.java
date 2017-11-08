package edu.mtu.tinventory;

import edu.mtu.tinventory.data.Access;
import edu.mtu.tinventory.data.Employee;
import edu.mtu.tinventory.database.DatabaseInterface;
import edu.mtu.tinventory.database.utils.DatabaseUtils;
import edu.mtu.tinventory.gui.Controller;
import edu.mtu.tinventory.gui.IconLoader;
import edu.mtu.tinventory.gui.MainController;
import edu.mtu.tinventory.logging.LocalLog;
import java.io.IOException;
import java.util.logging.Level;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class TInventory extends Application {
    private DatabaseInterface database;
    private MainController mainController;
    private Stage primaryStage;
	private Employee loggedIn;
	private Image icon;
    public static boolean databaseFrozen = false;
    
    @Override
    public void start(Stage stage) throws Exception {
        setupLog();
        database = DatabaseInterface.getInstance();
        DatabaseUtils.checkStatus();
        icon = new IconLoader().getIcon();

        if (getParameters().getRaw().contains("-nologin")) { //TODO: Strip this in final program.
			LocalLog.info("Using Testing account.");
        	loggedIn = new Employee("TESTING", "Test", "Ing", new Access(Access.Level.ADMINISTRATOR));
		} else {
			showLoginDialog();
			this.primaryStage = stage;
			showMainWindow();
		}
    }

    public void showMainWindow() throws IOException {
		if (loggedIn != null) {
			FXMLLoader loader = new FXMLLoader(TInventory.class.getResource("fxml/main.fxml"));
			BorderPane root = loader.load();
			mainController = loader.getController();
			mainController.setMainApp(this);
			mainController.setStage(primaryStage);
			Scene scene = new Scene(root);
			primaryStage.setTitle(loggedIn.getFullName() + " - TInventory");
			primaryStage.getIcons().add(icon);
			primaryStage.setMinWidth(root.getMinWidth());
			primaryStage.setMinHeight(root.getMinHeight());
			primaryStage.setScene(scene);
			primaryStage.setMaximized(true);
			primaryStage.setOnCloseRequest(e -> database.quit());
			primaryStage.show();
		} else {
			database.quit();
			Platform.exit();
		}
	}

    public Stage getMainWindow() {
        return primaryStage;
    }

    public MainController getMainController() {
        return mainController;
    }

    public Image getIcon() {
        return icon;
    }

	public void showLoginDialog() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/login.fxml"));
		GridPane root = loader.load();
		Stage stage = new Stage();
		Controller c = loader.getController();
		c.setMainApp(this);
		c.setStage(stage);
		stage.setTitle("Login - TInventory");
		stage.setScene(new Scene(root));
		stage.getIcons().add(icon);
		stage.showAndWait();
	}

	public Employee getLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(Employee loggedIn) {
        this.loggedIn = loggedIn;
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
}
