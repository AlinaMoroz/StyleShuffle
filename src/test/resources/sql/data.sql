INSERT INTO users (id, name, email, password, avatar, size, surname, username)
VALUES (1, 'Иван Иванов', 'ivan@example.com', 'password123', 'avatar1.jpg', 'M', 'Иванов', 'ivan_ivanov'),
       (2, 'Мария Петрова', 'maria@example.com', 'password456', 'avatar2.jpg', 'S', 'Петрова', 'maria_petrova'),
       (3, 'Алексей Сидоров', 'alex@example.com', 'password789', 'avatar3.jpg', 'L', 'Сидоров', 'alex_sidorov');
SELECT SETVAL('users_id_seq', (SELECT MAX(id) FROM users));






INSERT INTO looks (id, description,  name, user_id)
VALUES (1, 'Повседневный стиль', 'Набор 1', 1),
       (2, 'Спортивный стиль', 'Набор 2', 2),
       (3, 'Офисный стиль', 'Набор 3', 3);
SELECT SETVAL('looks_id_seq', (SELECT MAX(id) FROM looks));

INSERT INTO news_lines (id, look_id, post_date, like_count, dislike_count)
VALUES (1, 1 ,'2023-10-01 12:00:00', 10, 2),
       (2, 2, '2023-10-02 14:30:00', 5, 1),
       (3, 3, '2023-10-03 09:15:00', 20, 3);
SELECT SETVAL('news_lines_id_seq', (SELECT MAX(id) FROM news_lines));



INSERT INTO clothes (id, user_id, link_photo, season, type, brand, color)
VALUES (1, 1, 'photo1.jpg', 'SUMMER', 'TSHIRT', 'Nike', 'BLACK'),
       (2, 1, 'photo2.jpg', 'SUMMER', 'SHORTS', 'Adidas', 'BLUE'),
       (3, 2, 'photo3.jpg', 'WINTER', 'JACKET', 'The North Face', 'RED'),
       (4, 2, 'photo4.jpg', 'WINTER', 'JACKET', 'Columbia', 'GRAY'),
       (5, 3, 'photo5.jpg', 'AUTUMN', 'JACKET', 'Zara', 'BEIGE'),
       (6, 3, 'photo6.jpg', 'AUTUMN', 'JACKET', 'H&M', 'BLACK');
SELECT SETVAL('clothes_id_seq', (SELECT MAX(id) FROM clothes));



INSERT INTO looks_clothes (id, look_id, cloth_id)
VALUES (1, 1, 1), -- Набор 1: Футболка
       (2, 1, 2), -- Набор 1: Шорты
       (3, 2, 3), -- Набор 2: Куртка
       (4, 2, 4), -- Набор 2: Шапка
       (5, 3, 5), -- Набор 3: Пальто
       (6, 3, 6); -- Набор 3: Брюки
SELECT SETVAL('looks_clothes_id_seq', (SELECT MAX(id) FROM looks_clothes));



INSERT INTO comments (id, user_id, text, date_post, news_line_id)
VALUES (1, 1, 'Отличный пост!', '2023-10-01 12:30:00', 1),
       (2, 2, 'Мне тоже нравится!', '2023-10-01 13:00:00', 1),
       (3, 3, 'Интересный стиль.', '2023-10-02 15:00:00', 2);
SELECT SETVAL('comments_id_seq', (SELECT MAX(id) FROM comments));

