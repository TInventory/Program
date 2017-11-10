package edu.mtu.tinventory.gui;

import edu.mtu.tinventory.TInventory;
import edu.mtu.tinventory.data.Employee;
import edu.mtu.tinventory.database.DatabaseInterface;
import edu.mtu.tinventory.logging.LocalLog;
import java.io.IOException;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AdminController extends Controller {
	@FXML private TableView<Employee> table;
	@FXML private TableColumn<Employee, String> colID;
	@FXML private TableColumn<Employee, String> colFirstName;
	@FXML private TableColumn<Employee, String> colLastName;
	@FXML private TableColumn<Employee, String> colAccess;
	@FXML private TableColumn<Employee, String> colOverrides;

	@FXML
	private void initialize() {
		colID.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getID()));
		colFirstName.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getFirstName()));
		colLastName.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getLastName()));
		colAccess.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getAccess().getLevel().name()));
		colOverrides.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getAccess().getOverridesString()));
		table.setItems(FXCollections.observableList(DatabaseInterface.getInstance().getEmployees()));
	}

	@FXML
	private void addEmployee() {
		try {
			FXMLLoader loader = new FXMLLoader(TInventory.class.getResource("fxml/createEmployee.fxml"));
			GridPane pane = loader.load();
			Controller c = loader.getController();
			Stage stage = new Stage();
			c.setMainApp(mainApp);
			c.setStage(stage);
			stage.initOwner(this.stage);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.setScene(new Scene(pane));
			stage.setTitle("Create Employee - TInventory");
			stage.getIcons().add(mainApp.getIcon());
			stage.showAndWait();
		} catch (IOException e) {
			LocalLog.exception("Failed to open createEmployee dialog.", e);
			Dialogs.showDialogWithException("Couldn't open window", "Failed to open Add Employee window.", e);
		}
	}

	@FXML
	private void changeAccess() {

	}

	@FXML
	private void changeOverrides() {

	}

	@FXML
	private void resetPassword() {

	}
}
