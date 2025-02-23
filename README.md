Database entries-----------------------------------------------------------
/*Bcrypt, 10 rounds
admin -> parola: admin123
employee -> parola: employee123
user -> parola: user123*/
 
INSERT INTO user_details (id_user_details, name, phone_number)
VALUES
('6fe64d16-89e9-42', 'Maria Ionescu', '0723456789'),
('0c89ae63-a308-45', 'Radu Marinescu', '0764788621'),
('7a6b2219-eecf-44', 'George Vasile', '0734567890'),
('f31447e4-048a-40', 'Dana Marginean', '0747537435');
 
INSERT INTO user (id_user, email, password, role, id_user_details) VALUES
('89e9-43-6fe64d16', 'maria.ionescu@admin.com', '$2a$10$b8mMLdbReBeNgsUXsDtkxO6.7hhRV2yKTpU911b9JKwyzDZoKIKYa', 'ADMINISTRATOR', '6fe64d16-89e9-42'),
('6402aefe-b261-42', 'radu.marinescu@employee.com', '$2a$10$dHOBZQV28ZDmviPA9eHeNuvo36WMdVBg5a3kjLThdaiBtjGmlTaGu', 'EMPLOYEE', '0c89ae63-a308-45'),
('d2a3802b-45be-46', 'george.vasile@yahoo.com', '$2a$10$gkZBQeMvtsQN0fAnQpDVG.Gd.H6XN7pHLTHATl2NMFu7vwJNTsWWu', 'USER', '7a6b2219-eecf-44'),
('c6fb10a6-c27f-4e', 'dana.marginean@employee.com', '$2a$10$ifAF2CirFpTZm0GjtnvEoe3rGGc0DfDa2Janp7DThoOhyi80l1yWG', 'EMPLOYEE', 'f31447e4-048a-40');
 
INSERT INTO category (id_category, name) VALUES
('beda4705-d7f1-4a', 'Pizza'),
('1fc784f7-5904-40', 'Dessert'),
('76284d4a-f0f2-48', 'Salad'),
('aff3fda2-a347-4c', 'Soup'),
('44538cd1-6f05-40', 'Pasta');
 
INSERT INTO restaurant (id_restaurant, name, description, phone_number, rating, latitude, longitude, start_hour, end_hour, id_user) VALUES
('ee44a282-1356-4e', 'Restaurantul Bunătăților', 'The best restaurant in town', '0123456789', 4.5, 46.749, 23.598, '08:00:00', '23:00:00', '6402aefe-b261-42'),
('2f658639-ffe4-41', 'Casa Gusturilor', 'Authentic and traditional tastes', '0987654321', 4.7, 46.769, 23.584, '09:00:00', '22:00:00', 'c6fb10a6-c27f-4e');
 
INSERT INTO menu_item (id_menu_item, name, description, price, is_available, id_restaurant) VALUES
('7338d127-5a64-48', 'Caesar Salad', 'Crisp romaine lettuce with homemade Caesar dressing', 20.00, 1, '2f658639-ffe4-41'),
('759d4638-01c6-4d', 'Tiramisu', 'Classic Italian dessert with mascarpone cheese and espresso-soaked ladyfingers', 25.00, 1, '2f658639-ffe4-41'),
('da27c47b-86d4-49', 'Chocolate Brownie', 'Rich and fudgy brownie with chocolate chunks', 18.00, 0, '2f658639-ffe4-41'),
('beda4705-d7f1-4a', 'Classic Margherita', 'Traditional pizza with tomato sauce, mozzarella, and fresh basil', 38.00, 1, 'ee44a282-1356-4e'),
('76284d4a-f0f2-48', 'Greek Salad', 'Crisp salad with tomatoes, cucumbers, red onions, olives, feta cheese, and dressing', 10.00, 1, '2f658639-ffe4-41'),
('aff3fda2-a347-4c', 'Creamy Tomato Basil', 'Rich and creamy tomato soup with a hint of basil', 7.00, 1, '2f658639-ffe4-41'),
('44538cd1-6f05-40', 'Spaghetti Carbonara', 'Pasta with a creamy sauce, pancetta, and grated Parmesan cheese', 14.00, 1, 'ee44a282-1356-4e');
 
