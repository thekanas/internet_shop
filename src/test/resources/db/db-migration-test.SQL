CREATE TABLE customer
(
    customerId UUID PRIMARY KEY ,
    fullName VARCHAR(128) NOT NULL,
    email VARCHAR(128) UNIQUE

);

CREATE TABLE product
(
    productId UUID PRIMARY KEY ,
    name VARCHAR(128) NOT NULL,
    price DECIMAL(8, 2)

);

CREATE TABLE orders
(
    orderId UUID PRIMARY KEY ,
    createDate TIMESTAMP NOT NULL,
    customerId UUID REFERENCES customer(customerId) ON DELETE CASCADE

);

CREATE TABLE order_product
(
    id BIGSERIAL PRIMARY KEY ,
    orderId UUID REFERENCES orders(orderId)  ON DELETE CASCADE,
    productId UUID REFERENCES product(productId)  ON DELETE CASCADE

);

