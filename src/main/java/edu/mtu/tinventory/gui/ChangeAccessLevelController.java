package edu.mtu.tinventory.gui;

import edu.mtu.tinventory.data.Access;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class ChangeAccessLevelController extends Controller {
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
            //TODO: Query
        }
    }

    @FXML
    private void close() {
        stage.close();
    }
}
