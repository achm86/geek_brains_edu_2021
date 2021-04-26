


CREATE TABLE TelephoneDictionary(
   entry_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
   surname_id INT NOT NULL,
   telephone_number VARCHAR(40) NOT NULL,
   address_id INT NOT NULL
   );
   
CREATE TABLE Addresses(
   address_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
   house_number INT NOT NULL,
   street_name VARCHAR(100) NOT NULL,
   city VARCHAR(40) NOT NULL,
   region VARCHAR(40) NOT NULL,
   country VARCHAR(40) NOT NULL
   );
   
CREATE TABLE AutoHalter(
  surname_id INT NOT NULL,
  brand VARCHAR(40) NOT NULL,
  model VARCHAR(40) NOT NULL,
  color VARCHAR(40) NOT NULL,
  price INT NOT NULL
  );
  
CREATE TABLE Bankholder(
  surname_id INT NOT NULL,
  bank_id INT not NULL,
  account_number INT not null,
  balance INT NOT NULL
  );

CREATE TABLE Bank(
  bank_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  bank_name VARCHAR(40) NOT NULL,
  address_id INT NOT NULL
  );

CREATE TABLE Surname(
  surname_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  surname VARCHAR(40) NOT NULL
  );


INSERT INTO `TelephoneDictionary`(surname_id,telephone_number,address_id) VALUES 
(1,'+79261778198',1), (2,'+79605151732',2), (3,'+79656142944',3),
(4,'+79656142999',4), (5,'+79187139826', 5), (6,'+79087356241', 6),
(4,'+79657562535',7);

INSERT INTO `Addresses`(house_number,street_name,city,region,country) VALUES 
(12,'Chekhova street','Lipetsk','Lipetskaya obl','Russland'),
(15,'Mira street','Rostov','Rostovskaya obl','Russland'),
(251,'Mira street','Stavropol','Stavropol territory','Russland'),
(5,'Lenin street','Voronezh','Voronezhskaya obl','Russland'),
(71,'2 Pereulok','Tambov','Tambov obl','Russland'),
(29,'Tverskaya str','Moscow','Moscow','Russland'),
(14,'Petrovskaya str','Volgograd','Volgograd rgn','Russland'),
(41,'Novi blvd','Volgograd','Volgograd rgn','Russland');

INSERT INTO `Surname`(surname) VALUES ('Ivanoff'), ('Kotoff'), ('Radimoff'), ('James'), ('Venger'), ('Smith');
INSERT INTO `Bank`(bank_name, address_id) VALUES ('Sberbank', 2),('Tinkoff', 4);

INSERT INTO AutoHalter(surname_id, brand, model, color, price) VALUES
(1, 'Lada', 'Sedan 7', 'White', '1000'),
(2, 'Lada', 'Sedan 10', 'Violet', '2000'),
(3, 'Lada', 'Hatch 11', 'Brun', '3000'),
(3, 'MAZ', 'Hyper Truck', 'Brun', '30000'),
(4, 'UAZ', 'Patriot', 'Brun', '6000'),
(4, 'UAZ', 'Patriot XS', 'White', '9000'),
(5, 'GAZ', 'Truck 2110', 'Haki', '4000'),
(5, 'Lada', 'Sedan 15', 'Lila', '5000'),
(6, 'GAZ', 'Limousine Pobeda', 'Bege', '5000');

INSERT INTO BankHolder(surname_id, bank_id, account_number, balance) VALUES
(1, 1, 101010, 457680),
(2, 1, 104510, 1457680),
(3, 2, 11510, 187620),
(4, 1, 632510, 4187620),
(5, 2, 963510, 7187620),
(6, 1, 19510, 645378)



### Query #1 – Select Surname, Auto model and price by telephone number

select sr.surname, ah.model, ah.price 
  from AutoHalter ah
    INNER JOIN
  Surname sr
    ON ah.surname_id = sr.surname_id
  where ah.surname_id in (select surname_id from TelephoneDictionary where telephone_number = '+79187139826')
  order by price


### Query #2 – Select Auto models by telephone number

select ah.model
  from AutoHalter ah
    INNER JOIN
  Surname sr
    ON ah.surname_id = sr.surname_id
  where ah.surname_id in (select surname_id from TelephoneDictionary where telephone_number = '+79187139826')
  order by price


### Query #3 – Select Tel-number, Street-name, Bank-name by Surname and City

select addr.street_name, addr.city, bank.bank_name, tel.telephone_number from
  Addresses addr
    INNER JOIN
  Bank bank
    INNER JOIN
  TelephoneDictionary tel
    INNER JOIN
  BankHolder bank_holder
    ON tel.address_id = addr.address_id AND tel.surname_id = bank_holder.surname_id AND bank_holder.bank_id = bank.bank_id 
  where 
    tel.surname_id in (select surname_id from Surname where surname = 'James')
      AND
    addr.city = 'Volgograd'


