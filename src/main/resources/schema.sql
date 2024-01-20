DROP TABLE IF EXISTS order_rows;
DROP TABLE IF EXISTS orders;
DROP SEQUENCE IF EXISTS seq1;

CREATE SEQUENCE seq1 AS INTEGER START WITH 1;

CREATE TABLE orders
(
    id           INT NOT NULL PRIMARY KEY DEFAULT nextval('seq1'),
    order_number VARCHAR(255)
);

CREATE TABLE order_rows
(
    orders_id INT,
    item_name VARCHAR(255),
    quantity  INT,
    price     DOUBLE PRECISION,
    FOREIGN KEY (orders_id) REFERENCES orders ON DELETE CASCADE
);

CREATE TABLE users
(
    username   VARCHAR(255) NOT NULL PRIMARY KEY,
    password   VARCHAR(255) NOT NULL,
    enabled    BOOLEAN      NOT NULL,
    first_name VARCHAR(255) NOT NULL
);

CREATE TABLE authorities
(
    username  VARCHAR(50) NOT NULL,
    authority VARCHAR(50) NOT NULL,
    FOREIGN KEY (username) REFERENCES users ON DELETE CASCADE
);

INSERT INTO users (username, password, enabled, first_name)
VALUES ('user', '$2a$10$QCJXkfCPqQ7oFsJ/mCkzk.D9dpsORC0j0vRFnjy7r3E9t.KaHVuUW', true, 'Jack');

INSERT INTO users (username, password, enabled, first_name)
VALUES ('admin', '$2a$10$GfKM3Y0Q.5F/DHIEnTs17.dSJzgVvb8bd9tzdysnN9NR0PJ6RbQzW', true, 'Jill');

INSERT INTO AUTHORITIES (username, authority)
VALUES ('user', 'ROLE_USER');

INSERT INTO AUTHORITIES (username, authority)
VALUES ('admin', 'ROLE_ADMIN');