use crud_manager;
desc users;
show TABLES;
drop table if exists companies;

create table if not EXISTS vehicles(
    id int not null AUTO_INCREMENT,
    plate varchar(10) not null unique,
    model varchar(50) not null,
    manufacture_year int not null,
    category varchar(20) not null,
    user_id int not null,
    PRIMARY KEY (id),
    CONSTRAINT fk_vehicle_user foreign key (user_id) references users(id)

);

create table if not exists parkings(
    id int not null AUTO_INCREMENT,
    spot_number int not null,
    entry_time DATETIME not null,
    hourly_rate DECIMAL(10,2) not null,
    is_occupied BOOLEAN not null DEFAULT 1,
    vehicle_id int not null,
    primary key (id),
   CONSTRAINT fk_parking_vehicle FOREIGN KEY (vehicle_id) REFERENCES vehicles(id)
);

RENAME TABLE posts TO messages;