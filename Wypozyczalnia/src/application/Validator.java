package application;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
	public String validateOrder(String id_equipment, String id_client, String date) {
		// CHECKING IF INPUTS AREN'T EMPTY
		if (id_equipment.equals(""))
			return "Equipment id can't be empty.";
		if (id_client.equals(""))
			return "Client id can't be empty.";
		if (date.equals(""))
			return "Date can't be empty.";

		// INTEGER VALIDATIONS
		try {
			Integer.parseInt(id_equipment);
		} catch (Exception e) {
			return "Equipment id has to be integer!";
		}
		try {
			Integer.parseInt(id_client);
		} catch (Exception e) {
			return "Client id has to be integer!";
		}

		// DATE VALIDATION
		try {
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
					.withResolverStyle(ResolverStyle.STRICT);
			System.out.println(dateFormatter.parse(date).toString());
		} catch (Exception e) {
			return "Date format has to be yyyy-MM-dd!";
		}

		// CORRECT DATA
		return "ok";
	}

	public String validateClient(String name, String idNumber, String address, String postcode, String phoneNumber,
			String email) {
		// CHECKING IF INPUTS AREN'T EMPTY
		if (name.equals(""))
			return "Name can't be empty.";
		if (idNumber.equals(""))
			return "Identification number can't be empty.";
		if (address.equals(""))
			return "Address can't be empty.";
		if (postcode.equals(""))
			return "Postcode can't be empty.";
		if (phoneNumber.equals(""))
			return "Phone number can't be empty.";
		if (email.equals(""))
			return "E-mail can't be empty.";

		// LENGTH VALIDATION
		if (phoneNumber.length() != 9)
			return "Phone number has to be 9 digits long.";

		// INTEGER VALIDATIONS
		try {
			Integer phoneNumberAsInt = Integer.parseInt(phoneNumber);
			if (phoneNumberAsInt < 0)
				throw new Exception("Phone number is negative!");
		} catch (Exception e) {
			e.printStackTrace();
			return "Phone number has to be a positive number.";
		}

		try {
			Integer idNumberAsInt = Integer.parseInt(idNumber);
			if (idNumberAsInt < 0)
				throw new Exception("Identification number is negative!");
		} catch (Exception e) {
			e.printStackTrace();
			return "Identification number has to be a positive number.";
		}

		// REGEX VALIDATIONS
		String emailRegex = "^[^@\\s]+@[^@\\s\\.]+\\.[^@\\.\\s]+$";
		Pattern pattern = Pattern.compile(emailRegex);
		Matcher matcher = pattern.matcher(email);
		if (!matcher.matches())
			return "Incorrect e-mail.";

		// Address examples:
		// Adress 1
		// Adress 2a
		// Adress 3a/1
		// Adress 4b/2b
		// Adress 5/3c
		// Adress 6/4

		String addressRegex = "^(\\p{L}+ )(\\d+|\\d+/\\d+|\\d+\\p{L}{1}/\\d+|\\d+\\p{L}{1}/\\d+\\p{L}{1}|\\d+/\\d+\\p{L}{1})$";
		pattern = Pattern.compile(addressRegex);
		matcher = pattern.matcher(address);
		if (!matcher.matches())
			return "Incorrect address.";

		String postcodeRegex = "^\\d{2}-\\d{3}$";
		pattern = Pattern.compile(postcodeRegex);
		matcher = pattern.matcher(postcode);
		if (!matcher.matches())
			return "Incorrect postcode.";

		// CORRECT DATA
		return "ok";
	}

	public String validateEquipment(String type, String name, String model, String year, String cost) {
		// CHECKING IF INPUTS AREN'T EMPTY
		if (type.equals(""))
			return "Type can't be empty.";
		if (name.equals(""))
			return "Name can't be empty.";
		if (model.equals(""))
			return "Model can't be empty.";
		if (year.equals(""))
			return "Year can't be empty.";
		if (cost.equals(""))
			return "Cost can't be empty.";

		// INTEGER VALIDATION
		Integer currentYear = LocalDateTime.now().getYear();
		try {
			Integer yearAsInt = Integer.parseInt(year);
			if (yearAsInt < 0)
				throw new Exception("Year can't be negative");
			if (yearAsInt > currentYear)
				throw new Exception("Equipment can't be from the future");
		} catch (Exception e) {
			return "Year has to be between 0 and " + currentYear.toString();
		}

		// DOUBLE VALIDATION
		try {
			Double costAsDouble = Double.parseDouble(cost);
			if (costAsDouble <= 0 || costAsDouble > 10000)
				throw new Exception("Cost can't be negative or bigger than 10000");
		} catch (Exception e) {
			return "Cost has to be between 0 and 10000";
		}

		// REGEX VALIDATIONS
		String nameAndTypeRegex = "^\\p{L}+$";
		Pattern pattern = Pattern.compile(nameAndTypeRegex);
		Matcher matcher = pattern.matcher(name);
		if (!matcher.matches())
			return "Name can only contain letters.";

		matcher = pattern.matcher(type);
		if (!matcher.matches())
			return "Type can only contain letters.";
		return "ok";
	}
}