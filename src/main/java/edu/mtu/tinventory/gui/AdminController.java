package edu.mtu.tinventory.gui;

import edu.mtu.tinventory.data.Access;
import edu.mtu.tinventory.data.Employee;
import java.util.Set;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class AdminController extends Controller {
	@FXML private TableView<Employee> table;
	@FXML private TableColumn<Employee, String> colID;
	@FXML private TableColumn<Employee, String> colFirstName;
	@FXML private TableColumn<Employee, String> colLastName;
	@FXML private TableColumn<Employee, Access> colAccess;
	@FXML private TableColumn<Employee, Set<View>> colOverrides;

	@FXML
	private void initialize() {

	}

	@FXML
	private void addEmployee() {

	}

	@FXML
	private void changeAccess() {

	}

	@FXML
	private void changeOverrides() {

	}

	@FXML
	private void resetPassword() {

	}
}
