package application;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
	// ACCESS TO DB
	private DBConnector database = new DBConnector();

	// VALIDATES DATA
	private Validator validator = new Validator();

	// POPUP WINDOW THAT SHOWS ERROR MESSAGE
	private PopupWindow popupWindow;

	// STORES EMPLOYEE'S LOGIN
	private Singleton singleton = Singleton.getInstance();

	private ObservableList<Order> ordersTableContent;

	private ObservableList<Client> clientsTableContent;

	private ObservableList<Equipment> equipmentTableContent;

	private ObservableList<Archive> archiveTableContent;

	// LogInView

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

	// MainView
	@FXML
	private BorderPane mainViewBorderPane;

	// ORDER TEXT FIELDS
	@FXML
	private TextField orderIdEqupimentInput, orderIdClientInput, orderDateFromInput, orderDaysInput, orderCommentInput;

	// CLIENT TEXT FIELDS
	@FXML
	private TextField clientNameInput, clientIdentificationNumberInput, clientAddressInput, clientPostcodeInput,
			clientPhoneNumberInput, clientEmailInput;

	// EQUIPMENT TEXT FIELDS
	@FXML
	private TextField equipmentTypeInput, equipmentNameInput, equipmentModelInput, equipmentYearInput,
			equipmentCostInput;

	@FXML
	private TabPane tabPane;

	@FXML
	private Tab ordersTab, clientsTab, equipmentTab, archiveTab;

	@FXML
	private TableView<Order> ordersTable;

	@FXML
	private TableView<Client> clientsTable;

	@FXML
	private TableView<Equipment> equipmentTable;

	@FXML
	private TableView<Archive> archiveTable;

	// ORDER COLUMNS
	@FXML
	private TableColumn<Order, Integer> orderIdColumn, orderIdEquipmentColumn, orderIdClientColumn;
	@FXML
	private TableColumn<Order, Date> orderDateFromColumn, orderDateUntilColumn;
	@FXML
	private TableColumn<Order, String> orderEmployeeColumn, orderCommentColumn;

	// CLIENT COLUMNS
	@FXML
	private TableColumn<Client, Integer> clientIdColumn;
	@FXML
	private TableColumn<Client, String> clientNameColumn, clientIdNumberColumn, clientAddressColumn,
			clientPostcodeColumn, clientPhoneNumberColumn, clientEmailColumn;

	// EQUIPMENT COLUMNS
	@FXML
	private TableColumn<Equipment, Integer> equipmentIdColumn, equipmentYearColumn;
	@FXML
	private TableColumn<Equipment, String> equipmentTypeColumn, equipmentNameColumn, equipmentModelColumn;
	@FXML
	private TableColumn<Equipment, Float> equipmentCostColumn;

	// ARCHIVE COLUMNS
	@FXML
	private TableColumn<Archive, Integer> archiveIdColumn;
	@FXML
	private TableColumn<Archive, String> archiveClientNameColumn, archiveClientIdNumberColumn,
			archiveEquipmentNameColumn, archiveEquipmentModelColumn;
	@FXML
	private TableColumn<Archive, Date> archiveDateFromColumn, archiveDateUntilColumn, archiveDateColumn;

	@FXML
	void addOrderOnAction(ActionEvent event) {
		String id_equipment = orderIdEqupimentInput.getText();
		String id_client = orderIdClientInput.getText();
		String date_from = orderDateFromInput.getText();
		String daysOfRental = orderDaysInput.getText();
		String comment = orderCommentInput.getText();
		String validationResult = validator.validateOrder(id_equipment, id_client, date_from, daysOfRental, comment);
		if (!validationResult.equals("ok")) {
			popupWindow.display("Warning", validationResult);
			return;
		}
		String date_until = calculateFinishDate(date_from, daysOfRental);

		String exceptionMessage = database.addOrder(id_equipment, id_client, date_from, date_until,
				singleton.getEmployee(), comment);
		if (exceptionMessage != null) {
			popupWindow.display("Error", "Following error occured: " + exceptionMessage);
		} else {
			orderIdEqupimentInput.clear();
			orderIdClientInput.clear();
			orderDateFromInput.clear();
			orderDaysInput.clear();
			orderCommentInput.clear();
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
		String postcode = clientPostcodeInput.getText();
		String phoneNumber = clientPhoneNumberInput.getText();
		String email = clientEmailInput.getText();

		String validationResult = validator.validateClient(name, idNumber, address, postcode, phoneNumber, email);
		if (!validationResult.equals("ok")) {
			popupWindow.display("Warning", validationResult);
			return;
		}

		String exceptionMessage = database.addClient(name, idNumber, address, postcode, phoneNumber, email);
		if (exceptionMessage != null) {
			// CHECKING IF IDENTIFICATION NUMBER IS ALREADY IN DB
			String identificationAlreadyInDatabaseRegex = "^.*identification_number_UNIQUE.*$";
			Pattern pattern = Pattern.compile(identificationAlreadyInDatabaseRegex);
			Matcher matcher = pattern.matcher(exceptionMessage);
			if (matcher.matches())
				popupWindow.display("Warning", "Client with that identification number is already in database.");
			else
				popupWindow.display("Error", "Following error occured: " + exceptionMessage);
		} else {
			clientNameInput.clear();
			clientIdentificationNumberInput.clear();
			clientAddressInput.clear();
			clientPostcodeInput.clear();
			clientPhoneNumberInput.clear();
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

		String validationResult = validator.validateEquipment(type, name, model, year, cost);
		if (!validationResult.equals("ok")) {
			popupWindow.display("Warning", validationResult);
			return;
		}

		// ROUNDING TO 2 DECIMAL PLACES
		Double costAsDouble = Double.parseDouble(cost);
		costAsDouble = (double) (Math.round(costAsDouble * 100.0) / 100.0);
		cost = costAsDouble.toString();

		String exceptionMessage = database.addEquipment(type, name, model, year, cost);
		if (exceptionMessage != null) {
			popupWindow.display("Error", "Following error occured: " + exceptionMessage);
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

	@FXML
	void initialize() {
		try {
			popupWindow = new PopupWindow();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void refreshOrdersTable() {
		List<Order> orders = database.selectOrders();

		orderIdColumn.setCellValueFactory(new PropertyValueFactory<Order, Integer>("id"));
		orderIdEquipmentColumn.setCellValueFactory(new PropertyValueFactory<Order, Integer>("id_equipment"));
		orderIdClientColumn.setCellValueFactory(new PropertyValueFactory<Order, Integer>("id_client"));
		orderDateFromColumn.setCellValueFactory(new PropertyValueFactory<Order, Date>("date_from"));
		orderDateUntilColumn.setCellValueFactory(new PropertyValueFactory<Order, Date>("date_until"));
		orderEmployeeColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("employee"));
		orderCommentColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("comment"));

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
		clientPostcodeColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("postcode"));
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
		archiveDateFromColumn.setCellValueFactory(new PropertyValueFactory<Archive, Date>("date_from"));
		archiveDateUntilColumn.setCellValueFactory(new PropertyValueFactory<Archive, Date>("date_until"));
		archiveDateColumn.setCellValueFactory(new PropertyValueFactory<Archive, Date>("archived_date"));

		archiveTableContent = FXCollections.observableArrayList();
		archiveTable.setItems(archiveTableContent);
		if (archiveList != null) {
			Platform.runLater(() -> {
				archiveTableContent.addAll(archiveList);
			});
		}
	}

	private String calculateFinishDate(String date_from, String daysOfRental) {
		// CHANGING STR TO INT
		Integer days;
		days = Integer.parseInt(daysOfRental);

		// CHANGING STR TO LOCALDATE AND ADDING DAYS
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate dateUntil = LocalDate.parse(date_from, formatter).plusDays(days);

		// BACK TO STR
		String date_until = dateUntil.toString();
		System.out.println(date_until);
		return date_until;
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
