CREATE TABLE t_coffee (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255),
    price BIGINT NOT NULL ,
    create_time TIMESTAMP,
    update_time TIMESTAMP,
    PRIMARY KEY (id)
);