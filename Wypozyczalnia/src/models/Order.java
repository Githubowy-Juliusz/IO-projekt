package models;

import java.util.Date;

public class Order {
	private Integer id;
	private Integer id_equipment;
	private Integer id_client;
	private Date date_from;
	private Date date_until;
	private String employee;
	private String comment;

	public Order() {
		super();
	}

	public Order(Integer id_equipment, Integer id_client, Date date_from, Date date_until, String employee,
			String comment) {
		super();
		this.id_equipment = id_equipment;
		this.id_client = id_client;
		this.date_from = date_from;
		this.date_until = date_until;
		this.employee = employee;
		this.comment = comment;
	}

	public Order(Integer id, Integer id_equipment, Integer id_client, Date date_from, Date date_until, String employee,
			String comment) {
		super();
		this.id = id;
		this.id_equipment = id_equipment;
		this.id_client = id_client;
		this.date_from = date_from;
		this.date_until = date_until;
		this.employee = employee;
		this.comment = comment;
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

	public Date getDate_from() {
		return date_from;
	}

	public void setDate_from(Date date_from) {
		this.date_from = date_from;
	}

	public Date getDate_until() {
		return date_until;
	}

	public void setDate_until(Date date_until) {
		this.date_until = date_until;
	}

	public String getEmployee() {
		return employee;
	}

	public void setEmployee(String employee) {
		this.employee = employee;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
