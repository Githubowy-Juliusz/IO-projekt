package models;

import java.util.Date;

public class Order {
	private Integer id;
	private Integer id_equipment;
	private Integer id_client;
	private Date date;

	public Order(Integer id, Integer id_client, Integer id_equipment, Date date) {
		this.id = id;
		this.id_client = id_client;
		this.id_equipment = id_equipment;
		this.date = date;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId_equipment() {
		return id_equipment;
	}

	public void setId_equipment(Integer id_equipment) {
		this.id_equipment = id_equipment;
	}

	public Integer getId_client() {
		return id_client;
	}

	public void setId_client(Integer id_client) {
		this.id_client = id_client;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
