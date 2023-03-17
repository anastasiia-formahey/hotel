create database if not exists hotel;
create table if not exists features
(
    id   int auto_increment
        primary key,
    name varchar(100) not null
);

create table if not exists room
(
    id               int auto_increment
        primary key,
    number_of_person int                not null,
    price            double default (0) null,
    class_of_room    varchar(20)        null,
    image            varchar(200)       null
);

create table if not exists room_features
(
    room_id    int null,
    feature_id int null,
    constraint room_features_ibfk_1
        foreign key (room_id) references room (id),
    constraint room_features_ibfk_2
        foreign key (feature_id) references features (id)
);

create index feature_id
    on room_features (feature_id);

create index room_id
    on room_features (room_id);

create table if not exists status
(
    status_id int         not null
        primary key,
    status    varchar(45) not null,
    constraint id_UNIQUE
        unique (status_id)
);

create table if not exists occupancy_of_room
(
    room_id        int  not null,
    client_id      int  not null,
    check_in_date  date not null,
    check_out_date date not null,
    status_id      int  not null,
    constraint occupancy_of_room_status_id
        foreign key (status_id) references status (status_id),
    constraint room_id
        foreign key (room_id) references room (id)
);

create table if not exists user
(
    id         int auto_increment
        primary key,
    first_name varchar(20) not null,
    last_name  varchar(20) not null,
    email      varchar(30) not null,
    password   varchar(10) not null,
    role       varchar(10) not null,
    constraint email
        unique (email)
);

create table if not exists application
(
    id               int auto_increment
        primary key,
    client_id        int           not null,
    number_of_guests int           not null,
    apartment_class  varchar(50)   not null,
    length_of_stay   int           not null,
    status_id        int default 5 not null,
    constraint client_id
        foreign key (client_id) references user (id),
    constraint status_id
        foreign key (status_id) references status (status_id)
);

create index id_idx
    on application (client_id);

create index status_id_idx
    on application (status_id);

create table if not exists booking
(
    id              int auto_increment
        primary key,
    room_id         int    not null,
    client_id       int    not null,
    check_in_date   date   not null,
    check_out_date  date   not null,
    price           double not null,
    date_of_booking date   not null,
    status_id       int    not null,
    constraint booking_client_id
        foreign key (client_id) references user (id),
    constraint booking_room_id
        foreign key (room_id) references room (id),
    constraint booking_status_id
        foreign key (status_id) references status (status_id)
);

create index status_id_idx
    on booking (status_id);

create table if not exists request
(
    application_id int           not null,
    check_in_date  date          not null,
    check_out_date date          not null,
    room_id        int           not null,
    status_id      int default 7 not null,
    constraint application_id
        foreign key (application_id) references application (id),
    constraint request_status_id
        foreign key (status_id) references status (status_id)
);

create index room_id
    on request (room_id);
INSERT INTO hotel.status (status_id, status) VALUES
                                                 (1, 'FREE'),
                                                 (2, 'BOOKED'),
                                                 (3, 'BUSY'),
                                                 (4, 'UNAVAILABLE'),
                                                 (5, 'NEW'),
                                                 (6, 'REVIEWED'),
                                                 (7, 'NOT_CONFIRMED'),
                                                 (8, 'CONFIRMED'),
                                                 (9, 'PAID'),
                                                 (10, 'CANCELED');

