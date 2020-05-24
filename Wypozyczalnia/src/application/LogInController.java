package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class LogInController {
	// ACCESS TO DB
	private DBConnector database = new DBConnector();

	// POPUP WINDOW THAT SHOWS ERROR MESSAGE
	private PopupWindow popupWindow;

	// STORES EMPLOYEE'S LOGIN
	private Singleton singleton = Singleton.getInstance();

	@FXML
	private TextField loginInput;

	@FXML
	private PasswordField passwordInput;

	@FXML
	private BorderPane logInViewBorderPane;

	@FXML
	void logInButtonOnAction(ActionEvent event) {
		String login = loginInput.getText();
		String password = passwordInput.getText();
		if (database.logIn(login, password)) {
			singleton.setEmployee(login);
			changeScene((Parent) logInViewBorderPane, "MainView");
		} else
			popupWindow.display("Error", "Wrong login and/or password.");
	}

	@FXML
	void initialize() {
		try {
			popupWindow = new PopupWindow();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void changeScene(Parent pane, String fxml) {
		Parent newPane;
		try {
			newPane = FXMLLoader.load(getClass().getResource(fxml + ".fxml"));
			pane.getScene().setRoot(newPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
