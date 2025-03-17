INSERT INTO categorias (nombre) VALUES('Categoría 1');
INSERT INTO categorias (nombre) VALUES('Categoría 2');
INSERT INTO categorias (nombre) VALUES('Categoría 3');

INSERT INTO productos (categoria_id, fecha_ingreso, nombre, precio)
VALUES (1, CURRENT_DATE, 'Producto 1', 1.15);
INSERT INTO productos (categoria_id, fecha_ingreso, nombre, precio)
VALUES (1, CURRENT_DATE, 'Producto 2', 2.25);
INSERT INTO productos (categoria_id, fecha_ingreso, nombre, precio)
VALUES (1, CURRENT_DATE, 'Producto 3', 4.12);

INSERT INTO productos (categoria_id, fecha_ingreso, nombre, precio)
VALUES (2, CURRENT_DATE, 'Producto 4', 2.46);
INSERT INTO productos (categoria_id, fecha_ingreso, nombre, precio)
VALUES (2, CURRENT_DATE, 'Producto 5', 3.72);
INSERT INTO productos (categoria_id, fecha_ingreso, nombre, precio)
VALUES (2, CURRENT_DATE, 'Producto 6', 1.45);

INSERT INTO productos (categoria_id, fecha_ingreso, nombre, precio)
VALUES (3, CURRENT_DATE, 'Producto 7', 0.55);
INSERT INTO productos (categoria_id, fecha_ingreso, nombre, precio)
VALUES (3, CURRENT_DATE, 'Producto 8', 1.47);
INSERT INTO productos (categoria_id, fecha_ingreso, nombre, precio)
VALUES (3, CURRENT_DATE, 'Producto 9', 3.70);