package models;

public class Client {
	Integer id;
	String name;
	String identification_number;
	String address;
	String postcode;
	String phone_number;
	String email;

	public Client(Integer id, String name, String identification_number, String address, String postcode,
			String phone_number, String email) {
		super();
		this.id = id;
		this.name = name;
		this.identification_number = identification_number;
		this.address = address;
		this.postcode = postcode;
		this.phone_number = phone_number;
		this.email = email;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdentification_number() {
		return identification_number;
	}

	public void setIdentification_number(String identification_number) {
		this.identification_number = identification_number;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
