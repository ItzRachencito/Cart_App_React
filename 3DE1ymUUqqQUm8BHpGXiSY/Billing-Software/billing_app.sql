DROP DATABASE IF EXISTS billing_app;
CREATE DATABASE billing_app CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE billing_app;

-- Tabla: tbl_category
DROP TABLE IF EXISTS tbl_category;
CREATE TABLE tbl_category (
  id BIGINT NOT NULL AUTO_INCREMENT,
  bg_color VARCHAR(255),
  category_id VARCHAR(255),
  created_at DATETIME,
  description VARCHAR(255),
  img_url VARCHAR(255),
  name VARCHAR(255),
  updated_at DATETIME,
  PRIMARY KEY (id),
  UNIQUE KEY unique_category_id (category_id),
  UNIQUE KEY unique_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla: tbl_items
DROP TABLE IF EXISTS tbl_items;
CREATE TABLE tbl_items (
  id BIGINT NOT NULL AUTO_INCREMENT,
  created_at DATETIME,
  description VARCHAR(255),
  img_url VARCHAR(255),
  item_id VARCHAR(255),
  name VARCHAR(255),
  price DECIMAL(38,2),
  updated_at DATETIME,
  category_id BIGINT NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY unique_item_id (item_id),
  KEY fk_category (category_id),
  CONSTRAINT fk_category FOREIGN KEY (category_id) REFERENCES tbl_category (id) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla: tbl_orders
DROP TABLE IF EXISTS tbl_orders;
CREATE TABLE tbl_orders (
  id BIGINT NOT NULL AUTO_INCREMENT,
  created_at DATETIME,
  customer_name VARCHAR(255),
  grand_total DOUBLE,
  order_id VARCHAR(255),
  razorpay_order_id VARCHAR(255),
  razorpay_payment_id VARCHAR(255),
  razorpay_signature VARCHAR(255),
  status TINYINT,
  payment_method ENUM('CASH','UPI'),
  phone_number VARCHAR(255),
  subtotal DOUBLE,
  tax DOUBLE,
  PRIMARY KEY (id),
  CHECK (status BETWEEN 0 AND 2)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla: tbl_order_items
DROP TABLE IF EXISTS tbl_order_items;
CREATE TABLE tbl_order_items (
  id BIGINT NOT NULL AUTO_INCREMENT,
  item_id VARCHAR(255),
  name VARCHAR(255),
  price DOUBLE,
  quantity INT,
  order_id BIGINT,
  PRIMARY KEY (id),
  KEY fk_order (order_id),
  CONSTRAINT fk_order FOREIGN KEY (order_id) REFERENCES tbl_orders (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla: tbl_users
DROP TABLE IF EXISTS tbl_users;
CREATE TABLE tbl_users (
  id BIGINT NOT NULL AUTO_INCREMENT,
  created_at DATETIME,
  email VARCHAR(255),
  name VARCHAR(255),
  password VARCHAR(255),
  role VARCHAR(255),
  updated_at DATETIME,
  user_id VARCHAR(255),
  PRIMARY KEY (id),
  UNIQUE KEY unique_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;