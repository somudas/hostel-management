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
                                      cmpId  int AUTO_INCREMENT primary key,
                                      title   VARCHAR(50) NOT NULL,
                                      description    VARCHAR(250) NOT NULL,
                                      status       ENUM('RESOLVED', 'UNRESOLVED') NOT NULL,
                                      feedback VARCHAR(50),
                                      postedById INT(8) NOT NULL,
                                      postedByRole ENUM('STUDENT', 'PARLIAMENT', 'WARDEN', 'PROFESSOR', 'DEAN') NOT NULL,
                                      postedAt datetime default current_timestamp not null,

                                    FOREIGN KEY (postedById, postedByRole) REFERENCES MEMBERS(MID, ROLE)

);