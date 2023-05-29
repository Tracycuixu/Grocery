-- drop table if exists test.product;
-- drop table if exists test.secondary_category;
-- drop table if exists test.primary_category;
-- drop database if exists test;
-- drop table if exists test.sequence;
drop database test;
create database test;
use test;
CREATE TABLE test.category (
    category_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
 );
 insert into test.category (`name`) values
("fruits"),
("vegetables"),
("meat"),
("seafood"),
("milkEggs"),
("snacks");

CREATE TABLE test.product (
 product_id bigint not null auto_increment primary key,
 name VARCHAR(50) NOT NULL,
 unit_format varchar(20) not null,
 img_path varchar(250),
 category_id INT NOT NULL,
 FOREIGN KEY (category_id) REFERENCES category(category_id)
);
insert into test.product(`name`,unit_format,category_id)
values("banana","ea",1),
("Beef-Tomato","lb",2),
("Carrots","bag",2),
("Solid-Potato","bag",2),
("beef","lb",3),
("beef-packaged","lb",3),
("veal","kg",3),
("Chicken-breast","lb",3),
("apple","ea",1),
("watermelon","ea",1),
("mango","ea",1),
("grapes","ea",1)
;
create table test.inventory(
inventory_id bigint not null auto_increment primary key,
product_id bigint,
cost_price decimal(10,2),
sale_price decimal(10,2),
quantity int,
`status` varchar(2),
created_date date,
foreign key (product_id) references product(product_id)
);
insert into test.inventory(product_id,buy_price, sale_price,quantity,`status`,created_date) values
(1,0.11,1.11,11,'A','2023-04-14'),
(2,0.22,2.22,22,'A','2023-04-14'),
--(2,0.55,2.22,22,'A','2023-04-14'),
(3,0.33,3.33,33,'A','2023-04-14'),
(4,0.44,4.44,44,'A','2023-04-14'),
(5,0.55,5.55,55,'A','2023-04-14'),
(6,3,6.66,66,'A','2023-04-14'),
(7,3,5.55,55,'A','2023-04-14'),
(8,5,6.66,66,'A','2023-04-14'),
(9,4,5.55,55,'A','2023-04-14'),
(10,3,6.66,66,'A','2023-04-14'),
(11,4,5.55,55,'A','2023-04-14'),
(12,2,6.66,66,'A','2023-04-14');



-- create table test.supplier(
-- supplier_id int auto_increment primary key,
-- name varchar(50),
-- location varchar(50)
-- );
-- insert into test.supplier(`name`,location) values
-- ("kimPhat","980 Broadway"),
-- ("costco","99 peel"),
-- ("XX farm","87  principal"); 
-- create table test.purchase_order(
-- order_id bigint not null auto_increment primary key,
-- product_id bigint,
-- unit_price decimal(10,2),
-- quantity int,
-- supplier_id int,
-- foreign key(product_id) references product(product_id),
-- foreign key(supplier_id) references supplier(supplier_id)
-- );
-- insert into test.purchase_order(product_id,unit_price,quantity,supplier_id) values
-- (1,0.99,100,1),
-- (1,0.98,200,2),
-- (2,1.00,100,3),
-- (3,1.00,100,1),
-- (4,1.00,100,2);

-- create table test.customer(
-- customer_id bigint auto_increment primary key,
-- `name` varchar(50),
-- age int,
-- address varchar(50)); 


-- create table  test.`order`(
-- order_id bigint auto_increment primary key,
-- customer_id bigint,
-- order_datetime datetime,
-- countsOfOrderDetail int,
-- foreign key(customer_id) references customer(customer_id) );

insert into test.client(client_id,address,email,password,phone,user_name) values
(1,"Canada","canada@email.com","canada",'1234567890','canada'),
(2,"USA","usa@email.com","usa",'1234567890','usa'),
(3,"China","china@email.com","china",'1234567890','china'),
(4,"UK","uk@email.com","uk",'1234567890','uk');



