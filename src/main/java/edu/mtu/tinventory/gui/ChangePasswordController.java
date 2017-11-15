package edu.mtu.tinventory.gui;

import edu.mtu.tinventory.data.Employee;
import edu.mtu.tinventory.database.DatabaseInterface;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;

public class ChangePasswordController extends Controller {
    @FXML private PasswordField oldPass;
    @FXML private PasswordField newPass;
    @FXML private PasswordField confirmPass;

    @FXML
    private void validate() {
        Employee employee = DatabaseInterface.getInstance().getEmployee(mainApp.getLoggedIn().getID(), oldPass.getText());
        if (employee != null && employee.equals(mainApp.getLoggedIn())) {
            oldPass.setDisable(true);
            newPass.setDisable(false);
            confirmPass.setDisable(false);
        } else {
            Dialogs.showDialog(Dialogs.Type.ERROR, null, "Password does not match.");
        }
    }

    @FXML
    private void change() {
        if (newPass.getText().equals(confirmPass.getText())) {
            if (DatabaseInterface.getInstance().changePassword(mainApp.getLoggedIn(), newPass.getText())) {
                Dialogs.showDialog(Dialogs.Type.INFO, null, "Password was changed successfully.");
                close();
            } else {
                Dialogs.showDialog(Dialogs.Type.ERROR, "Could not change password", "Check logs for more information.");
            }
        } else {
            Dialogs.showDialog(Dialogs.Type.ERROR, null, "Passwords do not match.");
        }
    }

    @FXML
    private void close() {
        stage.close();
    }
}
