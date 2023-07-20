insert into category (category_name)
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


insert into lesson(title, description, video_title, video_url, file_title, file_url, course_id)
values ('Lesson Title', 'Lesson Description', 'video title', 'video url', 'file title', 'file url', 1);

insert into quiz (title, lesson_id)
values ('Quiz Title', 1);

update lesson
set quiz_id = 1
where lesson.id = 1;

insert into question(content,quiz_id)
values ('2 + 2')