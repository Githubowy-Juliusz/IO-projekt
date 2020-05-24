package application;

public class Singleton {
	private static Singleton singleton = new Singleton();
	private String employee;

	private Singleton() {
	}

	public static Singleton getInstance() {
		return singleton;
	}

	public String getEmployee() {
		return employee;
	}

	public void setEmployee(String employee) {
		this.employee = employee;
	}

}
