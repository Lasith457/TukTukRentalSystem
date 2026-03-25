CREATE DATABASE tuktuk_rental;
CREATE TABLE customers(
customer_id INT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(100),
nic VARCHAR(20),
phone VARCHAR(15),
license_no VARCHAR(20)
);
CREATE TABLE tuktuks(
tuktuk_id INT AUTO_INCREMENT PRIMARY KEY,
plate_no VARCHAR(20),
model VARCHAR(50),
price_per_day DOUBLE,
status VARCHAR(20)
);
CREATE TABLE rentals(
rental_id INT AUTO_INCREMENT PRIMARY KEY,
customer_id INT,
tuktuk_id INT,
start_date DATE,
end_date DATE,
total_amount DOUBLE
);
CREATE TABLE payments(
payment_id INT AUTO_INCREMENT PRIMARY KEY,
rental_id INT,
amount DOUBLE,
payment_date DATE
);
SELECT * FROM customers;
SELECT * FROM customers;
SELECT * FROM customers;
SELECT * FROM customers;
SELECT * FROM tuktuks;
UPDATE tuktuks
SET status = 'Available'
WHERE status = 'Rented';
CREATE TABLE payments (
    payment_id INT AUTO_INCREMENT PRIMARY KEY,
    rental_id INT,
    amount DOUBLE,
    payment_method VARCHAR(50),
    payment_date DATE,
    FOREIGN KEY (rental_id) REFERENCES rentals(rental_id)
);
CREATE TABLE payments (
    payment_id INT AUTO_INCREMENT PRIMARY KEY,
    rental_id INT UNIQUE,
    amount DOUBLE,
    payment_method VARCHAR(50),
    payment_date DATE,
    FOREIGN KEY (rental_id) REFERENCES rentals(rental_id)
);
SELECT * FROM payments;
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE,
    password VARCHAR(50)
);

INSERT INTO users (username, password)
VALUES ('admin', '1234');
SHOW TABLES;