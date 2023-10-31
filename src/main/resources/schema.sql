
CREATE TABLE IF NOT EXISTS MEMBERS(
                                      MID         INT(8)  NOT NULL,
                                      ROLE        ENUM('STUDENT', 'PARLIAMENT', 'WARDEN', 'PROFESSOR', 'DEAN') NOT NULL,
                                      FIRSTNAME   VARCHAR(15) NOT NULL,
                                      LASTNAME    VARCHAR(25) default ' ',
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

CREATE INDEX complaints_idx ON COMPLAINTS (postedById, postedByRole);

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

insert into MEMBERS(MID, ROLE, FIRSTNAME, BATCH, BRANCH, DATEOFBIRTH, EMAIL, PHONENUMBER)
VALUES (13075000, 'WARDEN', 'WARDEN', 1919, 'NON', '1919-01-01', 'warden.vs@itbhu.ac.in' ,'1234567890');

insert into users (username, password, enabled, mid, role)
values('WARDEN13075000', '$2a$10$ymyxESdBrXbkDoT9L7E03u0jO3HB7iD87nbzJaa/hy27ho2z/Vmee', true, 13075000, 'warden');

insert into authorities (username, authority)
values('WARDEN13075000', 'warden');

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

CREATE INDEX messages_idx ON MESSAGES (grpId);

CREATE TABLE IF NOT EXISTS GROUP_MEMBERSHIP(
                                               id int AUTO_INCREMENT primary key,
                                               grpId int NOT NULL,
                                               memberId    INT(8) NOT NULL,
                                               memberRole  ENUM('STUDENT', 'PARLIAMENT', 'WARDEN', 'PROFESSOR', 'DEAN') NOT NULL,

                                               FOREIGN KEY (memberId, memberRole) REFERENCES MEMBERS(MID, ROLE),
                                               FOREIGN KEY (grpId) REFERENCES MESSAGE_GROUP(grpId)
);
CREATE INDEX group_membership_idx_grpId ON  GROUP_MEMBERSHIP(grpId);
CREATE INDEX group_membership_idx_member ON  GROUP_MEMBERSHIP(memberId,memberRole);

ALTER TABLE GROUP_MEMBERSHIP ADD  unreadCnt int not null default 0;

create table if not exists inventory(
                                        itemId int(8) not null auto_increment,
                                        itemName varchar(50) not null,
                                        quantity int not null default 0,
                                        thresholdQuantity int,
                                        primary key(itemId)
);

create table if not exists services(
                                       serviceId int(8) not null auto_increment,
                                       serviceName varchar(50) not null,
                                       assignedToId  INT(8) NOT NULL,
                                       assignedToRole ENUM('STUDENT', 'PARLIAMENT', 'WARDEN', 'PROFESSOR', 'DEAN', 'STAFF') default 'STAFF',
                                       lastUpdatedOn datetime default current_timestamp not null,
                                       primary key(serviceId),
                                       FOREIGN KEY (assignedToId, assignedToRole) REFERENCES MEMBERS(MID, ROLE)
);

CREATE INDEX services_idx ON  services(assignedToId,assignedToRole);

ALTER TABLE MEMBERS MODIFY COLUMN  ROLE  ENUM('STUDENT', 'PARLIAMENT', 'WARDEN', 'PROFESSOR', 'DEAN','STAFF') NOT NULL;
ALTER TABLE users MODIFY COLUMN  role  ENUM('STUDENT', 'PARLIAMENT', 'WARDEN', 'PROFESSOR', 'DEAN','STAFF') NOT NULL;
ALTER TABLE GROUP_MEMBERSHIP MODIFY COLUMN  memberRole  ENUM('STUDENT', 'PARLIAMENT', 'WARDEN', 'PROFESSOR', 'DEAN','STAFF') NOT NULL;
ALTER TABLE MESSAGES MODIFY COLUMN  sentByRole  ENUM('STUDENT', 'PARLIAMENT', 'WARDEN', 'PROFESSOR', 'DEAN','STAFF') NOT NULL;
ALTER TABLE MESSAGE_GROUP MODIFY COLUMN  adminRole  ENUM('STUDENT', 'PARLIAMENT', 'WARDEN', 'PROFESSOR', 'DEAN','STAFF') NOT NULL;

create table if not exists dues (
                                    dueId int(8) not null auto_increment,
                                    imposedOnId int(8) not null,
                                    imposedOnRole ENUM('STUDENT', 'PARLIAMENT', 'WARDEN', 'PROFESSOR', 'DEAN', 'STAFF'),
                                    dueDate datetime default current_timestamp not null,
                                    dueType varchar(50) not null,
                                    dueAmount int not null,
                                    primary key(dueId),
                                    foreign key(imposedOnId, imposedOnRole) references MEMBERS(mid, role)
);

CREATE INDEX dues_idx ON  dues(imposedOnId,imposedOnRole);




