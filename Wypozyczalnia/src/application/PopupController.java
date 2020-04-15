package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.Window;

public class PopupController {
	@FXML
	private Label popupMessage;

	@FXML
	private Button okButton;

	@FXML
	void okButtonOnAction(ActionEvent event) {
		Node eventSource = (Node) event.getSource();
		Window window = eventSource.getScene().getWindow();
		Stage stage = (Stage) window;
		stage.close();
	}

	@FXML
	void initialize() {

	}

	public void setMessage(String message) {
		popupMessage.setText(message);
	}
}
