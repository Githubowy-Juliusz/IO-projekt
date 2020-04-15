package models;

public class Equipment {
	Integer id;
	String type;
	String name;
	String model;
	Integer year;
	Float cost;

	public Equipment(Integer id, String type, String name, String model, Integer year, Float cost) {
		super();
		this.id = id;
		this.type = type;
		this.name = name;
		this.model = model;
		this.year = year;
		this.cost = cost;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Float getCost() {
		return cost;
	}

	public void setCost(Float cost) {
		this.cost = cost;
	}

}
