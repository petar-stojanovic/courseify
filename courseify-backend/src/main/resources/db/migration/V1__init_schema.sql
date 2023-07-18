create table role
(
    id        bigserial primary key,
    role_name text not null
);

create table course_category
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
    author_id   bigint references users,
    category_id bigint references course_category
);

create table lesson
(
    id          bigserial primary key,
    title       text not null,
    description text,
    course_id   bigint references course ON DELETE CASCADE
);

-- create table quiz(
--     id bigserial primary key,
--     question tex
-- )
