INSERT INTO categories (title)
VALUES
    ('Авто'),
    ('Продукты'),
    ('Коммунальные услуги'),
    ('Одежда'),
    ('Транспорт'),
    ('Косметика'),
    ('Аренда'),
    ('Прочее') ON CONFLICT DO NOTHING;