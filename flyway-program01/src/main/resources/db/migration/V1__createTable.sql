create table Users (
id bigint(20) NOT NULL AUTO_INCREMENT,
username varchar(100) NOT NULL,
firstName varchar(50) NOT NULL,
lastName varchar(50) DEFAULT NULL,
email varchar(100) NOT NULL,
PRIMARY KEY (id),
UNIQUE KEY uk_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;