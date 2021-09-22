drop database if exists `chatrooms`;

create database `chatrooms`;

use `chatrooms`;

create table `users`(
    `id` int primary key AUTO_INCREMENT,
    `name` varchar(50),
    `email` varchar(50) unique key,
    `password` varchar(200),
    `enabled` int
);

drop table if exists `authorities`;
create table `authorities`(
    `id` int primary key AUTO_INCREMENT,
    `username` varchar(50),
    `authority` varchar(50)
);

drop table if exists `chatrooms`;
create table `chatrooms`(
    `id` int primary key AUTO_INCREMENT,
    `user_id` int,
    `name` varchar(50),
    `description` varchar(200),

    foreign key (`user_id`) references `users`(`id`) on delete restrict

);

drop table if exists `messages`;
create table `messages`(
    `message_id` int primary key AUTO_INCREMENT,
    `user_id` int,
    `chatroom_id` int,
    `message` varchar(200),
    `timestamp` timestamp,
    
    foreign key (`user_id`) references `users`(`id`) on delete set null,
    foreign key (`chatroom_id`) references `chatrooms`(`id`) on delete set null
);

insert into `users` values(1, 'admin', 'admin', '$2a$10$sxmI1wfaAQRN8hIIP8z1Tuw6O5X52lh25IxnAetVX4RAzmZhKYMH.', 1);
insert into `authorities` values(1, 'admin', 'ROLE_ADMIN');
insert into `authorities` values(2, 'admin', 'ROLE_USER');

insert into `chatrooms` values(1, 1, 'General', 'For Everyone');