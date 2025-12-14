DROP TABLE IF EXISTS persons;

CREATE TABLE persons (
                         name VARCHAR(50) NOT NULL,
                         surname VARCHAR(50) NOT NULL,
                         age INT NOT NULL,
                         phone_number VARCHAR(20),
                         city_of_living VARCHAR(50),
                         PRIMARY KEY (name, surname, age)
);
