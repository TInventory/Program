package edu.mtu.tinventory.gui;

import edu.mtu.tinventory.data.Access;
import edu.mtu.tinventory.database.DatabaseInterface;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class ChangeAccessLevelController extends AdminSubController {
    @FXML private ChoiceBox<Access.Level> levels;

    @FXML
    private void initialize() {
        levels.setItems(FXCollections.observableArrayList(Access.Level.values()));
    }

    @FXML
    private void change() {
        if (levels.getSelectionModel().isEmpty()) {
            Dialogs.showDialog(Dialogs.Type.ERROR, null, "Please select an access level.");
        } else {
            Access access = new Access(levels.getSelectionModel().getSelectedItem(), employee.getAccess().getOverrides());
            if (DatabaseInterface.getInstance().updateAccess(employee, access) != null) {
                Dialogs.showDialog(Dialogs.Type.INFO, null, "Access Level was successfully updated.");
            } else {
                Dialogs.showDialog(Dialogs.Type.ERROR, "Error in Database Communication", "Check the logs for more information.");
            }
            close();
        }
    }

    @FXML
    private void close() {
        stage.close();
    }
}
