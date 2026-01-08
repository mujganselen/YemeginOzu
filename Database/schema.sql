CREATE DATABASE IF NOT EXISTS yemeginozu_db;
USE yemeginozu_db;

CREATE TABLE categories (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    display_order INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE menu_items (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(150) NOT NULL,
    description TEXT,
    base_price DECIMAL(10, 2) NOT NULL,
    category_id INT NOT NULL,
    is_available BOOLEAN DEFAULT TRUE,
    image_path VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE
);

CREATE TABLE ingredients (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    extra_price DECIMAL(10, 2) DEFAULT 0.00,
    is_removable BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE menu_item_ingredients (
    menu_item_id INT NOT NULL,
    ingredient_id INT NOT NULL,
    is_default BOOLEAN DEFAULT TRUE,
    PRIMARY KEY (menu_item_id, ingredient_id),
    FOREIGN KEY (menu_item_id) REFERENCES menu_items(id) ON DELETE CASCADE,
    FOREIGN KEY (ingredient_id) REFERENCES ingredients(id) ON DELETE CASCADE
);

CREATE TABLE orders (
    id INT PRIMARY KEY AUTO_INCREMENT,
    order_number VARCHAR(50) UNIQUE NOT NULL,
    order_type ENUM('DINE_IN', 'TAKEAWAY') NOT NULL,
    total_price DECIMAL(10, 2) NOT NULL,
    status ENUM('PENDING', 'PREPARING', 'READY', 'COMPLETED', 'CANCELLED') DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    completed_at TIMESTAMP NULL
);

CREATE TABLE order_items (
    id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT NOT NULL,
    menu_item_id INT NOT NULL,
    quantity INT NOT NULL DEFAULT 1,
    unit_price DECIMAL(10, 2) NOT NULL,
    customization_note TEXT,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (menu_item_id) REFERENCES menu_items(id)
);

CREATE TABLE order_item_customizations (
    id INT PRIMARY KEY AUTO_INCREMENT,
    order_item_id INT NOT NULL,
    ingredient_id INT NOT NULL,
    action ENUM('ADD', 'REMOVE') NOT NULL,
    FOREIGN KEY (order_item_id) REFERENCES order_items(id) ON DELETE CASCADE,
    FOREIGN KEY (ingredient_id) REFERENCES ingredients(id)
);

INSERT INTO categories (name, description, display_order) VALUES
('Ana Yemekler', 'Lezzetli ana yemek seçeneklerimiz', 1),
('Başlangıçlar', 'Yemeğe başlamadan önce atıştırmalıklar', 2),
('Tatlılar', 'Tatlı seçeneklerimiz', 3),
('İçecekler', 'Soğuk ve sıcak içecekler', 4);

INSERT INTO ingredients (name, extra_price, is_removable) VALUES
('Domates', 0.00, TRUE),
('Soğan', 0.00, TRUE),
('Biber', 0.00, TRUE),
('Marul', 0.00, TRUE),
('Peynir', 5.00, TRUE),
('Zeytin', 3.00, TRUE),
('Mantar', 7.00, TRUE),
('Acı Sos', 0.00, TRUE),
('Ranch Sos', 2.00, TRUE),
('BBQ Sos', 2.00, TRUE);

INSERT INTO menu_items (name, description, base_price, category_id) VALUES
('Izgara Tavuk', 'Özel baharatlarla marine edilmiş ızgara tavuk', 85.00, 1),
('Köfte', 'El yapımı köfte', 95.00, 1),
('Pizza Margherita', 'Klasik İtalyan pizzası', 120.00, 1),
('Patates Kızartması', 'Çıtır çıtır patates', 35.00, 2),
('Soğan Halkası', 'Paneli soğan halkası', 40.00, 2),
('Sezar Salata', 'Taze marul ve kruton', 55.00, 2),
('Baklava', 'Antep fıstıklı baklava', 60.00, 3),
('Sütlaç', 'Fırın sütlaç', 45.00, 3),
('Kola', 'Soğuk kola', 15.00, 4),
('Ayran', 'Soğuk ayran', 10.00, 4);

INSERT INTO menu_item_ingredients (menu_item_id, ingredient_id, is_default) VALUES
(1, 1, TRUE), (1, 2, TRUE), (1, 3, TRUE),
(2, 1, TRUE), (2, 2, TRUE),
(3, 1, TRUE), (3, 5, TRUE), (3, 6, TRUE),
(4, 8, FALSE), (4, 9, FALSE),
(6, 4, TRUE), (6, 5, TRUE), (6, 1, TRUE);