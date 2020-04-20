package models;

import java.util.Date;

public class Archive {
	private Integer id;
	private String client_name;
	private String client_identification_number;
	private String equipment_name;
	private String equipment_model;
	private Date date;

	public Archive(Integer id, String client_name, String client_identification_number, String equipment_name,
			String equipment_model, Date date) {
		this.id = id;
		this.client_name = client_name;
		this.client_identification_number = client_identification_number;
		this.equipment_name = equipment_name;
		this.equipment_model = equipment_model;
		this.date = date;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getClient_name() {
		return client_name;
	}

	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}

	public String getClient_identification_number() {
		return client_identification_number;
	}

	public void setClient_identification_number(String client_identification_number) {
		this.client_identification_number = client_identification_number;
	}

	public String getEquipment_name() {
		return equipment_name;
	}

	public void setEquipment_name(String equipment_name) {
		this.equipment_name = equipment_name;
	}

	public String getEquipment_model() {
		return equipment_model;
	}

	public void setEquipment_model(String equipment_model) {
		this.equipment_model = equipment_model;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
