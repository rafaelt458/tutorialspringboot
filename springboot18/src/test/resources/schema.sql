DROP TABLE IF EXISTS productos;
DROP TABLE IF EXISTS categorias;

CREATE TABLE categorias (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE productos (
    codigo INT AUTO_INCREMENT PRIMARY KEY,
    categoria_id INT NOT NULL,
    fecha_ingreso DATE NOT NULL,
    nombre VARCHAR(120) NOT NULL UNIQUE,
    precio DOUBLE NOT NULL,
    FOREIGN KEY (categoria_id) REFERENCES categorias (id)
);