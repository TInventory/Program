package edu.mtu.tinventory.gui;

import edu.mtu.tinventory.data.Access;
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

	}

	@FXML
	private void close() {
		stage.close();
	}
}
