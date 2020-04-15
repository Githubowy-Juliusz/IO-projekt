package application;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.Client;
import models.Equipment;
import models.Order;

public class DBConnector {
	private final String database = "jdbc:mysql://localhost:3306/ioapp?useUnicode=true"
			+ "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private final String databaseUser = "root";
	private final String userPassword = "githubowyjuliusz";

	public List<Order> selectOrders() {
		List<Order> orders = new ArrayList<>();
		try (Connection connection = DriverManager.getConnection(database, databaseUser, userPassword);) {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM orders ORDER BY id;");
			while (result.next()) {
				Integer id = result.getInt(1);
				Integer id_equipment = result.getInt(2);
				Integer id_client = result.getInt(3);
				Date date = result.getDate(4);
				Order order = new Order(id, id_client, id_equipment, date);
				orders.add(order);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orders;
	}

	public List<Client> selectClients() {
		List<Client> clients = new ArrayList<>();
		try (Connection connection = DriverManager.getConnection(database, databaseUser, userPassword);) {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM client ORDER BY id;");
			while (result.next()) {
				Integer id = result.getInt(1);
				String name = result.getString(2);
				String identification_number = result.getString(3);
				String address = result.getString(4);
				String phone_number = result.getString(5);
				String email = result.getString(6);
				Client client = new Client(id, name, identification_number, address, phone_number, email);
				clients.add(client);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clients;
	}

	public List<Equipment> selectEquipment() {
		List<Equipment> equipmentList = new ArrayList<>();
		try (Connection connection = DriverManager.getConnection(database, databaseUser, userPassword);) {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM equipment ORDER BY id;");
			while (result.next()) {
				Integer id = result.getInt(1);
				String type = result.getString(2);
				String name = result.getString(3);
				String model = result.getString(4);
				Integer year = result.getInt(5);
				Float cost = result.getFloat(6);
				Equipment equipment = new Equipment(id, type, name, model, year, cost);
				equipmentList.add(equipment);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return equipmentList;
	}

	public List<Order> selectArchive() {
		List<Order> archiveList = new ArrayList<>();
		try (Connection connection = DriverManager.getConnection(database, databaseUser, userPassword);) {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM archive ORDER BY id;");
			while (result.next()) {
				Integer id = result.getInt(1);
				Integer id_equipment = result.getInt(2);
				Integer id_client = result.getInt(3);
				Date date = result.getDate(4);
				Order archive = new Order(id, id_client, id_equipment, date);
				archiveList.add(archive);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return archiveList;
	}

	public String addOrder(String id_equipment, String id_client, String date) {
		try (Connection connection = DriverManager.getConnection(database, databaseUser, userPassword);) {
			String query = "INSERT INTO orders VALUES(0, " + id_equipment + ", " + id_client + ", \"" + date + "\")";
			Statement statement = connection.createStatement();
			statement.executeUpdate(query);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getLocalizedMessage();
		}
		return null;
	}

	public String addClient(String name, String idNumber, String address, String phoneNumber, String email) {
		try (Connection connection = DriverManager.getConnection(database, databaseUser, userPassword);) {
			String query = "INSERT INTO client VALUES(0, \"" + name + "\", \"" + idNumber + "\", \"" + address
					+ "\", \"" + phoneNumber + "\", \"" + email + "\");";
			Statement statement = connection.createStatement();
			statement.executeUpdate(query);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getLocalizedMessage();
		}
		return null;
	}

	public String addEquipment(String type, String name, String model, String year, String cost) {
		try (Connection connection = DriverManager.getConnection(database, databaseUser, userPassword);) {
			String query = "INSERT INTO equipment VALUES(0, \"" + type + "\", \"" + name + "\", \"" + model + "\", "
					+ year + ", " + cost + ");";
			Statement statement = connection.createStatement();
			statement.executeUpdate(query);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getLocalizedMessage();
		}
		return null;
	}

	public void archiveOrder(String id) {
		try (Connection connection = DriverManager.getConnection(database, databaseUser, userPassword);) {
			String query = "DELETE FROM orders WHERE id=" + id + ";";
			Statement statement = connection.createStatement();
			statement.executeUpdate(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteClient(String id) {
		try (Connection connection = DriverManager.getConnection(database, databaseUser, userPassword);) {
			String query = "DELETE FROM client WHERE id=" + id + ";";
			Statement statement = connection.createStatement();
			statement.executeUpdate(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteEquipment(String id) {
		try (Connection connection = DriverManager.getConnection(database, databaseUser, userPassword);) {
			String query = "DELETE FROM equipment WHERE id=" + id + ";";
			Statement statement = connection.createStatement();
			statement.executeUpdate(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
