package application;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import models.Archive;
import models.Client;
import models.Equipment;
import models.Order;

public class FXController {
	private DBConnector database = new DBConnector();

	private ObservableList<Order> ordersTableContent;

	private ObservableList<Client> clientsTableContent;

	private ObservableList<Equipment> equipmentTableContent;

	private ObservableList<Archive> archiveTableContent;

	private PopupWindow popupWindow;

	// LogInView

	@FXML
	private TextField loginInput;

	@FXML
	private PasswordField passwordInput;

	@FXML
	private Button logInButton;

	@FXML
	private BorderPane logInViewBorderPane;

	@FXML
	void logInButtonOnAction(ActionEvent event) {
		String login = loginInput.getText();
		String password = passwordInput.getText();
		if (database.logIn(login, password))
			changeScene((Parent) logInViewBorderPane, "MainView");
		else
			popupWindow.display("Error", "Wrong login and/or password.");
	}

	// MainView
	@FXML
	private BorderPane mainViewBorderPane;

	@FXML
	private TextField orderIdEqupimentInput, orderIdClientInput, orderDateInput, clientNameInput,
			clientIdentificationNumberInput, clientAddressInput, clientPhoneNumberInput, clientEmailInput,
			equipmentTypeInput, equipmentNameInput, equipmentModelInput, equipmentYearInput, equipmentCostInput;

	@FXML
	private TableView<Order> ordersTable;

	@FXML
	private TableView<Client> clientsTable;

	@FXML
	private TableView<Equipment> equipmentTable;

	@FXML
	private TableView<Archive> archiveTable;

	@FXML
	private TabPane tabPane;

	@FXML
	private Tab ordersTab, clientsTab, equipmentTab, archiveTab;

	@FXML
	private TableColumn<Order, Integer> orderIdColumn, orderIdEquipmentColumn, orderIdClientColumn;
	@FXML
	private TableColumn<Order, Date> orderDateColumn;

	@FXML
	private TableColumn<Client, Integer> clientIdColumn;
	@FXML
	private TableColumn<Client, String> clientNameColumn, clientIdNumberColumn, clientAddressColumn,
			clientPhoneNumberColumn, clientEmailColumn;

	@FXML
	private TableColumn<Equipment, Integer> equipmentIdColumn, equipmentYearColumn;
	@FXML
	private TableColumn<Equipment, String> equipmentTypeColumn, equipmentNameColumn, equipmentModelColumn;
	@FXML
	private TableColumn<Equipment, Float> equipmentCostColumn;

	@FXML
	private TableColumn<Archive, Integer> archiveIdColumn;
	@FXML
	private TableColumn<Archive, String> archiveClientNameColumn, archiveClientIdNumberColumn,
			archiveEquipmentNameColumn, archiveEquipmentModelColumn;
	@FXML
	private TableColumn<Archive, Date> archiveDateColumn;

	@FXML
	void addOrderOnAction(ActionEvent event) {
		String id_equipment = orderIdEqupimentInput.getText();
		String id_client = orderIdClientInput.getText();
		String date = orderDateInput.getText();
		if (id_equipment.equals("")) {
			createPopupWindow("Warning", "Equipment id can't be empty.");
			return;
		}
		if (id_client.equals("")) {
			createPopupWindow("Warning", "Client id can't be empty.");
			return;
		}
		if (date.equals("")) {
			createPopupWindow("Warning", "Date can't be empty.");
			return;
		}
		try {
			Integer.parseInt(id_equipment);
		} catch (Exception e) {
			createPopupWindow("Warning", "Equipment id has to be integer!");
			return;
		}
		try {
			Integer.parseInt(id_client);
		} catch (Exception e) {
			createPopupWindow("Warning", "Client id has to be integer!");
			return;
		}
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			formatter.parse(date);
		} catch (Exception e) {
			createPopupWindow("Warning", "Date format has to be yyyy-MM-dd!");
			return;
		}

