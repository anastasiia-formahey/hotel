use hotel;
    create table room(
        id int not null auto_increment,
        number_of_person int not null,
        price double default(0),
        class_of_room varchar(20),
        image varchar(200),
        primary key (id)
    );
