package application;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PopupWindow {
	private Stage stage;
	private BorderPane pane;
	private Scene scene;
	private PopupController controller;

	public PopupWindow() throws IOException {
		stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("PopupWindow.fxml"));
		pane = (BorderPane) loader.load();
		controller = (PopupController) loader.getController();
		scene = new Scene(pane, 200, 200);
		stage.setScene(scene);
	}

	public void display(String title, String message) {
		stage.setTitle(title);
		controller.setMessage(message);
		stage.show();

	}
}
