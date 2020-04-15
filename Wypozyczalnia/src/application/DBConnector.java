package application;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.Client;
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
			ResultSet result = statement.executeQuery("SELECT * FROM orders;");
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
		// TODO
		return null;
	}

	public List<Order> selectArchive() {
		// TODO
		return null;
	}

	public String addOrder(String id_equipment, String id_client, String date) {
		try (Connection connection = DriverManager.getConnection(database, databaseUser, userPassword);) {
			String query = "INSERT INTO orders VALUES(0, " + id_equipment + ", " + id_client + ", \"" + date + "\")"
					+ "ORDER BY id;";
			Statement statement = connection.createStatement();
			statement.executeUpdate(query);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getLocalizedMessage();
		}
		return null;
	}

	public String addClient(String name, String idNumber, String address, String phoneNumber, String email) {
		// TODO
		return null;
	}

	public String addEquipment(String type, String name, String model, String year, String cost) {
		// TODO
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
		// TODO
	}

	public void deleteEquipment(String id) {
		// TODO
	}

}
