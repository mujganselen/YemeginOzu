-- RESET + CREATE + SEED (tek seferde çalıştır)

DROP DATABASE IF EXISTS yemeginozu_db;
CREATE DATABASE yemeginozu_db;
USE yemeginozu_db;

-- Categories table
CREATE TABLE categories (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    display_order INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Menu Items table
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

-- Ingredients table
CREATE TABLE ingredients (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    extra_price DECIMAL(10, 2) DEFAULT 0.00,
    is_removable BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Menu Item Ingredients
CREATE TABLE menu_item_ingredients (
    menu_item_id INT NOT NULL,
    ingredient_id INT NOT NULL,
    is_default BOOLEAN DEFAULT TRUE,
    PRIMARY KEY (menu_item_id, ingredient_id),
    FOREIGN KEY (menu_item_id) REFERENCES menu_items(id) ON DELETE CASCADE,
    FOREIGN KEY (ingredient_id) REFERENCES ingredients(id) ON DELETE CASCADE
);

-- Orders table
CREATE TABLE orders (
    id INT PRIMARY KEY AUTO_INCREMENT,
    order_number VARCHAR(50) UNIQUE NOT NULL,
    order_type ENUM('DINE_IN', 'TAKEAWAY') NOT NULL,
    total_price DECIMAL(10, 2) NOT NULL,
    status ENUM('PENDING', 'PREPARING', 'READY', 'COMPLETED', 'CANCELLED') DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    completed_at TIMESTAMP NULL
);

-- Order Items table
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

-- Order Item Customizations
CREATE TABLE order_item_customizations (
    id INT PRIMARY KEY AUTO_INCREMENT,
    order_item_id INT NOT NULL,
    ingredient_id INT NOT NULL,
    action ENUM('ADD', 'REMOVE') NOT NULL,
    FOREIGN KEY (order_item_id) REFERENCES order_items(id) ON DELETE CASCADE,
    FOREIGN KEY (ingredient_id) REFERENCES ingredients(id)
);

-- Seed categories
INSERT INTO categories (name, description, display_order) VALUES
('Main Dishes', 'Our signature main course options', 1),
('Starters', 'Light bites to begin your meal', 2),
('Desserts', 'Sweet dessert selections', 3),
('Drinks', 'Cold and hot beverages', 4);

-- Seed ingredients
INSERT INTO ingredients (name, extra_price, is_removable) VALUES
('Ketchup', 0.00, TRUE),
('Mayonnaise', 0.00, TRUE),
('Tomato', 0.00, TRUE),
('Onion', 0.00, TRUE),
('Pepper', 0.00, TRUE),
('Lettuce', 0.00, TRUE),
('Cheese', 5.00, TRUE),
('Olives', 3.00, TRUE),
('Mushrooms', 7.00, TRUE),
('Hot Sauce', 0.00, TRUE),
('Ranch Sauce', 2.00, TRUE),
('BBQ Sauce', 2.00, TRUE);

-- Seed menu_items (ilk 10 aynı sırada)
INSERT INTO menu_items (name, description, base_price, category_id) VALUES
('Grilled Chicken', 'Grilled chicken marinated with special spices', 85.00, 1),
('Meatballs (Köfte)', 'Handmade Turkish meatballs', 95.00, 1),
('Pizza Margherita', 'Classic Italian pizza with tomato sauce and mozzarella', 120.00, 1),
('French Fries', 'Crispy golden fries', 35.00, 2),
('Onion Rings', 'Crispy battered onion rings', 40.00, 2),
('Caesar Salad', 'Fresh lettuce with croutons and Caesar dressing', 55.00, 2),
('Baklava', 'Traditional layered pastry with pistachio', 60.00, 3),
('Rice Pudding (Sütlaç)', 'Baked Turkish rice pudding', 45.00, 3),
('Cola', 'Chilled cola', 15.00, 4),
('Ayran', 'Cold yogurt-based Turkish drink', 10.00, 4);

-- Yeni Main Dishes
INSERT INTO menu_items (name, description, base_price, category_id) VALUES
('Bursa Iskender', 'Doner meat over bread with tomato sauce and butter', 165.00, 1),
('Grilled Köfte', 'Grilled Turkish meatballs served with garnish', 135.00, 1),
('Cokertme Kebab', 'Beef over crispy potatoes with yogurt sauce', 175.00, 1),
('Eggplant Kebab', 'Kebab prepared with roasted eggplant and meat', 170.00, 1),
('Beyti Kebab', 'Wrapped kebab served with yogurt and tomato sauce', 180.00, 1),
('Kofte over Eggplant Puree (Beğendili Köfte)', 'Meatballs on smoked eggplant puree', 155.00, 1);

-- Yeni Desserts
INSERT INTO menu_items (name, description, base_price, category_id) VALUES
('Trilece', 'Sponge cake soaked in three-milk syrup', 65.00, 3),
('Bread Pudding with Kaymak (Ekmek Kadayıfı)', 'Traditional bread dessert served with kaymak', 75.00, 3),
('Profiterole', 'Choux pastry filled with cream and chocolate sauce', 70.00, 3),
('Tulumba', 'Fried dough dessert soaked in syrup', 55.00, 3),
('Supangle', 'Chocolate pudding dessert', 60.00, 3),
('Chocolate Souffle (Sufle)', 'Warm chocolate souffle', 80.00, 3);

-- Yeni Drinks
INSERT INTO menu_items (name, description, base_price, category_id) VALUES
('Ice Tea', 'Chilled iced tea', 18.00, 4),
('Tea', 'Hot black tea', 12.00, 4);

-- Seed menu_item_ingredients
INSERT INTO menu_item_ingredients (menu_item_id, ingredient_id, is_default) VALUES
(1, 1, TRUE), (1, 2, TRUE), (1, 3, TRUE),
(2, 1, TRUE), (2, 2, TRUE),
(3, 1, TRUE), (3, 5, TRUE), (3, 6, TRUE),
(4, 8, FALSE), (4, 9, FALSE),
(6, 4, TRUE), (6, 5, TRUE), (6, 1, TRUE);
