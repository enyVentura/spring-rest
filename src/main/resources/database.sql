-- Table: users
CREATE TABLE users(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
    userName VARCHAR(255) NOT NULL ,
    firstName VARCHAR(255) NOT NULL ,
    lastName VARCHAR(255) NOT NULL ,
    password VARCHAR(255) NOT NULL
)
ENGINE =InnoDB;

-- Table: roles
CREATE TABLE roles(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
    name VARCHAR(100) NOT NULL
)
ENGINE =InnoDB;

-- Table for mapping user id roles: users_roles
CREATE TABLE users_roles(
    user_id INT NOT NULL ,
    role_id INT NOT NULL ,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (role_id) REFERENCES roles (id)
)
ENGINE =InnoDB;
INSERT INTO users VALUES ('user','userFirstName','userLastName','$2y$12$OZRyaSKiTsKwELNgZNZr8e6qj9sEq34NUJxTRdEvcybjjKxUDcUaG'); #123
INSERT INTO users VALUES ('admin','adminFirstName','adminLastName','$2y$12$OZRyaSKiTsKwELNgZNZr8e6qj9sEq34NUJxTRdEvcybjjKxUDcUaG'); #123
INSERT INTO roles VALUES (1,'ROLE_USER');
INSERT INTO roles VALUES (2,'ROLE_ADMIN');
INSERT INTO users_roles VALUES (1,2);