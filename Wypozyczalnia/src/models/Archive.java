package models;

import java.util.Date;

public class Archive {
	private Integer id;
	private String client_name;
	private String client_identification_number;
	private String equipment_name;
	private String equipment_model;
	private Date date_from;
	private Date date_until;
	private Date archived_date;

	public Archive() {
		super();
	}

	public Archive(Integer id, String client_name, String client_identification_number, String equipment_name,
			String equipment_model, Date date_from, Date date_until, Date archived_date) {
		super();
		this.id = id;
		this.client_name = client_name;
		this.client_identification_number = client_identification_number;
		this.equipment_name = equipment_name;
		this.equipment_model = equipment_model;
		this.date_from = date_from;
		this.date_until = date_until;
		this.archived_date = archived_date;
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

	public Date getArchived_date() {
		return archived_date;
	}

	public void setArchived_date(Date archived_date) {
		this.archived_date = archived_date;
	}

}
