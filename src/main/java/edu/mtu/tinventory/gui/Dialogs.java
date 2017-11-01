package edu.mtu.tinventory.gui;

import java.io.PrintWriter;
import java.io.StringWriter;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
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

	/**
	 * Displays a dialog box to the user.
	 * @param type The type of dialog box this is. Just dictates the icon the window shows.
	 * @param heading A heading message. Using null here will not show a heading. Good for compact messages.
	 * @param message The actual content of the message.
	 */
	public static void showDialog(Type type, String heading, String message) {
		Alert alert = new Alert(type.getAlertType());
		alert.initStyle(StageStyle.UTILITY);
		alert.setTitle(type.getTitle() + " - TInventory");
		alert.setHeaderText(heading);
		alert.setContentText(message);
		alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE); // Fixes edge case sizing problems
		alert.showAndWait();
	}

	/**
	 * Displays an ERROR dialog box with a details box displaying the stacktrace of the exception encountered.
	 * @param heading A heading message. Using null here will not show a heading. Good for compact messages.
	 * @param message The actual content of the message.
	 * @param e The exception encountered.
	 */
	public static void showDialogWithException(String heading, String message, Exception e) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.initStyle(StageStyle.UTILITY);
		alert.setTitle("Exception - TInventory");
		alert.setHeaderText(heading);
		alert.setContentText(message);

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		pw.close();

		GridPane exDisplay = new GridPane();
		exDisplay.setMaxWidth(Double.MAX_VALUE);
		TextArea ta = new TextArea(sw.toString());
		ta.setEditable(false);
		ta.setWrapText(true);
		ta.setMaxWidth(Double.MAX_VALUE);
		ta.setMaxHeight(Double.MAX_VALUE);
		GridPane.setHgrow(ta, Priority.ALWAYS);
		GridPane.setVgrow(ta, Priority.ALWAYS);
		exDisplay.add(new Label("Details:"), 0, 0);
		exDisplay.add(ta, 0, 1);
		alert.getDialogPane().setExpandableContent(exDisplay);

		alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE); // Fixes edge case sizing problems
		alert.showAndWait();
	}
}
