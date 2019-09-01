DROP SCHEMA IF EXISTS bichomonJDBC;
CREATE SCHEMA bichomonJDBC;

USE bichomonJDBC;


CREATE TABLE especie (

especie_id INT(99) NOT NULL AUTO_INCREMENT UNIQUE,
nombre VARCHAR(30) NOT NULL UNIQUE,
peso INT NOT NULL,
altura INT NOT NULL,
tipo VARCHAR(30) NOT NULL,
cantidad_de_bichos INT NOT NULL,
PRIMARY KEY(especie_id)
);







