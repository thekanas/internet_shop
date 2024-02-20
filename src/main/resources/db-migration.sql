CREATE TABLE customer
(
    customerId UUID PRIMARY KEY ,
    fullName VARCHAR(128) NOT NULL,
    email VARCHAR(128) UNIQUE

);

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

INSERT INTO customer(customerId, fullName, email)
VALUES ('cd0ebac6-2b2d-465c-ab6f-752dcd8cc7ac'::uuid, 'Pavlov Pavel Pavlovich', 'google1@goo.com'),
       ('384f6458-9b42-41e2-9f05-308259dba4c0'::uuid, 'Ivanov Ivan Jovanovich', 'google2@goo.com'),
       ('d79705b3-372d-47d3-8a56-8be7eae13737'::uuid, 'Grigorev Grigori Grigorevich', 'google3@goo.com');

CREATE TABLE product
(
    productId UUID PRIMARY KEY ,
    name VARCHAR(128) NOT NULL,
    price DECIMAL(8, 2)

);

INSERT INTO product(productId, name, price)
VALUES ('432c2ccf-91ba-436b-beea-694fd8ac12d9'::uuid, 'Book', 33.25),
       ('8bcbdbb4-4608-4953-a087-4882c3ac673c'::uuid, 'TV', 9999.99),
       ('fea299c4-f9f8-4fb4-8f36-038b651af810'::uuid, 'Laptop', 500.00);

CREATE TABLE orders
(
    orderId UUID PRIMARY KEY ,
    createDate TIMESTAMP NOT NULL,
    customerId UUID REFERENCES customer(customerId) ON DELETE CASCADE

);

INSERT INTO orders(orderId, createDate, customerId)
VALUES ('da4970c5-5053-4b48-87db-32693abe6e76'::uuid, now(), 'cd0ebac6-2b2d-465c-ab6f-752dcd8cc7ac'),
       ('73839edc-0373-440c-a3e9-484090fc4a0d'::uuid, now(), 'cd0ebac6-2b2d-465c-ab6f-752dcd8cc7ac'),
       ('87a87929-be1f-452d-90dc-d713a4577105'::uuid, now(), '384f6458-9b42-41e2-9f05-308259dba4c0');


CREATE TABLE order_product
(
    id BIGSERIAL PRIMARY KEY ,
    orderId UUID REFERENCES orders(orderId)  ON DELETE CASCADE,
    productId UUID REFERENCES product(productId)  ON DELETE CASCADE

);

INSERT INTO order_product(orderId, productId)
VALUES ('da4970c5-5053-4b48-87db-32693abe6e76'::uuid, '432c2ccf-91ba-436b-beea-694fd8ac12d9'::uuid),
       ('73839edc-0373-440c-a3e9-484090fc4a0d'::uuid, '8bcbdbb4-4608-4953-a087-4882c3ac673c'::uuid),
       ('73839edc-0373-440c-a3e9-484090fc4a0d'::uuid, 'fea299c4-f9f8-4fb4-8f36-038b651af810'::uuid),
       ('87a87929-be1f-452d-90dc-d713a4577105'::uuid, '8bcbdbb4-4608-4953-a087-4882c3ac673c'::uuid);