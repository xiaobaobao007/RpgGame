DROP TABLE IF EXISTS `account`;
CREATE TABLE `account`
(
  `account`    varchar(128) not null,
  `userId`     int(11)      not null,
  `password`   varchar(255) not null,
  `email`      varchar(255) default null,
  `createTime` varchar(255) not null,
  `registIp`   varchar(255) default null,
  PRIMARY KEY (`account`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;