insert into category (category_name)
values ('Java Programming'),
       ('Python Programming');


insert into users (first_name, last_name, email, password, username, role)
values ('Petar', 'Stojanovic', 'petar@gmail.com', 'Test123!', 'petar', 'USER'),
       ('Bojan', 'Ristevski', 'bojan@gmail.com', 'Test123!', 'bojan', 'USER');


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

insert into question(content, quiz_id)
values ('2 + 2', 1),
       ('3 + 3', 1),
       ('4 + 4', 1),
       ('5 + 5', 1);

insert into answer(content, question_id)
values ('3', 1),
       ('4', 1),
       ('5', 1),
       ('6', 1),

       ('0', 2),
       ('3', 2),
       ('6', 2),
       ('9', 2),

       ('65', 3),
       ('-23', 3),
       ('8', 3),
       ('I dont know', 3),

       ('1000', 4),
       ('-10', 4),
       ('100', 4),
       ('10', 4);

update question
set correct_answer_id = 2
where question.id = 1;

update question
set correct_answer_id = 7
where question.id = 2;

update question
set correct_answer_id = 11
where question.id = 3;

update question
set correct_answer_id = 16
where question.id = 4;

insert into takes_course(user_id, course_id, start_date, end_date)
values (1, 1, '2023-07-20', null),
       (1, 2, '2020-01-01', null),
       (2, 1, '2020-02-02', '2021-03-03'),
       (2, 2, '2021-05-04', null)
