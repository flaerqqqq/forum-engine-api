DELETE FROM user_roles WHERE true;
DELETE FROM roles WHERE true;

INSERT INTO roles (name) VALUES ('ROLE_USER');