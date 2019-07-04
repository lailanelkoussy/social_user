CREATE TABLE user
(
    user_id     int(11)      NOT NULL AUTO_INCREMENT,
    email       varchar(255) NOT NULL,
    username    varchar(255) NOT NULL,
    password    varchar(255) NOT NULL,
    first_name  varchar(255) NOT NULL,
    active      bool         DEFAULT TRUE,
    middle_name varchar(255) DEFAULT NULL,
    last_name   varchar(255) NOT NULL,
    birthday    datetime(6) DEFAULT NULL,

    PRIMARY KEY (user_id),
    UNIQUE (username),
    UNIQUE (email)
);