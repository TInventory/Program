package edu.mtu.tinventory.gui;

import edu.mtu.tinventory.data.Employee;
import edu.mtu.tinventory.database.DatabaseInterface;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController extends Controller {
	@FXML private TextField id;
	@FXML private PasswordField password;

	@FXML
	private void attemptLogin() {
		Employee employee = DatabaseInterface.getInstance().getEmployee(id.getText().toUpperCase(), password.getText());
		if (employee != null) {
			mainApp.setLoggedIn(employee);
			close();
		} else {
			Dialogs.showDialog(Dialogs.Type.ERROR, null, "Invalid credentials.");
		}
	}

	@FXML
	private void close() {
		stage.close();
	}
}
