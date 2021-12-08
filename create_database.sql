CREATE DATABASE finance CHARACTER SET utf8mb4 COLLATE utf8mb4_polish_ci;

CREATE TABLE transaction (
	id INT PRIMARY KEY AUTO_INCREMENT,
    type VARCHAR(20),
    description VARCHAR(50),
    amount DOUBLE,
    date DATE
);
