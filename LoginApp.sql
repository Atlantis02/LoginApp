CREATE database loginapp;

CREATE USER 'loginapp'@'%' IDENTIFIED BY 'password';

GRANT ALL PRIVILEGES ON *.* TO 'loginapp'@'localhost' IDENTIFIED BY 'password'


CREATE TABLE `tb_user` (
 `userId` bigint(20) NOT NULL AUTO_INCREMENT,
 `emailId` varchar(128) NOT NULL,
 `password` varchar(128) NOT NULL,
 `isValid` bool,
 PRIMARY KEY (`userId`),
 UNIQUE KEY `emailId` (`emailId`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8;

create table tb_user_access (
 userId bigint(20) NOT NULL,
 accessToken varchar(128),
 expiryTime timestamp,
 primary key (userId)
);

create table tb_validation (
 userId bigint(20) NOT NULL,
 expiryTime timestamp,
 validationCode varchar(128),
 primary key (userId)
);

create table tb_user_profile (
 userId bigint(20) NOT NULL,
 userName varchar(128),
 pic varchar(128),
 primary key (userId)
);