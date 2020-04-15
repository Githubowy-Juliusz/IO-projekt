package application;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import models.Client;
import models.Equipment;
import models.Order;

public class FXController {
	private DBConnector database = new DBConnector();

	private ObservableList<Order> ordersTableContent;

	private ObservableList<Client> clientsTableContent;

	private ObservableList<Equipment> equipmentTableContent;

	private ObservableList<Order> archiveTableContent;

	private PopupWindow popupWindow;

	// LogInView

	@FXML
	private TextField loginInput;

	@FXML
	private TextField passwordInput;

	@FXML
	private Button logInButton;

	@FXML
	private BorderPane logInViewBorderPane;

	@FXML
	void logInButtonOnAction(ActionEvent event) {
		// TODO validate login and password
		changeScene((Parent) logInViewBorderPane, "MainView");
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
	private TableView<Order> archiveTable;

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
	private TableColumn<Order, Integer> archiveIdColumn, archiveIdEquipmentColumn, archiveIdClientColumn;
	@FXML
	private TableColumn<Order, Date> archiveDateColumn;

	@FXML
	void addOrderOnAction(ActionEvent event) {
		String id_equipment = orderIdEqupimentInput.getText();
		String id_client = orderIdClientInput.getText();
		String date = orderDateInput.getText();
		if (id_equipment.contentEquals("")) {
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

	}

	@FXML
	void refreshClientsOnAction(ActionEvent event) {
		refreshClientsTable();
	}

	@FXML
	void deleteSelectedClientOnAction(ActionEvent event) {

	}

	@FXML
	void addEquipmentOnAction(ActionEvent event) {

	}

	@FXML
	void refreshEquipmentOnAction(ActionEvent event) {
		refreshEquipmentTable();
	}

	@FXML
	void deleteSelectedEquipmentOnAction(ActionEvent event) {

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
		List<Order> archiveList = database.selectArchive();
		archiveIdColumn.setCellValueFactory(new PropertyValueFactory<Order, Integer>("id"));
		archiveIdEquipmentColumn.setCellValueFactory(new PropertyValueFactory<Order, Integer>("id_equipment"));
		archiveIdClientColumn.setCellValueFactory(new PropertyValueFactory<Order, Integer>("id_client"));
		archiveDateColumn.setCellValueFactory(new PropertyValueFactory<Order, Date>("date"));
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