/*Restaurantul Bunătăților -> pizza, pasta
Casa Gusturilor -> salad, soup, dessert*/
 
INSERT INTO restaurant_category (id_restaurant, id_category) VALUES
('ee44a282-1356-4e', 'beda4705-d7f1-4a'),
('ee44a282-1356-4e', '44538cd1-6f05-40'),
('2f658639-ffe4-41', '1fc784f7-5904-40'),
('2f658639-ffe4-41', '76284d4a-f0f2-48'),
('2f658639-ffe4-41', 'aff3fda2-a347-4c');

INSERT INTO user_details (id_user_details, name, phone_number) VALUES

('1a2b3c4d-5678-90', 'Ana Popescu', '0712345678'),

('2b3c4d5e-6789-01', 'Ion Georgescu', '0723456781'),

('3c4d5e6f-7890-12', 'Elena Dumitrescu', '0734567892'),

('4d5e6f7g-8901-23', 'Mihai Enescu', '0745678903');
 
INSERT INTO user (id_user, email, password, role, id_user_details) VALUES

('5678-90-1a2b3c4d', 'ana.popescu@employee.com', '$2a$10$abcdefg1234567hijklmnop', 'EMPLOYEE', '1a2b3c4d-5678-90'),

('6789-01-2b3c4d5e', 'ion.georgescu@employee.com', '$2a$10$hijklmnop1234567abcdefg', 'EMPLOYEE', '2b3c4d5e-6789-01'),

('7890-12-3c4d5e6f', 'elena.dumitrescu@employee.com', '$2a$10$mnop1234567abcdefgijkl', 'EMPLOYEE', '3c4d5e6f-7890-12'),

('8901-23-4d5e6f7g', 'mihai.enescu@employee.com', '$2a$10$ijklmnopabcdefg1234567h', 'EMPLOYEE', '4d5e6f7g-8901-23');

 
INSERT INTO restaurant (id_restaurant, name, description, phone_number, rating, latitude, longitude, start_hour, end_hour, id_user) VALUES

('a1b2c3d4-e5f6-78', 'Delicii Urbane', 'Urban delights with a modern twist', '0123456780', 4.6, 46.7712, 23.6236, '07:00:00', '22:30:00', '5678-90-1a2b3c4d'),

('b2c3d4e5-f6a7-89', 'Gusturi de Acasă', 'Home-style cooking at its best', '0987654320', 4.8, 46.7695, 23.5899, '10:00:00', '21:00:00', '6789-01-2b3c4d5e'),

('c3d4e5f6-a7b8-90', 'Bistro de la Colț', 'Cozy corner bistro with great coffee', '0123456781', 4.4, 46.7643, 23.5901, '06:30:00', '20:00:00', '7890-12-3c4d5e6f'),

('d4e5f6a7-b8c9-01', 'Savurări de Seară', 'Evening delights and fine dining', '0987654322', 4.9, 46.7708, 23.5854, '17:00:00', '23:30:00', '8901-23-4d5e6f7g');

 
INSERT INTO restaurant_category (id_restaurant, id_category) VALUES

('a1b2c3d4-e5f6-78', 'beda4705-d7f1-4a'),

('b2c3d4e5-f6a7-89', '44538cd1-6f05-40'),

('c3d4e5f6-a7b8-90', '1fc784f7-5904-40'),

('d4e5f6a7-b8c9-01', '76284d4a-f0f2-48');


 Installs---------------------------------------------
 npm install @fortawesome/react-fontawesome @fortawesome/free-solid-svg-icons
