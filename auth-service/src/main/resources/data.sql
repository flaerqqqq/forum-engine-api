DELETE FROM user_roles WHERE true;
DELETE FROM roles WHERE true;
DELETE FROM refresh_tokens WHERE true;
DELETE FROM auth_users WHERE true;

INSERT INTO roles (id, name) VALUES (1 ,'ROLE_USER');
INSERT INTO auth_users(id, username, password) VALUES ('id', 'username', '$2a$10$gy/3lP/mgShrLefjz7X3ZO58KQk42XtQPQe0LWBb00WrHvYq5ccby');
INSERT INTO user_roles(id, user_id, role_id) VALUES (1, 'id', 1);