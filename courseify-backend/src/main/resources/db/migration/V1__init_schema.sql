create table role
(
    id        bigserial primary key,
    role_name text not null
);

create table category
(
    id            bigserial primary key,
    category_name text not null
);

create table users
(
    id         bigserial primary key,
    first_name text not null,
    last_name  text not null,
    email      text not null unique,
    password   text not null,
    username   text not null unique,
    role_id    bigint references role
);

create table course
(
    id          bigserial primary key,
    title       text not null,
    description text not null,
    thumbnail   text not null,
    author_id   bigint references users,
    category_id bigint references course_category
);

create table lesson
(
    id          bigserial primary key,
    title       text not null,
    description text,
    video_title text not null,
    video_url   text not null,
    file_title  text,
    file_url    text,
    course_id   bigint references course ON DELETE CASCADE
);


create table quiz
(
    id        bigserial primary key,
    title     text not null,
    lesson_id bigint references lesson on DELETE CASCADE
);

alter table lesson
    ADD COLUMN quiz_id bigint references quiz ON DELETE SET NULL;


create table question
(
    id      bigserial primary key,
    content text not null,
    quiz_id bigint references quiz ON DELETE CASCADE
);


create table answer
(
    id          bigserial primary key,
    content     text not null,
    question_id bigint references question ON DELETE CASCADE
);

alter table question
    ADD COLUMN correct_answer_id bigint references answer ON DELETE SET NULL;