INSERT INTO hotel.room (id, number_of_person, price, class_of_room, image)
VALUES (2, 2, 1200, 'BUSINESS', 'busines.jpg'),
       (3, 2, 700, 'STANDARD', 'standart.jpg'),
       (4, 2, 4500, 'PRESIDENT', 'president.jpg'),
       (5, 3, 3000, 'LUX', 'lux.jpg'),
       (6, 6, 2000, 'STANDARD', 'standart.jpg'),
       (7, 1, 800, 'BUSINESS', 'busines.jpg'),
       (8, 1, 1200, 'LUX', 'lux.jpg'),
       (9, 1, 3000, 'PRESIDENT', 'president.jpg'),
       (10, 2, 2500, 'LUX', 'lux.jpg'),
       (11, 4, 1700, 'STANDARD', 'standart.jpg'),
       (12, 4, 3000, 'BUSINESS', 'busines.jpg'),
       (13, 1, 2500, 'PRESIDENT', 'president.jpg'),
       (14, 1, 2000, 'PRESIDENT', 'president.jpg'),
       (15, 1, 4000, 'PRESIDENT', 'president.jpg'),
       (16, 2, 1000, 'LUX', 'lux.jpg'),
       (17, 3, 3500, 'LUX', 'lux.jpg'),
       (18, 1, 500, 'STANDARD', 'standart.jpg'),
       (19, 3, 5000, 'PRESIDENT', 'president.jpg'),
       (20, 1, 1000, 'BUSINESS', 'busines.jpg'),
       (21, 3, 1200, 'BUSINESS', 'busines.jpg'),
       (22, 3, 2000, 'LUX', 'lux.jpg'),
       (23, 2, 1000, 'STANDARD', 'standart.jpg'),
       (25, 1, 500, 'STANDARD', 'standart.jpg'),
       (26, 1, 100, 'STANDARD', 'standart.jpg'),
       (27, 2, 700, 'STANDARD', 'standart.jpg'),
       (28, 1, 2000, 'PRESIDENT', 'president.jpg'),
       (29, 3, 1500, 'BUSINESS', 'busines.jpg');

INSERT INTO hotel.user (id, first_name, last_name, email, password, role)
VALUES (1, 'Anastasiia', 'Formahei', 'anastasiia.formahey@gmail.com', 'MTExMQ==', 'MANAGER'),
       (6, 'Test', 'Test', 'test@gmail.com', 'MTExMQ==', 'CLIENT'),
       (22, 'Ivan', 'Ivanov', 'ivanov@gmail.com', 'MDAwMA==', 'CLIENT'),
       (23, 'Анна', 'Іванова', 'ivanova@email.com', 'MDAwMA==', 'CLIENT');

INSERT INTO hotel.room_features (room_id, feature_id)
VALUES (5, 1), (5, 2),(5, 3),(6, 1),(9, 1),(9, 2),(9, 3),(9, 4),
       (9, 6),(8, 1),(8, 2), (8, 3),(10, 1),(10, 2),(10, 3),
       (10, 5),(11, 1),(11, 2),(12, 1),(12, 2),(12, 3),
       (13, 1), (13, 2),(13, 3),(13, 4),(13, 5),
       (13, 6),(14, 1),(14, 2),(14, 3), (14, 4),(4, 1),(4, 2),
       (4, 3),(4, 4),(4, 6),(15, 1),(15, 2),(15, 3),(15, 4),
       (15, 5),(15, 6),(16, 1),(16, 2),(16, 3),(16, 6),(17, 1),
       (17, 2), (17, 3),(17, 4),(18, 1),(7, 1),(7, 2),(7, 5),
       (19, 1),(19, 2),(19, 3),(19, 4),(19, 6),(20, 1),(20, 2),
       (20, 3),(3, 1),(3, 2),(3, 3),(22, 1),(22, 2),(22, 3),
       (21, 1),(21, 2),(27, 1),(27, 6),(28, 1),(28, 2),(28, 3),
       (28, 4),(28, 6),(29, 1),(29, 2),(29, 3),(2, 1),(2, 5),
       (2, 6);

insert into features values
                         (DEFAULT, 'Free Wi-Fi'),
                         (DEFAULT, 'Conditioner'),
                         (DEFAULT, 'Mini bar'),
                         (DEFAULT, 'Strongbox'),
                         (DEFAULT, 'Scales'),
                         (DEFAULT, 'TV');
insert into status values
                       (1, 'FREE'),
                       (2, 'BOOKED'),
                       (3, 'BUSY'),
                       (4, 'UNAVAILABLE'),
                       (5, 'NEW'),
                       (6, 'REVIEWED'),
                       (7, 'NOT_CONFIRMED'),
                       (8, 'CONFIRMED'),
                       (9, 'PAID')
