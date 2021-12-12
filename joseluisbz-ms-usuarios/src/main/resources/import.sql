INSERT INTO USERS (name, email, password, created, last_login, isactive) VALUES('Jose Luis', 'joseluisbz@gmail.com', '12345', NOW(), NOW(), 1);
INSERT INTO USERS (name, email, password, created, last_login, isactive) VALUES('Alcira', 'alcira@gmail.com', '09876', NOW(), NOW(), 0);

INSERT INTO PHONES (number, city_code, country_code, user_id) VALUES(12345, 1, 57, 1);
INSERT INTO PHONES (number, city_code, country_code, user_id) VALUES(09876, 5, 57, 2);