package edu.mtu.tinventory;

import java.util.logging.Level;

import edu.mtu.tinventory.database.DatabaseInterface;
import edu.mtu.tinventory.database.utils.DatabaseUtils;
import edu.mtu.tinventory.gui.Dialogs;
import edu.mtu.tinventory.gui.MainController;
import edu.mtu.tinventory.logging.LocalLog;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TInventory extends Application {
    private DatabaseInterface database = DatabaseInterface.getInstance();
    private MainController mainController;
    private Stage primaryStage;
    public static boolean databaseFrozen = false;
    
    @Override
    public void start(Stage stage) throws Exception {
        setupLog();
        initialDatabaseSetup(); 
        DatabaseUtils.checkStatus();
        
        this.primaryStage = stage;
        FXMLLoader loader = new FXMLLoader(TInventory.class.getResource("fxml/main.fxml"));
        BorderPane root = loader.load();
        mainController = loader.getController();
        mainController.setMainApp(this);
        mainController.setStage(stage);
        Scene scene = new Scene(root);
        stage.setTitle("TInventory");
        stage.setMinWidth(root.getMinWidth());
        stage.setMinHeight(root.getMinHeight());
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setOnCloseRequest(e -> database.quit());
        stage.show();
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

    private void initialDatabaseSetup() {
        if(!database.setupDatabase()) {
            Dialogs.showDialogWithException("Database setup failed", "Failed to setup necessary tables for operation. Check below for exact error.", LocalLog.getLastLoggedException());
            database.quit();
            Platform.exit();
        } 
        
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
