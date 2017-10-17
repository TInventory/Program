package edu.mtu.tinventory.gui;

import javafx.scene.control.Alert;
import javafx.stage.StageStyle;

/**
 * Helper class for using dialog boxes.
 */
public class Dialogs {
	enum Type {
		INFO("Info", Alert.AlertType.INFORMATION),
		WARNING("Warning", Alert.AlertType.WARNING),
		ERROR("Error", Alert.AlertType.ERROR);

		private String title;
		private Alert.AlertType alertType;

		Type(String title, Alert.AlertType alertType) {
			this.title = title;
			this.alertType = alertType;
		}

		public String getTitle() {
			return title;
		}

		public Alert.AlertType getAlertType() {
			return alertType;
		}
	}

	public static void showDialog(Type type, String heading, String message) {
		Alert alert = new Alert(type.getAlertType());
		alert.initStyle(StageStyle.UTILITY);
		alert.setTitle(type.getTitle() + " - TInventory");
		alert.setHeaderText(heading);
		alert.setContentText(message);
		alert.showAndWait();
	}
}
