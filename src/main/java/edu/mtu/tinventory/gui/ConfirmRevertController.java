package edu.mtu.tinventory.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ConfirmRevertController extends Controller {
	@FXML private Button confirm;
	@FXML private Button cancel;
	
	@FXML
	private void confirm() {
		stage.close();
	}
	
	@FXML
	private void close() {
		stage.close();
	}
}
