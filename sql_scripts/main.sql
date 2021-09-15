drop database if exists `chatrooms`;

create database `chatrooms`;

use `chatrooms`;

create table `users`(
    `id` int primary key AUTO_INCREMENT,
    `name` varchar(50),
    `email` varchar(50),
    `password` varchar(50),
    `role` varchar(50)
);

create table `chatrooms`(
    `id` int primary key AUTO_INCREMENT,
    `user_id` int,
    `name` varchar(50),
    `description` varchar(200),

    foreign key (`user_id`) references `users`(`id`)
);

create table `messages`(
    `message_id` int primary key AUTO_INCREMENT,
    `user_id` int,
    `chatroom_id` int,
    `message` varchar(200),
    `timestamp` timestamp,
    
    foreign key (`user_id`) references `users`(`id`),
    foreign key (`chatroom_id`) references `chatrooms`(`id`)
);