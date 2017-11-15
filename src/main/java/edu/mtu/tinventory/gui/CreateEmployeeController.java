package edu.mtu.tinventory.gui;

import edu.mtu.tinventory.data.Access;
import edu.mtu.tinventory.data.Employee;
import edu.mtu.tinventory.database.DatabaseInterface;
import edu.mtu.tinventory.util.StringUtils;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class CreateEmployeeController extends Controller {
	@FXML private TextField firstName;
	@FXML private TextField lastName;
	@FXML private ChoiceBox<Access.Level> accessLevel;

	@FXML
	private void initialize() {
		accessLevel.setItems(FXCollections.observableArrayList(Access.Level.values()));
	}

	@FXML
	private void create() {
		if (StringUtils.isNullOrEmpty(firstName.getText())) {
			Dialogs.showDialog(Dialogs.Type.ERROR, null, "Please specify a first name.");
		} else if (StringUtils.isNullOrEmpty(lastName.getText())) {
			Dialogs.showDialog(Dialogs.Type.ERROR, null, "Please specify a last name.");
		} else if (accessLevel.getSelectionModel().isEmpty()) {
			Dialogs.showDialog(Dialogs.Type.ERROR, null, "Please select a valid access level.");
		} else {
			String id = Character.toUpperCase(firstName.getText().charAt(0)) +
					(lastName.getText().length() < 7 ? lastName.getText().toUpperCase() : lastName.getText().substring(0, 7).toUpperCase());
			if (DatabaseInterface.getInstance().doesEmployeeIDExist(id) && Dialogs.showYesNoDialog(Dialogs.Type.WARNING, "Employee ID already exists", String.format("An employee with the ID %s already exists. Would you like us to append numbers until we find a valid user id?", id))) {
				boolean valid = false;
				for(int i = 1; !valid; i++) {
					id = id.substring(0, 6) + i;
					valid = !DatabaseInterface.getInstance().doesEmployeeIDExist(id);
				}
			}
			String password = DatabaseInterface.getInstance().registerNewEmployee(new Employee(id, firstName.getText(), lastName.getText(), new Access(accessLevel.getSelectionModel().getSelectedItem())));
			if (!StringUtils.isNullOrEmpty(password)) {
				Dialogs.showDialog(Dialogs.Type.INFO, "Employee successfully created", String.format("ID is %s and password is %s", id, password));
			} else {
				Dialogs.showDialog(Dialogs.Type.ERROR, "Could not create new employee", "Check logs for more information.");
			}
			close();
		}
	}

	@FXML
	private void close() {
		stage.close();
	}
}