		String exceptionMessage = database.addOrder(id_equipment, id_client, date);
		if (exceptionMessage != null) {
			createPopupWindow("Error", "Following error occured: " + exceptionMessage);
		} else {
			orderIdEqupimentInput.clear();
			orderIdClientInput.clear();
			orderDateInput.clear();
			refreshOrdersTable();
		}
	}

	@FXML
	void refreshOrdersOnAction(ActionEvent event) {
		refreshOrdersTable();
	}

	@FXML
	void archiveSelectedOrderOnAction(ActionEvent event) {
		Order selectedOrder = ordersTable.getSelectionModel().getSelectedItem();
		database.archiveOrder(selectedOrder.getId().toString());
		refreshOrdersTable();
	}

	@FXML
	void addClientOnAction(ActionEvent event) {
		String name = clientNameInput.getText();
		String idNumber = clientIdentificationNumberInput.getText();
		String address = clientAddressInput.getText();
		String phoneNumber = clientPhoneNumberInput.getText();
		String email = clientEmailInput.getText();

		// CHECKING IF INPUTS AREN'T EMPTY
		if (name.equals("")) {
			createPopupWindow("Warning", "Name can't be empty.");
			return;
		}
		if (idNumber.equals("")) {
			createPopupWindow("Warning", "Identification number can't be empty.");
			return;
		}
		if (address.equals("")) {
			createPopupWindow("Warning", "Address can't be empty.");
			return;
		}
		if (phoneNumber.equals("")) {
			createPopupWindow("Warning", "Phone number can't be empty.");
			return;
		}
		if (email.equals("")) {
			createPopupWindow("Warning", "E-mail can't be empty.");
			return;
		}
		if (phoneNumber.length() != 9) {
			createPopupWindow("Warning", "Phone number has to be 9 digits long.");
			return;
		}

		// INTEGER VALIDATIONS
		try {
			Integer tmp = Integer.parseInt(phoneNumber);
			if (tmp < 0) {
				throw new Exception("Phone number is negative!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			createPopupWindow("Warning", "Phone number has to be a positive number.");
			return;
		}
		try {
			Integer tmp = Integer.parseInt(idNumber);
			if (tmp < 0) {
				throw new Exception("Identification number is negative!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			createPopupWindow("Warning", "Identification number has to be a positive number.");
			return;
		}

		// REGEX VALIDATIONS
		String emailRegex = "^[^@\\s]+@[^@\\s\\.]+\\.[^@\\.\\s]+$";
		Pattern pattern = Pattern.compile(emailRegex);
		Matcher matcher = pattern.matcher(email);
		if (!matcher.matches()) {
			createPopupWindow("Warning", "Incorrect e-mail.");
			return;
		}
		// Examples:
		// Adress 1
		// Adress 2a
		// Adress 3a/1
		// Adress 4b/2b
		// Adress 5/3c
		// Adress 6/4

		String addressRegex = "^(\\p{L}+ )(\\d+|\\d+/\\d+|\\d+\\p{L}{1}/\\d+|\\d+\\p{L}{1}/\\d+\\p{L}{1}|\\d+/\\d+\\p{L}{1})$";
		pattern = Pattern.compile(addressRegex);
		matcher = pattern.matcher(address);
		if (!matcher.matches()) {
			createPopupWindow("Warning", "Incorrect address.");
			return;
		}

		String exceptionMessage = database.addClient(name, idNumber, address, phoneNumber, email);
		if (exceptionMessage != null) {
			createPopupWindow("Error", "Following error occured: " + exceptionMessage);
		} else {
			clientNameInput.clear();
			clientIdentificationNumberInput.clear();
			clientEmailInput.clear();
			refreshClientsTable();
		}
	}

	@FXML
	void refreshClientsOnAction(ActionEvent event) {
		refreshClientsTable();
	}

	@FXML
	void deleteSelectedClientOnAction(ActionEvent event) {
		Client selectedClient = clientsTable.getSelectionModel().getSelectedItem();
		database.deleteClient(selectedClient.getId().toString());
		refreshClientsTable();
	}

	@FXML
	void addEquipmentOnAction(ActionEvent event) {
		String type = equipmentTypeInput.getText();
		String name = equipmentNameInput.getText();
		String model = equipmentModelInput.getText();
		String year = equipmentYearInput.getText();
		String cost = equipmentCostInput.getText();
		if (type.equals("")) {
			createPopupWindow("Warning", "Type can't be empty.");
			return;
		}
		if (name.equals("")) {
			createPopupWindow("Warning", "Name can't be empty.");
			return;
		}
		if (model.equals("")) {
			createPopupWindow("Warning", "Model can't be empty.");
			return;
		}
		if (year.equals("")) {
			createPopupWindow("Warning", "Year can't be empty.");
			return;
		}
		if (cost.equals("")) {
			createPopupWindow("Warning", "Cost can't be empty.");
			return;
		}
		try {
			Integer.parseInt(year);
		} catch (Exception e) {
			createPopupWindow("Warning", "Year has to be integer!");
			return;
		}
		try {
			Float.parseFloat(cost);
		} catch (Exception e) {
			createPopupWindow("Warning", "Cost has to be float!");
			return;
		}
		String exceptionMessage = database.addEquipment(type, name, model, year, cost);
		if (exceptionMessage != null) {
			createPopupWindow("Error", "Following error occured: " + exceptionMessage);
		} else {
			equipmentTypeInput.clear();
			equipmentNameInput.clear();
			equipmentModelInput.clear();
			equipmentYearInput.clear();
			equipmentCostInput.clear();
			refreshEquipmentTable();
		}
	}

	@FXML
	void refreshEquipmentOnAction(ActionEvent event) {
		refreshEquipmentTable();
	}

	@FXML
	void deleteSelectedEquipmentOnAction(ActionEvent event) {
		Equipment selectedEquipment = equipmentTable.getSelectionModel().getSelectedItem();
		database.deleteEquipment(selectedEquipment.getId().toString());
		refreshEquipmentTable();
	}

	@FXML
	void ordersTabChange(Event event) {
		if (ordersTab.selectedProperty().get()) {
			refreshOrdersTable();
			System.out.println("orders tab is active");
		}
	}

	@FXML
	void clientsTabChange(Event event) {
		if (clientsTab.selectedProperty().get()) {
			refreshClientsTable();
			System.out.println("clients tab is active");
		}
	}

	@FXML
	void equipmentTabChange(Event event) {
		if (equipmentTab.selectedProperty().get()) {
			refreshEquipmentTable();
			System.out.println("equipment tab is active");
		}
	}

	@FXML
	void archiveTabChange(Event event) {
		if (archiveTab.selectedProperty().get()) {
			refreshArchiveTable();
			System.out.println("archive tab is active");
		}
	}

	private void refreshOrdersTable() {
		List<Order> orders = database.selectOrders();
		orderIdColumn.setCellValueFactory(new PropertyValueFactory<Order, Integer>("id"));
		orderIdEquipmentColumn.setCellValueFactory(new PropertyValueFactory<Order, Integer>("id_equipment"));
		orderIdClientColumn.setCellValueFactory(new PropertyValueFactory<Order, Integer>("id_client"));
		orderDateColumn.setCellValueFactory(new PropertyValueFactory<Order, Date>("date"));
		ordersTableContent = FXCollections.observableArrayList();
		ordersTable.setItems(ordersTableContent);
		if (orders != null) {
			Platform.runLater(() -> {
				ordersTableContent.addAll(orders);
			});
		}
	}

	private void refreshClientsTable() {
		List<Client> clients = database.selectClients();
		clientIdColumn.setCellValueFactory(new PropertyValueFactory<Client, Integer>("id"));
		clientNameColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("name"));
		clientIdNumberColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("identification_number"));
		clientAddressColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("address"));
		clientPhoneNumberColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("phone_number"));
		clientEmailColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("email"));
		clientsTableContent = FXCollections.observableArrayList();
		clientsTable.setItems(clientsTableContent);
		if (clients != null) {
			Platform.runLater(() -> {
				clientsTableContent.addAll(clients);
			});
		}
	}

	private void refreshEquipmentTable() {
		List<Equipment> equipmentList = database.selectEquipment();
		equipmentIdColumn.setCellValueFactory(new PropertyValueFactory<Equipment, Integer>("id"));
		equipmentTypeColumn.setCellValueFactory(new PropertyValueFactory<Equipment, String>("type"));
		equipmentNameColumn.setCellValueFactory(new PropertyValueFactory<Equipment, String>("name"));
		equipmentModelColumn.setCellValueFactory(new PropertyValueFactory<Equipment, String>("model"));
		equipmentYearColumn.setCellValueFactory(new PropertyValueFactory<Equipment, Integer>("year"));
		equipmentCostColumn.setCellValueFactory(new PropertyValueFactory<Equipment, Float>("cost"));
		equipmentTableContent = FXCollections.observableArrayList();
		equipmentTable.setItems(equipmentTableContent);
		if (equipmentList != null) {
			Platform.runLater(() -> {
				equipmentTableContent.addAll(equipmentList);
			});
		}
	}

	private void refreshArchiveTable() {
		List<Archive> archiveList = database.selectArchive();
		archiveIdColumn.setCellValueFactory(new PropertyValueFactory<Archive, Integer>("id"));
		archiveClientNameColumn.setCellValueFactory(new PropertyValueFactory<Archive, String>("client_name"));
		archiveClientIdNumberColumn
				.setCellValueFactory(new PropertyValueFactory<Archive, String>("client_identification_number"));
		archiveEquipmentNameColumn.setCellValueFactory(new PropertyValueFactory<Archive, String>("equipment_name"));
		archiveEquipmentModelColumn.setCellValueFactory(new PropertyValueFactory<Archive, String>("equipment_model"));
		archiveDateColumn.setCellValueFactory(new PropertyValueFactory<Archive, Date>("date"));
		archiveTableContent = FXCollections.observableArrayList();
		archiveTable.setItems(archiveTableContent);
		if (archiveList != null) {
			Platform.runLater(() -> {
				archiveTableContent.addAll(archiveList);
			});
		}
	}

	private void createPopupWindow(String title, String message) {
		popupWindow.display(title, message);
		System.out.println(message);
	}

	void changeScene(Parent pane, String fxml) {
		Parent newPane;
		try {
			newPane = FXMLLoader.load(getClass().getResource(fxml + ".fxml"));
			pane.getScene().setRoot(newPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void initialize() {
		try {
			popupWindow = new PopupWindow();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
