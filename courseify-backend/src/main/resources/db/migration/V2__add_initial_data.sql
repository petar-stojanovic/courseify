insert into course_category (category_name)
values ('Java Programming'),
       ('Python Programming');

insert into role (role_name)
values ('Admin'),
       ('User');


insert into users (first_name, last_name, email, password, username, role_id)
values ('Petar', 'Stojanovic', 'petar@gmail.com', 'Test123!', 'petar', 1),
       ('Bojan', 'Ristevski', 'bojan@gmail.com', 'Test123!', 'bojan', 2);


insert into course (title, description, thumbnail, author_id, category_id)
values ('Java Programming Course', 'Java Programming Course Description', 'Thumbnail Java', 1, 1),
       ('Python Programming Course For Beginners', 'Python Programming Course For Beginners Description',
        'Thumbnail Python', 2, 2);

