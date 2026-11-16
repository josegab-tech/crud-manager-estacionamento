USE crud_manager;

DROP TABLE IF EXISTS companies;

ALTER TABLE users
    ADD COLUMN password VARCHAR(255) NOT NULL DEFAULT '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92';

CREATE TABLE IF NOT EXISTS vehicles (
    id INT NOT NULL AUTO_INCREMENT,
    plate VARCHAR(10) NOT NULL UNIQUE,
    model VARCHAR(50) NOT NULL,
    manufacture_year INT NOT NULL,
    category VARCHAR(20) NOT NULL,
    user_id INT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_vehicle_user FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS parkings (
    id INT NOT NULL AUTO_INCREMENT,
    spot_number INT NOT NULL,
    entry_time DATETIME NULL DEFAULT NULL,
    hourly_rate DECIMAL(10,2) NOT NULL,
    is_occupied BOOLEAN NOT NULL DEFAULT 0,
    vehicle_id INT NULL DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_parking_vehicle FOREIGN KEY (vehicle_id) REFERENCES vehicles(id)
);

RENAME TABLE posts TO messages;