/*
Navicat MySQL Data Transfer

Source Server         : MySQL - 127.0.0.1
Source Server Version : 80012
Source Host           : localhost:3306
Source Database       : comic-cloud-admin

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2018-09-03 18:56:59
*/

drop database `comic-cloud-admin`;
create database `comic-cloud-admin`;

use `comic-cloud-admin`;

SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for USERS
-- ----------------------------
DROP TABLE IF EXISTS `USERS`;
create table `USERS`
(
    `ID`         INTEGER      NOT NULL AUTO_INCREMENT PRIMARY KEY UNIQUE,
    `UID`        VARCHAR(64)  NOT NULL UNIQUE,
    `USERNAME`   VARCHAR(100) NOT NULL,
    `PASSWORD`   VARCHAR(100) NOT NULL,
    `EMAIL`      VARCHAR(100) NOT NULL,
    `PHONE`      VARCHAR(20),
    `CREATED_AT` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `UPDATED_AT` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

create unique index `USERS_USERNAME_IDX` on `USERS` (`USERNAME`);

-- <'alice', bcrypt('alice-password')>
INSERT INTO USERS(UID, USERNAME, PASSWORD, EMAIL)
values (UUID(), 'alice', '$2a$10$ezJDL4CP0jMOsXO/0SmvJetPMehGdiIDYzh2SKsfkW5c57Bz33o2m', 'alice@gmail.com');
-- <'bob', bcrypt('bob-password')>
INSERT INTO USERS(UID, USERNAME, PASSWORD, EMAIL)
values (UUID(), 'bob', '$2a$10$vPZXpQ37RHVQhfI1jPCC9.6.w7LsmPu4Fee0FCF45rOXqv99UtQoa', 'bob@gmail.com');
-- <'chris', bcrypt('chris-password')>
INSERT INTO USERS(UID, USERNAME, PASSWORD, EMAIL)
values (UUID(), 'chris', '$2a$10$zo967KgB3M5kDcyRH.k6KegnSGKzfggZnG2wtCongD8FtIJTSrBnW', 'chris@gmail.com');
-- <'david', bcrypt('david-password')>
INSERT INTO USERS(UID, USERNAME, PASSWORD, EMAIL)
values (UUID(), 'david', '$2a$10$5H1gBZKAXY8UZsUwUbdGZux3GvXoOVP.urSI6RKH/TRsqGbtRb/UW', 'david@gmail.com');

-- ----------------------------
-- Table structure for USER_ROLES
-- ----------------------------
DROP TABLE IF EXISTS `USER_ROLES`;
CREATE TABLE `USER_ROLES`
(
    `ID`         INTEGER     NOT NULL AUTO_INCREMENT PRIMARY KEY UNIQUE,
    `UID`        VARCHAR(64) NOT NULL UNIQUE,
    `USERNAME`   VARCHAR(100),
    `ROLE_NAME`  VARCHAR(100),
    `CREATED_AT` TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

create unique index `USER_ROLES_USERNAME_IDX` on `USER_ROLES` (`USERNAME`);
create unique index `USER_ROLES_ROLE_NAME_IDX` on `USER_ROLES` (`ROLE_NAME`);

-- roles of users
INSERT INTO USER_ROLES(UID, USERNAME, ROLE_NAME)
values (UUID(), 'alice', 'admin');
INSERT INTO USER_ROLES(UID, USERNAME, ROLE_NAME)
values (UUID(), 'bob', 'user');
INSERT INTO USER_ROLES(UID, USERNAME, ROLE_NAME)
values (UUID(), 'chris', 'file-operator');
INSERT INTO USER_ROLES(UID, USERNAME, ROLE_NAME)
values (UUID(), 'david', 'log-archiver');

-- ----------------------------
-- Table structure for ROLES_PERMISSIONS
-- ----------------------------
DROP TABLE IF EXISTS `ROLES_PERMISSIONS`;
CREATE TABLE `ROLES_PERMISSIONS`
(
    `ID`         INTEGER     NOT NULL AUTO_INCREMENT PRIMARY KEY UNIQUE,
    `UID`        VARCHAR(64) NOT NULL UNIQUE,
    `ROLE_NAME`  VARCHAR(100),
    `PERMISSION` VARCHAR(100),
    `CREATED_AT` TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

create unique index `ROLES_PERMISSIONS_ROLE_NAME_IDX` ON `ROLES_PERMISSIONS` (`ROLE_NAME`);
create unique index `ROLES_PERMISSIONS_PERMISSION_IDX` ON `ROLES_PERMISSIONS` (`PERMISSION`);

-- permissions of roles

-- The 'admin' role has all permissions.
INSERT INTO ROLES_PERMISSIONS(UID, ROLE_NAME, PERMISSION)
values (UUID(), 'admin', '*');

-- The 'user' role can read and write files. This line can also be replaced by two lines:
--   insert into ROLES_PERMISSIONS(role_name, permission) values ('user', 'files:read');
--   insert into ROLES_PERMISSIONS(role_name, permission) values ('user', 'files:write');
INSERT INTO ROLES_PERMISSIONS(UID, ROLE_NAME, PERMISSION)
values (UUID(), 'user', 'files:read,write');

-- The 'file-operator' role can do anything to files.
INSERT INTO ROLES_PERMISSIONS(UID, ROLE_NAME, PERMISSION)
values (UUID(), 'file-operator', 'files:*');

-- The 'log-archiver' role can read and archive log files.
INSERT INTO ROLES_PERMISSIONS(UID, ROLE_NAME, PERMISSION)
values (UUID(), 'log-archiver', 'files:read,archive:log');

SET FOREIGN_KEY_CHECKS = 1;
