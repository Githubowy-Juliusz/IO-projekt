import random, string

#client
name_list = ["Adam", "Stefan", "Andrzej", "Juliusz", "Mariusz", "Janusz", "Mikołaj", "Agata", "Aneta", "Aleksandara", "Ania", "Alicja", "Beata", "Martyna", "Małgorzata"]
nazwiska_list = ["Nowak", "Kowal", "Sten", "Woźniak", "Pasternak", "Dudek", "Wróbel", "Kwiecień", "Krupa", "Socha", "Skiba", "Towarek", "Potok", "Piątek", "Hoffmann", "Miller", "Szulc", "Grec"]
lista_ulic = ["Kaszubska", "Toruńska", "Gdańska", "Fordońska", "Błońska", "Barwna", "Ośmiowa"]
domain_list = ["@tk.pl", "@rt.pl", "@mp.pl", "@gp.com", "@rt.ru", "@tk.com", "@trl.pl", "@pp.pl"]
used_addresses = []
used_pesel = []
used_phone_numbers = []
used_emails = []
for i in range(1, 201):
	pesel = ""
	phone_number = ""
	address = ""
	email = ""
	name = name_list[random.randint(0, len(name_list)-1)]
	last_name = nazwiska_list[random.randint(0, len(nazwiska_list)-1)]
	full_name = name + " " + last_name
	while True:
		ulica = lista_ulic[random.randint(0, len(lista_ulic) - 1)]
		nr_domu = str(random.randint(1, 300))
		address = f"{ulica} {nr_domu}"
		if address not in used_addresses:
			used_addresses.append(address)
			break
			
	while True:
		pesel = str(random.randint(10000000000, 99999999999))
		if pesel not in used_pesel:
			used_pesel.append(pesel)
			break
	while True:
		phone_number = str(random.randint(100000000, 999999999))
		if phone_number not in used_phone_numbers:
			used_phone_numbers.append(pesel)
			break
	rand_string = ""
	while True:
		domain = domain_list[random.randint(0, len(domain_list) - 1)]
		email = f"{name}.{last_name}{rand_string}{domain}".lower()
		if email not in used_emails:
			used_emails.append(email)
			break
		rand_string += str(random.randint(0, 9))

	print(f"INSERT INTO ioapp.client VALUES({i}, \"{full_name}\", \"{pesel}\", \"{address}\", \"{phone_number}\", \"{email}\");")

#equipment
types_and_names = [("Instrument dęty", "Puzon"), ("Instrument dęty", "Klarnet"), ("Instrument dęty", "Flet"), ("Instrument strunowy", "Gitara"), ("Instrument strunowy", "Gitara elektryczna"), ("Instrument strunowy", "Gitara basowa"), ("Instrument strunowy", "Gitara"), ("Instrument strunowy", "Skrzypce")]


for i in range (1, 501):
	model = "".join(random.choices(string.ascii_uppercase + string.digits, k=5))
	name = types_and_names[random.randint(0, len(types_and_names) - 1)]
	type_ = name[0]
	name = name[1]
	year = str(random.randint(2000, 2020))
	cost = random.uniform(100.0, 10000.0)
	cost = str(round(cost, 2))

	print(f"INSERT INTO ioapp.equipment VALUES({i}, \"{type_}\", \"{name}\", \"{model}\", {year}, {cost});")


#orders
taken_equipment = []
for i in range(1, 201):
	client = str(random.randint(1, 200))
	equipment = 0
	while True:
		equipment = random.randint(1, 500)
		if equipment not in taken_equipment:
			taken_equipment.append(equipment)
			break
	print(f"INSERT INTO ioapp.orders VALUES({i}, {equipment}, {client}, \"2020-04-13\");")
