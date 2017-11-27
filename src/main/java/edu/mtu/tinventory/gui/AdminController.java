package edu.mtu.tinventory.gui;

import edu.mtu.tinventory.TInventory;
import edu.mtu.tinventory.data.Employee;
import edu.mtu.tinventory.database.DatabaseInterface;
import edu.mtu.tinventory.logging.LocalLog;
import edu.mtu.tinventory.util.StringUtils;
import java.io.IOException;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
	@FXML private Button access;
	@FXML private Button override;
	@FXML private Button reset;

	@FXML
	private void initialize() {
		colID.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getID()));
		colFirstName.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getFirstName()));
		colLastName.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getLastName()));
		colAccess.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getAccess().getLevel().name()));
		colOverrides.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getAccess().getOverridesString()));
		table.setItems(FXCollections.observableList(DatabaseInterface.getInstance().getEmployees()));
		table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			boolean disable = (newValue == null);
			access.setDisable(disable);
			override.setDisable(disable);
			reset.setDisable(disable);
		});
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
			initialize();
		} catch (IOException e) {
			LocalLog.exception("Failed to open createEmployee dialog.", e);
			Dialogs.showDialogWithException("Couldn't open window", "Failed to open Add Employee window.", e);
		}
	}

	@FXML
	private void changeAccess() {
		try {
			FXMLLoader loader = new FXMLLoader(TInventory.class.getResource("fxml/changeAccessLevel.fxml"));
			GridPane pane = loader.load();
			AdminSubController c = loader.getController();
			Stage stage = new Stage();
			c.setMainApp(mainApp);
			c.setStage(stage);
			c.setSelectedEmployee(table.getSelectionModel().getSelectedItem());
			stage.initOwner(this.stage);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.setScene(new Scene(pane));
			stage.setTitle(String.format("Change Access Level - %s - TInventory", table.getSelectionModel().getSelectedItem().getFullName()));
			stage.getIcons().add(mainApp.getIcon());
			stage.showAndWait();
			DatabaseInterface.getInstance().forceUpdateCache();
			initialize();
		} catch (IOException e) {
			LocalLog.exception("Failed to open changeAccessLevel dialog.", e);
			Dialogs.showDialogWithException("Couldn't open window", "Failed to open Change Access Level window.", e);
		}
	}

	@FXML
	private void changeOverrides() {

	}

	@FXML
	private void resetPassword() {
		Employee e = table.getSelectionModel().getSelectedItem();
		if (Dialogs.showYesNoDialog(Dialogs.Type.WARNING, "Confirm Password Reset",
				String.format("Are you sure you want to reset %s's password?", e.getFullName()))) {
			if (DatabaseInterface.getInstance().changePassword(e, StringUtils.getDefaultPassword(e))) {
				Dialogs.showDialog(Dialogs.Type.INFO, "Password Successfully Reset",
						String.format("%s's new password is %s", e.getFullName(), StringUtils.getDefaultPassword(e)));
			} else {
				Dialogs.showDialog(Dialogs.Type.ERROR, "Failed to Reset Password", "Database error. Check the logs for more information.");
			}
		}
	}
}
