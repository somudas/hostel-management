
CREATE TABLE IF NOT EXISTS MEMBERS(
    MID         INT(8)  NOT NULL,
    ROLE        ENUM('STUDENT', 'PARLIAMENT', 'WARDEN', 'PROFESSOR', 'DEAN') NOT NULL,
    FIRSTNAME   VARCHAR(15) NOT NULL,
    LASTNAME    VARCHAR(25),
    BATCH       INT CHECK(BATCH > 1918 && BATCH < 2030) NOT NULL,
    BRANCH      ENUM('CSE', 'MNC', 'ECE', 'EEE', 'MEC', 'CHE', 'MET', 'MIN', 'CIV', 'CER', 'PHE', 'APD', 'NON') NOT NULL,
    DATEOFBIRTH DATE NOT NULL,
    EMAIL       VARCHAR(50) NOT NULL,
    PHONENUMBER CHAR(10) NOT NULL,
    PRIMARY KEY(MID, ROLE)
);

CREATE TABLE IF NOT EXISTS COMPLAINTS(
  cmpId         int AUTO_INCREMENT primary key,
  title         VARCHAR(50) NOT NULL,
  description   VARCHAR(250) NOT NULL,
  status        ENUM('RESOLVED', 'UNRESOLVED') NOT NULL,
  feedback      VARCHAR(50),
  postedById    INT(8) NOT NULL,
  postedByRole  ENUM('STUDENT', 'PARLIAMENT', 'WARDEN', 'PROFESSOR', 'DEAN') NOT NULL,
  postedAt      datetime default current_timestamp not null,
  FOREIGN KEY (postedById, postedByRole) REFERENCES MEMBERS(MID, ROLE)
);

create table users(
                      username      varchar(50) not null primary key,
                      password      varchar(500) not null,
                      enabled       boolean not null,
                       mid  int  not null,
                      role  ENUM('STUDENT', 'PARLIAMENT', 'WARDEN', 'PROFESSOR', 'DEAN') NOT NULL,
                        FOREIGN KEY (mid, role) REFERENCES MEMBERS(MID, ROLE)
);


create table authorities (
 username       varchar(50) not null,
 authority      varchar(50) not null,
 constraint fk_authorities_users foreign key(username) references users(username)
);

create unique index ix_auth_username on authorities (username,authority);


CREATE TABLE IF NOT EXISTS MESSAGE_GROUP(
                                       grpId         int AUTO_INCREMENT primary key,
                                       name       VARCHAR(50) NOT NULL,
                                       description       VARCHAR(250) NOT NULL,
                                       adminId    INT(8) NOT NULL,
                                       adminRole  ENUM('STUDENT', 'PARLIAMENT', 'WARDEN', 'PROFESSOR', 'DEAN') NOT NULL,
                                       FOREIGN KEY (adminId, adminRole) REFERENCES MEMBERS(MID, ROLE)
);

CREATE TABLE IF NOT EXISTS MESSAGES(
                                         msgId         int AUTO_INCREMENT primary key,
                                         grpId         int NOT NULL,
                                         content       VARCHAR(250) NOT NULL,
                                         sentById    INT(8) NOT NULL,
                                         sentByRole  ENUM('STUDENT', 'PARLIAMENT', 'WARDEN', 'PROFESSOR', 'DEAN') NOT NULL,
                                         sentAt      datetime default current_timestamp not null,
                                         FOREIGN KEY (sentById, sentByRole) REFERENCES MEMBERS(MID, ROLE),
                                        FOREIGN KEY (grpId) REFERENCES MESSAGE_GROUP(grpId)

);

CREATE TABLE IF NOT EXISTS GROUP_MEMBERSHIP(
    id int AUTO_INCREMENT primary key,
    grpId int NOT NULL,
    memberId    INT(8) NOT NULL,
    memberRole  ENUM('STUDENT', 'PARLIAMENT', 'WARDEN', 'PROFESSOR', 'DEAN') NOT NULL,

    FOREIGN KEY (memberId, memberRole) REFERENCES MEMBERS(MID, ROLE),
    FOREIGN KEY (grpId) REFERENCES MESSAGE_GROUP(grpId)
);








