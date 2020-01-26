# Команды PostgreSQL

- Вход за юзера postgres: ` docker exec -it -u 70 db psql`
- Показать список БД: `\l`
- Показать список таблиц: `\dt`
- Текущая БД: `SELECT current_database();`
- Создать таблицу с автоинкрементом:

```sql
CREATE TABLE books (
id SERIAL PRIMARY KEY,
title VARCHAR(100) NOT NULL,
primary_author VARCHAR(100) NULL
);
```

- Добавить что-то:

```sql
INSERT INTO books (title, primary_author) VALUES ('The Hobbit', 'Tolkien');
INSERT INTO books (title, primary_author) VALUES ('Harry Potter', 'Rowling');
```

- Проверяем автоинкремент:

```sql
SELECT * FROM books;

 id |    title     | primary_author 
----+--------------+----------------
  1 | The Hobbit   | Tolkien
  2 | Harry Potter | Rowling
```

